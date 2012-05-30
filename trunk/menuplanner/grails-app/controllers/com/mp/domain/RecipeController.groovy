package com.mp.domain

import com.mp.domain.ndb.NutritionLink
import com.mp.domain.party.Coach
import com.mp.domain.party.Party
import com.mp.email.Tag
import com.mp.tools.UserTools
import grails.util.GrailsUtil
import org.apache.lucene.document.NumberTools
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.grails.comments.Comment
import org.springframework.dao.DataIntegrityViolationException
import static com.mp.MenuConstants.RECIPE_IMAGE_SIZES
import static com.mp.MenuConstants.SYSTEM_OF_UNIT_USA
import com.mp.domain.*
import org.apache.commons.math.fraction.ProperFractionFormat
import com.mp.domain.subscriptions.Subscription
import com.mp.subscriptions.SubscriptionStatus
import com.mp.domain.subscriptions.CommunitySubscription
import com.mp.domain.subscriptions.RecipeContribution
import com.mp.domain.subscriptions.SubscriptionContributionRequirement
import com.mp.domain.pricing.Price

class RecipeController {
  final int resultsPerPage = 12
  static config = ConfigurationHolder.config
  def unitService
  def recipeService
  def masterDataBootStrapService
  def searchableService
  def userService
  def asynchronousMailService
  def analyticsService
  def standardConversionService
  static allowedMethods = [save: "POST", update: "POST"]

  def calculateNutrition = {
      Recipe recipe = Recipe.get(params.int('recipeId'))
    if(!recipe){
      flash.message = "Could not Find the recipe to calculate nutritional values on"
      redirect(action: "edit")
    }
    recipe.ingredients.each {
      Item ingredient = it.ingredient
      Quantity q = it.quantity

      if(ingredient instanceof MeasurableProduct && q.savedUnit.isConvertible){
        MeasurableProduct prod = ingredient
        Unit unit = prod.preferredUnit
        //VH=(N*CM)/100
        /**
         * Vh = the nutrient content per the desired common measure
            N = the nutrient content per 100 g
            For NDB No. 01001, fat = 81.11 g/100 g
            CM = grams of the common measure
            For NDB No. 01001, 1 tablespoon = 14.2 g
         */
        NutritionLink mapping = NutritionLink.findByIngredient(it)
        def N = mapping.food.Water
        def CM = mapping.weightMap.weight.Gm_Wgt
        def amount = mapping.weightMap.weight.Amount
        def volume = mapping.weightMap.volume
        def weighedQuantity = StandardConversionService.getQuantityToSave("${amount}",volume)
        def convQ = unitService.convertQuantity(weighedQuantity,q.unit)
        def VH = /* add in the amount factor here */ (q.value/convQ.value) * N * CM / 100
        println "Ingredient ${it} has ${VH} g Water."


      }
    }
    flash.message = "Finished Calculating the Nutritional Values"

    redirect(action:"edit")

  }
  def index = {
    redirect(action: "list")
  }

  def getMatchingItems = {
    List<Item> items = recipeService.getItemsForCurrentUser("%${params.q}%")
    String itemsJson = ''
    items.each {
      String name = it.name.replaceAll("'", "\\\'")
      itemsJson += name + "|" + it.id + "\n"
    }
    render(itemsJson)
  }

  def getMatchingProducts = {
    List<Item> items = recipeService.getProductsForCurrentUser("%${params.q}%")
    String itemsJson = ''
    items.each {
      String name = it.name.replaceAll("'", "\\\'")
      itemsJson += name + "|" + it.id + "\n"
    }
    render(itemsJson)
  }

  def list = {
    params.max = Math.min(params.max ? params.int('max') : resultsPerPage, 150)
    params.offset = Math.min(params.offset ? params.int('offset') : 0, 100)
    analyticsService.recordIntervalIn(System.currentTimeMillis(), request.'appRequestCO', "filtered-results", params.toString())
    Party currentUser = UserTools.currentUser?.party
    List<Recipe> filteredResults = recipeService.getFilteredRecipeList(params.max, params.long('offset'),currentUser)
    filteredResults = hydrateRecipeResults(filteredResults)

    Integer total = recipeService.getFilteredRecipeCount()
    analyticsService.recordIntervalOut(System.currentTimeMillis(), request.'appRequestCO', "filtered-results")
    analyticsService.recordIntervalIn(System.currentTimeMillis(), request.'appRequestCO', "sub-categories", params.toString())
    def subCategories = SubCategory.findAll('from SubCategory s where s in (select s.id from Recipe r join r.subCategories s)').sort{it.name}
    analyticsService.recordIntervalOut(System.currentTimeMillis(), request.'appRequestCO', "sub-categories")
    def categories = com.mp.domain.Category.findAll('from Category c where c in (select s.category.id from SubCategory s where s in (select s.id from Recipe r join r.subCategories s))').sort{it.name}
    render(view: 'list', model: [recipeList: filteredResults, categories: categories, subCategories: subCategories, recipeTotal: total])
  }

  private List<Recipe> hydrateRecipeResults(List results) {
    def recipeIds = results.collect {it.id}
    List<Recipe> filteredResults
    def lids = '('
    recipeIds.each{
      lids += it + ','    
    }
    lids += '0)'
    analyticsService.recordIntervalIn(System.currentTimeMillis(), request.'appRequestCO', "hydration", lids)

    filteredResults = Recipe.findAll('from Recipe as r left join fetch r.image \
    inner join fetch r.ingredients ingredients join fetch ingredients.ingredient \
    inner join fetch r.preparationTime pt \
    inner join fetch r.preparationTime.unit pu \
    inner join fetch r.cookingTime ct \
    inner join fetch r.cookingTime.unit cu \
    where r.id in ' + lids).unique()
    def leftIds = '('
    def foundIds =       filteredResults.collect { it.id }


    recipeIds.removeAll(foundIds)

    recipeIds.each {
      leftIds += it + ','
    }
    leftIds += '0)'
    def ingredientFreeRecipeResults = Recipe.findAll('from Recipe as r left join fetch r.image \
    where r.id in ' + leftIds).unique()
    analyticsService.recordIntervalOut(System.currentTimeMillis(), request.'appRequestCO', "hydration")
    filteredResults.addAll(ingredientFreeRecipeResults)
    filteredResults
  }

  def search = {
    Party currentUser = UserTools.currentUser?.party
    Long currentUserId = currentUser?.id
    List<String> allQueries = []
    List<String> subCategoriesString = []
    String subQueryString
    List<Recipe> results = []

    if (!params.query || (params.query == 'null')) {
      params.query = ''
    }
    if ((params.query instanceof String) && params.query.startsWith('[')) {
      params.query = params.query.substring(1, params.query.length() - 1).tokenize(',')
    }
    List queryList = params.list('query').flatten()
    queryList = queryList.findAll {it?.trim()}
    queryList?.eachWithIndex {String myQ, Integer index ->
      if (myQ.contains('subCategoriesString')) {
        String categoryString = myQ.split(":").flatten().get(1)
        if (categoryString.endsWith(']')) {
          categoryString = categoryString.substring(0, categoryString.length() - 1)
        }
        subCategoriesString.add(categoryString)

      } else {
        allQueries.push(myQ)
      }
    }
    if (subCategoriesString) {
      subQueryString = subCategoriesString.join('" OR "')
      subQueryString = '"' + subQueryString + '"'
      allQueries.push('subCategoriesString:' + subQueryString)
    }

    String keyword = allQueries.find {!(it.contains(':'))}
    allQueries = allQueries.findAll {(it.contains(':'))}
    if (keyword) {
      allQueries.add("*${keyword}*")
    }
    String query = allQueries?.join(" ")?.tokenize(", ")?.join(" ")
    if (query.startsWith('[')) {
      query = query.substring(1, query.length() - 1)
    }
    Integer total
    query += " (shareWithCommunity:true${(currentUser ? (" OR contributorsString:" + NumberTools.longToString(currentUser?.id)) : '')})"
    if (!currentUser?.showAlcoholicContent) { query += "  isAlcoholic:false" }
    def searchList = Recipe.search([reload: true, max: resultsPerPage, offset: params.offset ? params.long('offset') : 0]) {
      must(queryString(query))
    }
    results = searchList?.results
    total = searchList?.total

    if (!results && keyword) {
      String newQuery = recipeService.fuzzySearchQuery(query, keyword)
      searchList = Recipe.search([reload: true, max: resultsPerPage, offset: params.offset ? params.long('offset') : 0]) {
        must(queryString(newQuery))
      }
      results = searchList?.results
      results = searchList?.total
    }
    results = hydrateRecipeResults(results)

    render(template: '/recipe/searchResultRecipe', model: [recipeList: results, recipeTotal: total, query: params.query])
  }

  def delete = {
    def recipe = Recipe.get(params.id)
    LoginCredential loggedUser = UserTools.currentUser
    if (recipe) {
      try {
        flash.message = message(code: 'recipe.deleted.success')
        recipeService.deleteRecipe(recipe, loggedUser?.party)
        redirect(controller: 'recipe', action: "list")
        return
      }
      catch (org.springframework.dao.DataIntegrityViolationException e) {
        flash.message = message(code: 'recipe.deleted.unsuccess')
        redirect(action: "show", id: params.id)
        return
      }
    }
    else {
      flash.message = "No such Recipe exists."
      redirect(action: "list")
      return
    }
  }

  def create = {
    SystemOfUnit sys = SystemOfUnit.findBySystemName(SYSTEM_OF_UNIT_USA)
    List<Nutrient> nutrients = Nutrient.list()
    render(view: 'create', model: [timeUnits: sys.timeUnits, metricUnits: Unit.sortedMetricUnits, nutrients: nutrients,
            categories: Category.list(), aisles: Aisle.getAislesForCurrentUser(), preparationMethods: PreparationMethod.list()])
  }

  def edit = {
    if (params.id) {
      def recipe = getRecipe(params.long('id'))
      def recipeCO = recipeService.initRecipeCO (recipe)
      SystemOfUnit sys = SystemOfUnit.findBySystemName(SYSTEM_OF_UNIT_USA)
      List<Nutrient> nutrients = Nutrient.list()
      render(view: 'edit', model: [recipeCO: recipeCO, timeUnits: sys.timeUnits, metricUnits: Unit.sortedMetricUnits, nutrients: nutrients,
              categories: Category.list(), aisles: Aisle.getAislesForCurrentUser(), preparationMethods: PreparationMethod.list()])
    }
  }

  def update = {RecipeCO recipeCO ->
    if (recipeCO.validate()) {
      Recipe recipe = recipeCO.updateRecipe()
      flash.message = "Your changes have been saved successfully to ${recipeCO.name}"
      validateRecipeContribution(recipe)
      redirect(action: 'show', id: recipeCO?.id)
      searchableService.reindex(class: Recipe, recipe?.id)
    } else {
      println recipeCO.errors.allErrors.each {
        println it
      }
      SystemOfUnit sys = SystemOfUnit.findBySystemName(SYSTEM_OF_UNIT_USA)
      List<Nutrient> nutrients = Nutrient.list()
      render(view: 'edit', model: [recipeCO: recipeCO, timeUnits: sys.timeUnits, metricUnits: Unit.sortedMetricUnits, nutrients: nutrients,
              categories: Category.list(), aisles: Aisle.getAislesForCurrentUser(), preparationMethods: PreparationMethod.list()])
    }
  }

  def save = {RecipeCO recipeCO ->
    if (recipeCO.validate()) {
      LoginCredential loggedUser = UserTools.currentUser
      Recipe recipe = recipeCO.convertToRecipe(loggedUser?.party)
      loggedUser?.party?.addToContributions(recipe)
      loggedUser.party?.s()
      searchableService.index(class: Recipe, recipe?.id)
      flash.message = "Recipe ${recipe.name} has been created successfully"
      validateRecipeContribution(recipe)
      redirect(action: 'show', id: recipe?.id)
    } else {
      recipeCO.errors.allErrors.each {
        println it
      }
      SystemOfUnit sys = SystemOfUnit.findBySystemName(SYSTEM_OF_UNIT_USA)
      List<Nutrient> nutrients = Nutrient.list()
      render(view: 'create', model: [recipeCO: recipeCO, timeUnits: sys.timeUnits, metricUnits: Unit.sortedMetricUnits, nutrients: nutrients,
              categories: Category.list(), aisles: Aisle.getAislesForCurrentUser(), preparationMethods: PreparationMethod.list()])
    }
  }

  private def validateRecipeContribution(recipe) {
    def partyObject = UserTools.currentUser.party
    log.debug "obtaining subscriptions for ${partyObject.name}"
    def partyId = partyObject.id
    def subscriptions = CommunitySubscription.withCriteria {
      eq('status', SubscriptionStatus.PENDING)
      subscriptionFor {
        party {
          partyObject
        }
      }
      
      requirements {
        requires {
          eq('controllerName', 'recipe')
          eq('actionName', 'create')
        }
      }
    }
    subscriptions.each {subscription ->
      subscription.status = SubscriptionStatus.CURRENT
      if(subscription instanceof CommunitySubscription){
        def scrList = SubscriptionContributionRequirement.withCriteria {
          requiredFor {
            idEq(subscription.id)
          }
        }
        assert scrList.size() == 1
        SubscriptionContributionRequirement scr = scrList[0]
        RecipeContribution contribution = new RecipeContribution(recipe:recipe,created:new Date(),completed:new Date())
        contribution.s()
        scr.fulfilledBy = contribution
        scr.s()
      }
      subscription.s()
    }
  }

  def addComment = {
    Recipe recipe = Recipe.findById(params?.recipeId)
    LoginCredential user = UserTools.currentUser
    def posters =[] as Set
    posters.addAll recipe.comments.collect {comment ->
      Party.get(comment?.poster?.id)
    }

    recipe?.addComment(user?.party, params?.comment)
    Party contributor = recipe?.contributor
    if (GrailsUtil.environment != 'test'){

      // send a note to the contributor
      if (contributor) {
        posters.remove(contributor)
        asynchronousMailService.sendAsynchronousMail {
          replyTo user?.party?.email
          from user?.party?.email
          to contributor?.email
          subject "${Tag.comment} A comment was made on your recipe: ${recipe?.name}"
          html g.render(template: '/recipe/comment', model: [tag:Tag.comment,party: user?.party, note: params?.comment, recipeId: recipe?.id])
        }
      }
      // send a note to the coach
      Coach coach = contributor?.coach
      if (coach) {
        posters.remove(coach)
        asynchronousMailService.sendAsynchronousMail {
          replyTo user?.party?.email
          from user?.party?.email
          to coach?.party?.email
          subject "${Tag.comment} A comment was made on your client's [${contributor.name}] recipe: ${recipe?.name}"
          html g.render(template: '/recipe/comment', model: [tag:Tag.comment,party: user?.party, note: params?.comment, recipeId: recipe?.id])
        }
      }
      def director = coach.director
      if (director) {
        posters.remove(director)
        asynchronousMailService.sendAsynchronousMail {
          replyTo user?.party?.email
          from user?.party?.email
          to director?.party?.email
          subject "${Tag.comment} A comment was made on one of your coaches [${coach?.party?.name}] client's [${contributor?.name}] recipe: ${recipe?.name}"
          html g.render(template: '/recipe/comment', model: [tag:Tag.comment,party: user?.party, note: params?.comment, recipeId: recipe?.id])
        }
      }
      posters.each {poster ->
        asynchronousMailService.sendAsynchronousMail {
          replyTo user?.party?.email
          from user?.party?.email
          to poster?.email
          subject "${Tag.comment} A comment was made on a recipe you have commented on: ${recipe?.name}"
          html g.render(template: '/recipe/comment', model: [tag:Tag.comment,party: user?.party, note: params?.comment, recipeId: recipe?.id])
        }
      }
    }
    redirect(action: 'show', controller: 'recipe', params: [id: recipe?.id])
  }

  def Recipe getRecipe(id){
    Recipe recipe = Recipe.find('from Recipe as r left join fetch r.image \
    inner join fetch r.ingredients ingredients join fetch ingredients.ingredient \
    inner join fetch r.preparationTime pt \
    inner join fetch r.preparationTime.unit pu \
    inner join fetch r.cookingTime ct \
    inner join fetch r.cookingTime.unit cu \
    where r.id = :recipeId',[recipeId:id])

  }
  def show = {
    def recipe = getRecipe(params?.long("id"))
    def nutritionFacts = recipeService.nutritionFacts(recipe)
      // if there aren't equal number of nutritional facts as ingredients, then all of the nutritional facts aren't
      // know for the recipe, and so the nutrition facts will be sent as null
    nutritionFacts = nutritionFacts.size() == recipe.ingredients.size()?nutritionFacts:null
    render(view: 'show', model: ['nutrition':nutritionFacts,recipe: recipe, party: UserTools.currentUser?.party])
  }

  def printRecipes = {
    Integer noOfServings = params?.long("noOfServings")
    String customServingChecked = params?.customServings
    List<Recipe> recipes = []
    Boolean printOneRecipePerPage = true
    Integer customServings
    MenuPlan menuPlan = MenuPlan.get(params.menuPlanId)
    String menuPlanName = menuPlan ? menuPlan.name : "Minute Menu Plan"

    if (request.method == "POST") {
      switch (params.printRecipe) {
        case "PRINT_SELECTED_WEEKS":
          recipes = (params.fullWeek1) ? menuPlan?.weeks?.get(params.int('fullWeek1') - 1)?.recipes : []
          recipes += (params.fullWeek2) ? menuPlan?.weeks?.get(params.int('fullWeek2') - 1)?.recipes : []
          recipes += (params.fullWeek3) ? menuPlan?.weeks?.get(params.int('fullWeek3') - 1)?.recipes : []
          recipes += (params.fullWeek4) ? menuPlan?.weeks?.get(params.int('fullWeek4') - 1)?.recipes : []
          recipes = Recipe.getAll(recipes*.id)
          printOneRecipePerPage = false
          break;
        case "PRINT_SELECTED_RECIPES":
          recipes = Recipe.getAll(params?.list('recipeIds'))
          break;
      }
    } else {
      recipes = (params.ids) ? Recipe.getAll(params?.list('ids')) : Recipe.list()
      printOneRecipePerPage = false
    }
    recipes = recipes?.unique {it.id}
    if (noOfServings && customServingChecked) {
      customServings = noOfServings
    }
    [menuPlanName: menuPlanName, recipes: recipes, printOneRecipePerPage: printOneRecipePerPage, customServings: customServings, isPrintable: true]
  }

  def reportCommentAbuse = {
    LoginCredential user = UserTools.currentUser
    Comment comment = Comment.get(params.id)
    new CommentAbuse(comment: comment, reporter: user?.party).s()
    flash.message = "Abuse has been reported to the Administrator"
    redirect(action: 'show', id: params.recipeId)
  }
  def removeCommentAbuse = {
    Comment comment = Comment.get(params?.id)
    CommentAbuse commentAbuse = CommentAbuse.findByComment(comment)
    if (commentAbuse) {
      try {
        flash.message = "Removed comment abuse"
        commentAbuse.delete(flush: true)
      } catch (org.springframework.dao.DataIntegrityViolationException e) {
        commentAbuse.errors.allErrors.each {
          println it
        }
        flash.message = "Could not removed abuse on comment"
      }
    } else {
      flash.message = "No such comment abuse found."
    }
    redirect(controller: 'user', action: 'show', id: params.userId)
  }
  def reportRecipeAbuse = {
    RecipeAbuse recipeAbuse = new RecipeAbuse()
    recipeAbuse.recipe = Recipe.get(params?.id?.toLong())
    recipeAbuse.reporter = UserTools.currentUser?.party
    recipeAbuse.s()
    flash.message = "Abuse has been reported to the Administrator"
    redirect(action: 'show', id: params?.id)
  }
  def removeRecipeAbuse = {
    Recipe recipe = Recipe.get(params?.id)
    RecipeAbuse recipeAbuse = RecipeAbuse.findByRecipe(recipe)
    if (recipeAbuse) {
      try {
        flash.message = "Removed recipe abuse"
        recipeAbuse.delete(flush: true)
      } catch (ex) {
        flash.message = "Could not removed abuse on recipe"
      }
    } else {
      flash.message = "No such recipe abuse found."
    }
    redirect(controller: 'user', action: 'show', id: params.userId)
  }

  def selectRecipesToPrint = {
    MenuPlan menuPlan = MenuPlan.get(params.id)
    [menuPlan: menuPlan, noOfServings: UserTools.currentUser?.party?.subscriber?.mouthsToFeed ?: 1]

  }

  def refreshAlcoholicContentList = {
    List<String> elements = masterDataBootStrapService.populateAlcoholicContentList()
    render "Alcoholic Items List Updated"
  }
}
class RecipeCO {
    static config = ConfigurationHolder.config
    def recipeService
    def searchableService
    def errors = []
    RecipeCO() {} //constructor
    Long imageId
    Long id
    String name
    String difficulty
    String description
    Boolean shareWithCommunity
    Boolean isAlcoholic
    Integer makesServing
    Integer preparationTime
    Long preparationUnitId
    Integer cookTime         
    Long cookUnitId
    BigDecimal cost

    String selectRecipeImage
    String selectRecipeImagePath

    Set<Long> subCategoryIds = []
    Set<String> categoryNames = []

    Set<String> serveWithItems = []

    List<String> ingredientQuantities = []
    List<String> ingredientUnitIds = []
    List<String> ingredientProductIds = []
    List<String> ingredientPreparationMethodIds = []
    List<String> ingredientAisleIds = []
    List<String> ingredientFoodMapIds = []
    List<String> hiddenIngredientFoodMapNames = []
    List<String> hiddenIngredientUnitNames = []
    List<String> hiddenIngredientUnitSymbols = []
    List<String> hiddenIngredientProductNames = []
    List<String> hiddenIngredientPreparationMethodNames = []
    List<String> hiddenIngredientAisleNames = []

    List<String> directions = []

    List<Long> nutrientIds = []
    def nutrientQuantities = []



    void setNutrientQuantities(def listOfNq) {
        [listOfNq].flatten().each {
            try {nutrientQuantities << new Float(it)} catch (ex) {nutrientQuantities << it}
        }
    }

    static constraints = {
        id(nullable: true)
        name(validator: {val ->
            if (!val) {
                return 'recipeCO.name.blank.error.name'
            }
        })
        cost(nullable: true)
        difficulty(blank: true, nullable: true)
        description(blank: true, nullable: true)
        makesServing(nullable: true)
        selectRecipeImagePath(nullable: true, blank: true)
        selectRecipeImage(nullable: true, blank: true)
        preparationTime(nullable: true)
        cookTime(nullable: true)

        nutrientQuantities(validator: {val ->
            if (val.findAll {!(it instanceof Float || it == "")}.size() > 0) {
                return 'recipeCO.nutrientQuantities.matches.invalid.nutrientQuantities'
            }
        })

        ingredientQuantities(validator: { values ->
            for(value in values) {
                if (value && !value.isNumber()) {
                    try {
                        new ProperFractionFormat().parse(value)?.floatValue()
                    }
                    catch (Exception e) {
                        println "Exception caught"
                        return 'recipeCO.ingredientQuantities.validator.error.java.util.List'
                    }
                }
            }
        })

        hiddenIngredientProductNames(validator: {val, obj ->
            List<String> tempProd = []
            val.each { tempProd.add(it) }
            if (!val.any {it}) {
                return 'recipeCO.ingredient.not.Provided.message'
            }
            if ((val.size() != tempProd?.unique()?.size()) && !(val.contains(''))) {
                return 'recipeCO.ingredientProduct.Repeated.message'
            }
        })


        directions(validator: {val, obj ->
            if ((val.size() < 1) || (val.any {!it})) {
                return 'recipeCO.directions.not.valid.message'
            }
        })
    }

    public Recipe updateRecipe() {
        Recipe recipe = Recipe.get(id)
        recipe.name = name
        recipe.description = description
        recipe.shareWithCommunity = shareWithCommunity
        recipe.isAlcoholic = isAlcoholic
        recipe.servings = makesServing
        if (difficulty) {
            recipe.difficulty = RecipeDifficulty."${difficulty}"
        }
        recipe.preparationTime = makeTimeQuantity(preparationTime, preparationUnitId)
        recipe.cookingTime = makeTimeQuantity(cookTime, cookUnitId)
        Price price = recipe.avePrice
        if(!price && cost){
          Quantity quantity = new Quantity(value:1,unit:Unit.findByName('Each'),savedUnit:Unit.findByName('Each')).s()
          price = new Price(price:cost,quantity:quantity).s()
          recipe.avePrice = price
          price.price = cost
        }

        recipe.subCategories = []
        addSubCategoriesToRecipe(recipe, subCategoryIds)

        def tempIngredients = recipe.ingredients

//
//
//
//        def tempFoodMappings = tempIngredients.collect { ing ->
//          def mapping = NutritionLink.findByIngredient(ing)
//          println "Mapping ${mapping}"
//        }.flatten()
//      tempFoodMappings*.ingredient = null
//      tempFoodMappings*.save(flush: true)

        recipe.ingredients = []
        tempIngredients*.delete(flush: true)
        addIngredientsToRecipe(recipe, ingredientQuantities, ingredientUnitIds, hiddenIngredientProductNames, hiddenIngredientAisleNames, hiddenIngredientPreparationMethodNames)

        recipe.directions = []
        addDirectionsToRecipe(recipe, directions)

        addServeWithToRecipe(recipe, serveWithItems)

        def tempNutrients = recipe.nutrients
        recipe.nutrients = []
        tempNutrients*.delete(flush: true)
        addNutrientsToRecipe(recipe, nutrientQuantities, nutrientIds)

        recipe.s()
        if(price)price.s()
        attachImage(recipe, selectRecipeImagePath)
        recipe.isAlcoholic = recipe.isAlcoholic ? true : recipeService.isRecipeAlcoholic(recipe?.id)
        recipe.s()

        return recipe
    }

    public Recipe convertToRecipe(Party byUser) {
        Recipe recipe = new Recipe()

        recipe.name = name
        recipe.description = description
        recipe.shareWithCommunity = shareWithCommunity
        recipe.servings = makesServing
        recipe.difficulty = RecipeDifficulty."${difficulty}"
        recipe.preparationTime = makeTimeQuantity(preparationTime, preparationUnitId)
        recipe.cookingTime = makeTimeQuantity(cookTime, cookUnitId)
        recipe.isAlcoholic = isAlcoholic
        addSubCategoriesToRecipe(recipe, subCategoryIds)
        addDirectionsToRecipe(recipe, directions)
        addIngredientsToRecipe(recipe, ingredientQuantities, ingredientUnitIds, hiddenIngredientProductNames, hiddenIngredientAisleNames, hiddenIngredientPreparationMethodNames)
        addServeWithToRecipe(recipe, serveWithItems)

        recipe.s()
        attachImage(recipe, selectRecipeImagePath)
        recipe.isAlcoholic = recipe.isAlcoholic ? true : recipeService.isRecipeAlcoholic(recipe?.id)
        recipe.s()
        return recipe
    }

    public boolean attachImage(Recipe recipe, String imagePath) {
        List<Integer> imageSizes = RECIPE_IMAGE_SIZES
        return Image.updateOwnerImage(recipe, imagePath, imageSizes)
    }

    public Quantity makeTimeQuantity(Integer minutes, Long unitId) {
        if (minutes == null) {
            return null
        }
        Quantity time = new Quantity()
        Unit unit = Unit.get(unitId)
        time = StandardConversionService.getQuantityToSave(minutes.toString(), unit)
        time?.s()
        if (time) {
            return time
        } else {
            return null
        }
    }

    public List<RecipeIngredient> recipeIngredientList(List<String> amounts, List<String> unitIds, List<String> productNames, List<String> aisleNames, List<String> preparationMethodNames) {
        List<RecipeIngredient> recipeIngredients = []
        productNames?.eachWithIndex {String productName, Integer index ->
            if (productName) {
                Party party = UserTools.currentUser?.party
                RecipeIngredient recipeIngredient = new RecipeIngredient()
                Unit unit = (unitIds[index]) ? Unit?.get(unitIds[index]?.toLong()) : null
                Aisle aisle = null
                if (aisleNames[index]) {
                    aisle = getAisleForRecipeIngredient(aisleNames[index], party)
                }
                if (productName) {
                    Item product = getProductForRecipeIngredient(productName, party, unit, aisle)
                    PreparationMethod preparationMethod = getPreparationMethodForRecipeIngredient(preparationMethodNames[index])
                    Quantity quantity = StandardConversionService.getQuantityToSave(amounts?.getAt(index) ? amounts[index] : null, unit, product.density)
                    quantity?.s()
                    recipeIngredient.ingredient = product
                    recipeIngredient.quantity = quantity
                    recipeIngredient.preparationMethod = preparationMethod
                    recipeIngredient.aisle = aisle
                    recipeIngredients.add(recipeIngredient)
                }
            }
        }
        return recipeIngredients
    }

    public boolean addIngredientsToRecipe(Recipe recipe, List<String> amounts, List<String> unitIds, List<String> productNames, List<String> aisleNames, List<String> preparationMethodNames) {
        List<RecipeIngredient> recipeIngredients = recipeIngredientList(amounts, unitIds, productNames, aisleNames, preparationMethodNames)
        recipeIngredients?.eachWithIndex {RecipeIngredient recipeIngredient, Integer index ->
            recipeIngredient.recipe = recipe
            recipe.addToIngredients(recipeIngredient)
        }
        return true
    }

    public boolean addDirectionsToRecipe(Recipe recipe, List<String> directions) {
        directions = directions.findAll { it && it != "" }
        recipe.directions = directions
        return true
    }

    public List<RecipeNutrient> recipeNutrientList(def amounts, List<Long> nutrientIds) {
        List<RecipeNutrient> recipeNutrientList = []
        amounts.eachWithIndex {def amount, Integer index ->
            if (amount) {
                RecipeNutrient recipeNutrient = new RecipeNutrient()
                recipeNutrient.nutrient = Nutrient.get(nutrientIds[index])
                Quantity quantity = StandardConversionService.getQuantityToSave(amount?.toInteger()?.toString(), Nutrient.get(nutrientIds[index]).preferredUnit)
                quantity?.s()
                recipeNutrient.quantity = quantity
                recipeNutrientList.add(recipeNutrient)
            }
        }
        return recipeNutrientList
    }

    public boolean addNutrientsToRecipe(Recipe recipe, def amounts, List<Long> nutrientIds) {
        List<RecipeNutrient> recipeNutrients = recipeNutrientList(amounts, nutrientIds)
        recipeNutrients.eachWithIndex {RecipeNutrient nutrient, Integer index ->
            nutrient.recipe = recipe
            recipe.addToNutrients(nutrient)
        }
        return true
    }

    public List<Item> serveWithList(Set<Long> itemIds) {
        List<Item> items = []
        itemIds.each {Long itemId ->
            Item item = Item.get(itemId)
            items.add(item)
        }
        return items
    }

    public boolean addServeWithToRecipe(Recipe recipe, Set<String> itemIds) {
        Set<Item> items = []
        Party party = UserTools.currentUser?.party
        Item item
        itemIds.each {String itemName ->
            if (itemName) {
                item = Recipe.findByName(itemName)
                if (item) {
                    items.add(item)
                } else {
                    item = Item.countByName(itemName) ? Item.findByName(itemName) : new Product(name: itemName).s()
                    party.addToIngredients(item)
                    party.s()
                    items.add(item)
                    Item.countByName(itemName) ? searchableService.reindex(class: Item, item?.id) : searchableService.index(class: Product, item?.id)
                }
            }
        }
        recipe.items = items
        return true
    }

    public boolean addSubCategoriesToRecipe(Recipe recipe, Set<Long> subCategoryIds) {
        subCategoryIds.each {Long categoryId ->
            if (categoryId) {
                SubCategory category = SubCategory.get(categoryId)
                recipe.addToSubCategories(category)
            }
        }
        return true
    }

    public Item getProductForRecipeIngredient(String productName, Party party, Unit unit, Aisle aisle) {
        List<Item> products = recipeService.getItemsForCurrentUser()
        Item product = products.find {it.name == productName}
        if (!product) {
            product = Product.findByName(productName)
            if (product) {
                party.addToIngredients(product)
                party.s()
                searchableService.reindex(class: Product, product?.id)
            } else {
                if (unit) {
                    product = new MeasurableProduct(name: productName, isVisible: false, preferredUnit: unit, suggestedAisle: aisle)
                    product.s()
                    party.addToIngredients(product)
                    party.s()
                    searchableService.index(class: MeasurableProduct, product?.id)
                } else {
                    product = new Product(name: productName, isVisible: false, suggestedAisle: aisle)
                    product.s()
                    party.addToIngredients(product)
                    party.s()
                    searchableService.index(class: Product, product?.id)
                }
            }
        }
        return product
    }

    public Aisle getAisleForRecipeIngredient(String aisleName, Party party) {
        List<Aisle> aislesForUser = Aisle.getAislesForCurrentUser()
        Aisle aisle = aislesForUser.find {it.name == aisleName}
        if (!aisle) {
            Aisle aisleInList = Aisle.list().find {it.name == aisleName}
            if (aisleInList) {
                aisleInList.ownedByUser = true
                aisleInList.s()
                party.addToAisles(aisleInList)
                party.s()
            } else {
                aisle = new Aisle(name: aisleName, ownedByUser: true)
                aisle.s()
                party.addToAisles(aisle)
                party.s()
            }
        }
        return aisle
    }

    public PreparationMethod getPreparationMethodForRecipeIngredient(String methodString) {
        String preparationMethodString = methodString?.trim()
        PreparationMethod preparationMethod = (preparationMethodString) ? PreparationMethod.findByName(preparationMethodString) : null
        if (!preparationMethod && preparationMethodString) {
            preparationMethod = new PreparationMethod(name: preparationMethodString).s()
        }
        return preparationMethod
    }
}

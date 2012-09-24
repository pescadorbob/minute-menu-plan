package com.mp.domain

import org.codehaus.groovy.grails.commons.ConfigurationHolder
import static com.mp.MenuConstants.*
import org.apache.commons.math.fraction.ProperFractionFormat
import com.mp.domain.party.Party
import com.mp.tools.UserTools
import org.apache.lucene.document.NumberTools
import com.mp.domain.ndb.NutritionLink
import com.mp.domain.ndb.ItemNutritionLink
import com.mp.domain.ndb.IngredientNutritionLink
import java.util.regex.Pattern
import com.mp.domain.party.Party
import com.mp.tools.UserTools
import com.mp.tools.UnitUtil
import com.mp.domain.pricing.ItemPrice
import com.mp.domain.pricing.PriceType


class RecipeService {

  static config = ConfigurationHolder.config
  boolean transactional = true
  def profilerLog

  def nutritionFacts(Recipe recipe){

    def query =
    "select link, ri \
from NutritionLink link, RecipeIngredient ri \
inner join ri.quantity as riq \
left join riq.savedUnit as hidden_unit \
left join riq.unit as ri_unit \
where ri.recipe = :recipe \
and link.product = ri.ingredient \
and ((ri.preparationMethod is null and link.prep is null) \
 or (ri.preparationMethod = link.prep))"

    def map = [recipe: recipe]
    def nutrition = NutritionLink.executeQuery(query, map)
    def linksMap = [:]
    nutrition.each {link ->
      def conversion = link[0].unit ? getStandardConversion(link[0].unit) : null
      linksMap[link[1].id] = [link[0], link[1], conversion]
    }
    linksMap
  }

  private StandardConversion getStandardConversion(Unit unit) {
    def query =
    "select link_conversion from StandardConversion link_conversion \
where link_conversion.targetUnit.symbol = 'mL' \
 and link_conversion.sourceUnit = :unit"
    def map = [unit: unit]
    def conversions = StandardConversion.executeQuery(query, map)
    if (conversions.size() > 0) {
      conversions[0]
    } else null
  }

  public boolean deleteRecipe(Recipe recipe, Party user) {
    user.removeFromContributions(recipe)
    user.s()
    recipe.delete()
    return true
  }

  public String fuzzySearchQuery(String query, String searchKeyword) {
    String oldKeyword = '*' + searchKeyword + '*'
    String newKeyword = searchKeyword + '~'
    return query.replace(oldKeyword, newKeyword)
  }

  public List<Recipe> getFilteredRecipeList(Integer max = 15, Long offset = 0, Party currentUser) {
    String query = "(shareWithCommunity:true${(currentUser ? (" OR contributorsString:" + NumberTools.longToString(currentUser?.id)) : '')})"
    if (!currentUser?.showAlcoholicContent) { query += "  isAlcoholic:false" }
    def recipes = Recipe.search([reload: false, max: max, offset: offset]) {
      must(queryString(query))
    }


    return recipes?.results
  }

  public Integer getFilteredRecipeCount(Party currentUser) {
    String query = "(shareWithCommunity:true${(currentUser ? (" OR contributorsString:" + NumberTools.longToString(currentUser?.id)) : '')})"
    if (!currentUser?.showAlcoholicContent) { query += "  isAlcoholic:false" }
    def recipes = Recipe.search([reload: true]) {
      must(queryString(query))
    }
    return recipes?.total
  }

  public List<Item> getFilteredItemList(Integer max = 15, Long offset = 0) {
    Party currentUser = UserTools.currentUser?.party
    String query = "(shareWithCommunity:true${(currentUser ? (" OR contributorsString:" + NumberTools.longToString(currentUser?.id)) : '')})"
    if (!currentUser?.showAlcoholicContent) { query += "  isAlcoholic:false" }
    def items = Item.search([reload: true, max: max, offset: offset]) {
      must(queryString(query))
    }
    return items?.results
  }

  public Integer getFilteredItemCount() {
    Party currentUser = UserTools.currentUser?.party
    String query = "(shareWithCommunity:true${(currentUser ? (" OR contributorsString:" + NumberTools.longToString(currentUser?.id)) : '')})"
    if (!currentUser?.showAlcoholicContent) { query += "  isAlcoholic:false" }
    def items = Item.search([reload: true]) {
      must(queryString(query))
    }
    return items?.total
  }

  List<RecipeIngredient> getRecipeIngredientsWithCustomServings(Recipe recipe, int customServings) {
    List<RecipeIngredient> newRecipeIngredients = []
    List<RecipeIngredient> ingredients = recipe.ingredients
    newRecipeIngredients = getIngredientsForVisibleItems(ingredients, recipe, customServings)
    return newRecipeIngredients
  }

  List<RecipeIngredient> getIngredientsForVisibleItems(List<RecipeIngredient> visibleItems, Recipe recipe, int customServings) {
    List<RecipeIngredient> recipeIngredients = []
    visibleItems.each {RecipeIngredient recipeIngredient ->
      RecipeIngredient recipeIngredientNew = new RecipeIngredient()
      Item item = new Item()
      item.name = recipeIngredient?.ingredient?.name
        item.id = recipeIngredient?.ingredient?.id
      recipeIngredientNew.ingredient = item
      recipeIngredientNew.id = recipeIngredient?.id
      Quantity quantity = new Quantity()
      quantity.unit = recipeIngredient?.quantity?.unit
      quantity.value = recipeIngredient?.quantity?.value
      quantity.savedUnit = recipeIngredient?.quantity?.savedUnit
      recipeIngredientNew.quantity = quantity

      recipeIngredientNew.aisle = recipeIngredient.aisle
//            recipeIngredientNew.foodMapping = recipeIngredient.foodMapping
      recipeIngredientNew.preparationMethod = recipeIngredient?.preparationMethod
      if (customServings && recipeIngredient?.quantity && recipeIngredient?.quantity?.value && (customServings != recipe?.servings)) {
        Float value = (recipeIngredientNew?.quantity?.value) ? recipeIngredientNew.quantity.value : 1.0f
        Integer servings = recipeIngredient?.recipe?.servings
        recipeIngredientNew.quantity.value = ((customServings * value) / servings).toFloat()
        if (!recipeIngredientNew.quantity.savedUnit || !recipeIngredientNew.quantity.savedUnit.isConvertible) {
          recipeIngredientNew.quantity.value = Math.ceil(recipeIngredientNew.quantity.value)
        }
      }
      recipeIngredients.add(recipeIngredientNew)
    }
    return recipeIngredients
  }

  boolean isRecipeAlcoholic(Long recipeId) {
    Boolean result = false
    Recipe recipe1 = Recipe.get(recipeId)
    String recipeString = getDetailRecipeString(recipe1)
    List<String> alcoholicStrings = config.alcoholicContentList ? config.alcoholicContentList : []
    alcoholicStrings = alcoholicStrings*.toLowerCase()
    result = alcoholicStrings.any {
      def pattern = /\b${it}\b/
      def matcher = recipeString =~ pattern
      return matcher.getCount() ? true : false
    }
    return result
  }

  String getDetailRecipeString(Recipe recipe) {
    List<String> allStrings = []
    String detailedString = ''
    String name = recipe.toString()
    allStrings.add(name)
    allStrings.add(recipe?.ingredientsString)
    allStrings.add(recipe?.serveWithString)
    allStrings.add(recipe?.subCategoriesString)
    allStrings.add(recipe?.aislesString)
    allStrings.add(recipe?.preparationMethodString)
    if (recipe?.directions?.size()) {
      allStrings.add(recipe?.directions?.join(','))
    }
    if (recipe?.description) {
      allStrings.add(recipe?.description)
    }
    detailedString = allStrings.join(',')
    return detailedString.toLowerCase()
  }

  def nutrients = [NUTRIENT_CALORIES, NUTRIENT_TOTAL_FAT, NUTRIENT_SATURATED_FAT, NUTRIENT_CHOLESTEROL, NUTRIENT_SODIUM, NUTRIENT_CARBOHYDRATES, NUTRIENT_FIBER, NUTRIENT_PROTEIN]


  public RecipeCO initRecipeCO(Recipe recipe) {
    RecipeCO recipeCO = new RecipeCO();
    recipeCO.id = recipe?.id
    recipeCO.imageId = recipe?.image?.id
    recipeCO.name = recipe?.name
    recipeCO.description = recipe?.description

    if (recipe?.image) {
      int firstIndex = recipe?.image?.storedName?.indexOf('.')
      String name = recipe?.image?.storedName?.substring(0, firstIndex)
      recipeCO.selectRecipeImagePath = recipe?.image?.path + name + "_640.jpg"
    } else {
      recipeCO.selectRecipeImagePath = ''
    }

    recipeCO.difficulty = recipe?.difficulty?.name()
    recipeCO.shareWithCommunity = recipe?.shareWithCommunity
    recipeCO.makesServing = recipe?.servings
    recipeCO.serveWithItems = (recipe?.items) ? (recipe?.items*.name) : []
    recipeCO.isAlcoholic = recipe?.isAlcoholic

      def c = ItemPrice.createCriteria()

    ItemPrice [] itemPrices = c {
          priceOf {
              eq('id',recipe.id)
          }
          eq('type',PriceType.AVE)
      }
      if(itemPrices?.size()>0){
          recipeCO.cost = itemPrices.first().price.price
      }

    recipeCO.preparationUnitId = recipe?.preparationTime?.unit?.id
    recipeCO.preparationTime = recipe?.preparationTime ? UnitUtil.getQuantityValueString(recipe?.preparationTime)?.toInteger() : null

    recipeCO.cookUnitId = recipe?.cookingTime?.unit?.id
    recipeCO.cookTime = recipe?.cookingTime ? UnitUtil.getQuantityValueString(recipe?.cookingTime)?.toInteger() : null

    recipeCO.subCategoryIds = recipe?.subCategories*.id as Set
    recipeCO.directions = recipe?.directions

    recipeCO.hiddenIngredientProductNames = recipe?.ingredients*.ingredient?.name
    recipeCO.ingredientProductIds = recipe?.ingredients*.ingredient?.id

    recipe?.ingredients*.aisle?.each {Aisle aisle ->
      recipeCO.ingredientAisleIds.add(aisle?.id?.toString())
      recipeCO.hiddenIngredientAisleNames.add(aisle?.name)
    }

    recipe?.ingredients.each {RecipeIngredient ri ->
      def mapping = IngredientNutritionLink.findByIngredient(ri)
      if (!mapping) {
        def query = "from ItemNutritionLink as link where \
            link.product = :product and "
        def map = [product: ri.ingredient]
        if (ri.preparationMethod) {
          query += " prep = :prep "
          map.prep = ri.preparationMethod
        } else {
          query += " prep is null "
        }
        if (ri.quantity?.unit) {
          query += " and (unit.isConvertible=1  or unit = :unit )"
          map.unit = ri.quantity.unit
        } else {
          query += " and unit is null"
        }
        def links = ItemNutritionLink.executeQuery("select distinct link " + query, map)
        if (links.size() > 0) mapping = links[0]
      }
      recipeCO.ingredientFoodMapIds.add(mapping?.id?.toString())
      recipeCO.hiddenIngredientFoodMapNames.add(mapping ? ("${mapping?.nutrition?.weightFor?.shrtDesc} ${mapping?.nutrition?.msreDesc} ") : '')
    }


    recipe?.ingredients*.preparationMethod?.each {PreparationMethod preparationMethod ->
      recipeCO.ingredientPreparationMethodIds.add(preparationMethod?.id?.toString())
      recipeCO.hiddenIngredientPreparationMethodNames.add(preparationMethod?.name)
    }
    recipe?.ingredients*.quantity?.unit?.eachWithIndex {Unit unit, Integer index ->
      recipeCO.ingredientUnitIds.add(unit?.id)
      recipeCO.hiddenIngredientUnitNames.add(unit?.name)
      recipeCO.hiddenIngredientUnitSymbols.add(unit?.symbol)
    }

    recipe?.ingredients*.quantity?.value?.eachWithIndex {Float val, Integer index ->
      String usValue = UnitUtil.getQuantityValueString(recipe?.ingredients?.getAt(index)?.quantity)
      recipeCO.ingredientQuantities.add(usValue)
    }

    recipeCO.nutrientIds = Nutrient.list()*.id
    Nutrient.count().times {
      recipeCO.nutrientQuantities[it] = ""
    }
    recipe?.nutrients.each {RecipeNutrient recipeNutrient ->
      Integer val = UnitUtil.getQuantityValueString(recipeNutrient?.quantity)?.toBigDecimal()
      recipeCO.nutrientQuantities[recipeNutrient?.nutrient?.id?.toInteger() - 1] = val
    }
    recipeCO
  }

  public List<Item> getItemsForCurrentUser(String matches = "%%") {
    Party party = UserTools.currentUser?.party
    List<Item> itemsByUser = getItemsForUser(party, matches)
    return itemsByUser
  }

  public List<Item> getItemsForUser(Party party, String matchString = "%%") {
    if (profilerLog?.profiling) profilerLog.startProfiling("getItemsForUser")
    Boolean showAlcoholicContent = party.showAlcoholicContent
    List<Item> totalItems = []
    List<Item> items = []
    List<Item> itemsByUser = []
    List<Item> recipesByUser = []
    totalItems = Item.withCriteria(unique: true) {
      if (!party.showAlcoholicContent)
        eq('isAlcoholic', false)
      or {
        eq('shareWithCommunity', true)
        if (party?.ingredients.size() > 0)
          inList('id', party?.ingredients*.id)
        if (party?.contributions.size() > 0)
          inList('id', party?.contributions*.id)
      }
      if (matchString != '%%') ilike('name', matchString)
    }
//      if (!party.showAlcoholicContent) {
//          if(matchString == '%%'){
//              items = Item?.findAllByIsAlcoholic(false) as List
//              items.addAll(Recipe?.findAllByIsAlcoholic(false) as List)
//              items.addAll(Product?.findAllByIsAlcoholic(false) as List)
//              items.addAll(MeasurableProduct?.findAllByIsAlcoholic(false) as List)
//          } else {
//              items = Item?.findAllByNameIlikeAndIsAlcoholic(matchString, false) as List
//              items.addAll(Recipe?.findAllByNameIlikeAndIsAlcoholic(matchString, false) as List)
//              items.addAll(Product?.findAllByNameIlikeAndIsAlcoholic(matchString, false) as List)
//              items.addAll(MeasurableProduct?.findAllByNameIlikeAndIsAlcoholic(matchString, false) as List)
//          }
//          items = items?.findAll {it?.shareWithCommunity} as List
//          matchString = matchString.replace("%", ".*")
//          itemsByUser = party?.ingredients?.findAll {!it.isAlcoholic && it?.name?.matches(Pattern.compile(matchString, Pattern.CASE_INSENSITIVE))} as List
//          recipesByUser = (party?.contributions as List).findAll {it?.name?.matches(Pattern.compile(matchString, Pattern.CASE_INSENSITIVE)) && (!it.isAlcoholic)} as List
//      } else {
//          items = Item?.findAllByNameIlike(matchString)?.findAll {it?.shareWithCommunity} as List
//          matchString = matchString.replace("%", ".*")
//          itemsByUser = party?.ingredients?.findAll {it?.name?.matches(Pattern.compile(matchString, Pattern.CASE_INSENSITIVE))} as List
//          recipesByUser = (party?.contributions as List).findAll {it?.name?.matches(Pattern.compile(matchString, Pattern.CASE_INSENSITIVE))} as List
//      }
//      totalItems = items + itemsByUser + recipesByUser
//      totalItems.unique()
    return totalItems.sort {it.name}
    if (profilerLog?.profiling) profilerLog.stopProfiling()

  }

  public List<Item> getProductsForCurrentUser(String matches = "%%") {
    Party party = UserTools.currentUser?.party
    List<Item> itemsByUser = getProductsForUser(party, matches)
    return itemsByUser
  }

  public List<Item> getProductsForUser(Party party, String matchString = "%%") {
    List<Item> totalItems = []
    List<Item> items = []
    List<Item> itemsByUser = []
    if (!party.showAlcoholicContent) {
      items = Product?.findAllByNameIlikeAndIsAlcoholic(matchString, false)?.findAll {it?.shareWithCommunity} as List
      items += MeasurableProduct?.findAllByNameIlikeAndIsAlcoholic(matchString, false)?.findAll {it?.shareWithCommunity} as List
      matchString = matchString.replace("%", ".*")
      itemsByUser = party?.ingredients?.findAll {it?.name?.matches(Pattern.compile(matchString, Pattern.CASE_INSENSITIVE)) && (!it.isAlcoholic)} as List
    } else {
      items = Product?.findAllByNameIlike(matchString)?.findAll {it?.shareWithCommunity} as List
      items += MeasurableProduct?.findAllByNameIlikeAndIsAlcoholic(matchString, false)?.findAll {it?.shareWithCommunity} as List
      matchString = matchString.replace("%", ".*")
      itemsByUser = party?.ingredients?.findAll {it?.name?.matches(Pattern.compile(matchString, Pattern.CASE_INSENSITIVE))} as List
    }
    totalItems = items + itemsByUser
    return totalItems.sort {it.name}
  }
}

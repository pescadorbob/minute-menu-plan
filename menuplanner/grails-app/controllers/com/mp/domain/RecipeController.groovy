package com.mp.domain

import org.codehaus.groovy.grails.commons.ConfigurationHolder
import grails.converters.JSON
import static com.mp.MenuConstants.*

import org.grails.comments.Comment
import org.apache.lucene.document.NumberTools
import javax.servlet.http.Cookie
import com.mp.domain.party.Party
import com.mp.tools.UserTools
import com.mp.domain.party.Coach
import grails.util.GrailsUtil
import com.mp.email.Tag

class RecipeController {
  static config = ConfigurationHolder.config
  def recipeService
  def masterDataBootStrapService
  def searchableService
  def userService
  def asynchronousMailService

  static allowedMethods = [save: "POST", update: "POST"]

  def index = {
    redirect(action: "list")
  }

  def getMatchingItems = {
    List<Item> items = Item.getItemsForCurrentUser("%${params.q}%")
    String itemsJson = ''
    items.each {
      String name = it.name.replaceAll("'", "\\\'")
      itemsJson += name + "|" + it.id + "\n"
    }
    render(itemsJson)
  }

  def getMatchingProducts = {
    List<Item> items = Item.getProductsForCurrentUser("%${params.q}%")
    String itemsJson = ''
    items.each {
      String name = it.name.replaceAll("'", "\\\'")
      itemsJson += name + "|" + it.id + "\n"
    }
    render(itemsJson)
  }

  def list = {
    params.max = Math.min(params.max ? params.int('max') : 15, 150)
    params.offset = Math.min(params.offset ? params.int('offset') : 0, 100)
    List<Recipe> filteredResults = recipeService.getFilteredRecipeList(params.max, params.long('offset'))
    Integer total = recipeService.getFilteredRecipeCount()
    List<SubCategory> subCategories = (Recipe.list()*.subCategories)?.flatten()?.unique {it.id}?.sort {it.name}
    List<Category> categories = (subCategories*.category)?.flatten()?.unique {it.id}?.sort {it.name}
    render(view: 'list', model: [recipeList: filteredResults, categories: categories, subCategories: subCategories, recipeTotal: total])
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
    def searchList = Recipe.search([reload: true, max: 15, offset: params.offset ? params.long('offset') : 0]) {
      must(queryString(query))
    }
    results = searchList?.results
    total = searchList?.total

    if (!results && keyword) {
      String newQuery = recipeService.fuzzySearchQuery(query, keyword)
      searchList = Recipe.search([reload: true, max: 15, offset: params.offset ? params.long('offset') : 0]) {
        must(queryString(newQuery))
      }
      results = searchList?.results
      total = searchList?.total
    }
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
      Recipe recipe = Recipe.get(params.long('id'))
      RecipeCO recipeCO = new RecipeCO(recipe)
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

  def show = {
    Recipe recipe = Recipe.findById(params?.id)
    render(view: 'show', model: [recipe: recipe, party: UserTools.currentUser?.party])
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

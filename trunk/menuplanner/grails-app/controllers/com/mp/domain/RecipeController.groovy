package com.mp.domain

import org.codehaus.groovy.grails.commons.ConfigurationHolder
import grails.converters.JSON
import static com.mp.MenuConstants.*

import org.grails.comments.Comment

class RecipeController {
    static config = ConfigurationHolder.config
    def recipeService

    static allowedMethods = [save: "POST", update: "POST"]

    def index = {
        redirect(action: "list")
    }

    def getMatchingProducts = {
        List<Product> products = Product.findAllByNameIlike("%${params.query}%")
        List productsJson = products.collect { [id: it.id, name: it.name] }
        render(productsJson as JSON)
    }

    def getMatchingCategories = {
        List<Category> categories = Category.findAllByNameIlike("%${params.query}%")
        List categoriesJson = categories.collect { [id: it.id, name: it.name] }
        render(categoriesJson as JSON)
    }

    def getMatchingItems = {
        List<Item> items = Item.findAllByNameIlike("%${params.q}%")
        String itemsJson = ''
        items.each {
            itemsJson += it.name + "|" + it.id + "\n"
        }
        render(itemsJson)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 15, 150)
        List<Recipe> recipeList = Recipe.list(params)
        List<SubCategory> subCategories = (Recipe.list()*.subCategories)?.flatten()?.unique {it.id}?.sort {it.name}
        List<Category> categories = (subCategories*.category)?.flatten()?.unique {it.id}?.sort {it.name}
        render(view: 'list', model: [recipeList: recipeList, categories: categories, subCategories: subCategories, recipeTotal: Recipe.count()])
    }

    def search = {
        List<String> allQueries = []
        List<String> subCategoriesString = []
        String subQueryString
        String searchKeyword = ''
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
            if (!(myQ.contains(':'))) {
                allQueries[index] = '*' + myQ + '*'
                searchKeyword = myQ
            }
        }

        if (subCategoriesString) {
            subQueryString = subCategoriesString.join('" OR "')
            subQueryString = '"' + subQueryString + '"'
            allQueries.push('subCategoriesString:' + subQueryString)
        }
        List<Recipe> results = []
        String query = allQueries?.join(" ")?.tokenize(", ")?.join(" ")
        if (query.startsWith('[')) {
            query = query.substring(1, query.length() - 1)
        }
        Integer total

        if (query && (query != 'null')) {
            def searchList = Recipe.search([reload: true, max: 15, offset: params.offset ? params.long('offset') : 0]) {
                must(queryString(query))
            }
            results = searchList?.results
            total = searchList?.total
            if (!results) {
                String newQuery = recipeService.fuzzySearchQuery(query, searchKeyword)
                searchList = Recipe.search([reload: true, max: 15, offset: params.offset ? params.long('offset') : 0]) {
                    must(queryString(newQuery))
                }
                results = searchList?.results
                total = searchList?.total
            }
        } else {
            params.max = 15
            results = Recipe.list(params)
            total = Recipe.count()
        }
        render(template: '/recipe/searchResultRecipe', model: [recipeList: results, recipeTotal: total, query: params.query])
    }

    def delete = {
        def recipe = Recipe.get(params.id)
        LoginCredential loggedUser = LoginCredential.currentUser
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
//            List<String> elementNames = params.list('hiddenIngredientProductNames')
//            List<String> serveWithNames = params.list('serveWithItems')
//            elementNames = elementNames*.toLowerCase()
//            serveWithNames = serveWithNames*.toLowerCase()
//            recipeCO.updateRecipe(elementNames, serveWithNames)
            recipeCO.updateRecipe()
            redirect(action: 'show', id: recipeCO?.id)
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
            LoginCredential loggedUser = LoginCredential.currentUser
//            List<String> elementNames = params.list('hiddenIngredientProductNames')
//            List<String> serveWithNames = params.list('serveWithItems')
//            elementNames = elementNames*.toLowerCase()
//            serveWithNames = serveWithNames*.toLowerCase()
//            Recipe recipe = recipeCO.convertToRecipe(loggedUser?.party, elementNames, serveWithNames)
            Recipe recipe = recipeCO.convertToRecipe(loggedUser?.party)
            loggedUser?.party?.addToContributions(recipe)
            loggedUser.party?.s()
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
        LoginCredential user = LoginCredential.currentUser
        recipe?.addComment(user?.party, params?.comment)
        redirect(action: 'show', controller: 'recipe', params: [id: recipe?.id])
    }

    def show = {
        Recipe recipe = Recipe.findById(params?.id)
        render(view: 'show', model: [recipe: recipe])
    }

    def printRecipes = {
        List<Recipe> recipes = []
        Boolean printOneRecipePerPage = true
        if (request.method == "POST") {
            switch (params.printRecipe) {
                case "PRINT_SELECTED_WEEKS":
                    MenuPlan menuPlan = MenuPlan.get(params.menuPlanId)
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
        Integer customServings = LoginCredential.currentUser.party?.subscriber?.mouthsToFeed
        [recipes: recipes, printOneRecipePerPage: printOneRecipePerPage, customServings: customServings, isPrintable: true]
    }

    def reportCommentAbuse = {
        LoginCredential user = LoginCredential.currentUser
        Comment comment = Comment.get(params.id)
        new CommentAbuse(comment: comment, reporter: user?.party).s()
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
        recipeAbuse.reporter = LoginCredential.currentUser?.party
        recipeAbuse.s()
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
                recipeAbuse.errors.allErrors.each {
                    println it
                }
                flash.message = "Could not removed abuse on recipe"
            }
        } else {
            flash.message = "No such recipe abuse found."
        }
        redirect(controller: 'user', action: 'show', id: params.userId)
    }

    def selectRecipesToPrint = {
        MenuPlan menuPlan = MenuPlan.get(params.id)
        [menuPlan: menuPlan]

    }
}
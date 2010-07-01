package com.mp.domain

import org.codehaus.groovy.grails.commons.ConfigurationHolder
import grails.converters.JSON
import static com.mp.MenuConstants.*
import org.codehaus.groovy.grails.commons.ApplicationHolder
import org.grails.comments.Comment

class RecipeController {
    static config = ConfigurationHolder.config
    def recipeService

    static allowedMethods = [save: "POST", update: "POST"]

    def index = {
        redirect(action: "list", params: params)
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
        Integer listSize = Recipe.count()
        params.max = Math.min(params.max ? params.int('max') : 15, 150)
        List<Category> categoryList = Category.list()
        List<Recipe> recipeList = Recipe.list(params)
        render(view: 'list', model: [recipeList: recipeList, categoryList: categoryList, recipeTotal: Recipe.count()])
    }

    def search = {
        List<String> allQueries = []
        params.query = (params.query == 'null') ? '' : params.query
        params?.list("query")?.eachWithIndex {String myQ, Integer index ->
            allQueries.push(myQ)
            if (!(myQ.contains(':'))) {
                allQueries[index] = '*' + myQ + '*'
            }
        }
        List<Recipe> results = []
        String query = allQueries?.join(" ")
        Integer total
        if (query && (query != 'null')) {
            def searchList = Recipe.search([reload: true, max: 15, offset: params.offset ?: 0]) {
                must(queryString(query))
            }
            results = searchList?.results
            total = searchList?.total
        } else {
            params.max = 15
            results = Recipe.list(params)
            total = Recipe.count()
        }
        render(template: '/recipe/searchResultRecipe', model: [recipeList: results, recipeTotal: total, query: params.query])
    }

    def delete = {
        def recipe = Recipe.get(params.id)
        User loggedUser = User.currentUser
        if (recipe) {
            try {
                flash.message = message(code: 'recipe.deleted.success')
                recipeService.deleteRecipe(recipe, loggedUser)
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
        List<Category> categories = Category.list()
        render(view: 'create', model: [timeUnits: sys.timeUnits, metricUnits: Unit.sortedMetricUnits, nutrients: nutrients,
                categories: categories, aisles: Aisle.list(), preparationMethods: PreparationMethod.list()])
    }

    def edit = {
        if (params.id) {
            Recipe recipe = Recipe.get(params.long('id'))
            RecipeCO recipeCO = new RecipeCO(recipe)
            SystemOfUnit sys = SystemOfUnit.findBySystemName(SYSTEM_OF_UNIT_USA)
            List<Nutrient> nutrients = Nutrient.list()
            List<Category> categories = Category.list()
            render(view: 'edit', model: [recipeCO: recipeCO, timeUnits: sys.timeUnits, metricUnits: Unit.sortedMetricUnits, nutrients: nutrients,
                    categories: categories, aisles: Aisle.list(), preparationMethods: PreparationMethod.list()])
        }
    }

    def update = {RecipeCO recipeCO ->
        if (recipeCO.validate()) {
            recipeCO.updateRecipe()
            redirect(action: 'show', id: recipeCO?.id)
        } else {
            println recipeCO.errors.allErrors.each {
                println it
            }
            SystemOfUnit sys = SystemOfUnit.findBySystemName(SYSTEM_OF_UNIT_USA)
            List<Nutrient> nutrients = Nutrient.list()
            List<Category> categories = Category.list()
            render(view: 'edit', model: [recipeCO: recipeCO, timeUnits: sys.timeUnits, metricUnits:  Unit.sortedMetricUnits, nutrients: nutrients,
                    categories: categories, aisles: Aisle.list(), preparationMethods: PreparationMethod.list()])
        }
    }

    def save = {RecipeCO recipeCO ->
        if (recipeCO.validate()) {
            User loggedUser = User.currentUser
            Recipe recipe = recipeCO.convertToRecipe(loggedUser)
            loggedUser.addToContributions(recipe)
            loggedUser.s()
            redirect(action: 'show', id: recipe?.id)
        } else {
            recipeCO.errors.allErrors.each {
                println it
            }
            SystemOfUnit sys = SystemOfUnit.findBySystemName(SYSTEM_OF_UNIT_USA)
            List<Nutrient> nutrients = Nutrient.list()
            List<Category> categories = Category.list()
            render(view: 'create', model: [recipeCO: recipeCO, timeUnits: sys.timeUnits, metricUnits: Unit.sortedMetricUnits, nutrients: nutrients,
                    categories: categories, aisles: Aisle.list(), preparationMethods: PreparationMethod.list()])
        }
    }

    def addComment = {
        Recipe recipe = Recipe.findById(params?.recipeId)
        User user = User.currentUser
        recipe?.addComment(user, params?.comment)
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
        [recipes: recipes, printOneRecipePerPage: printOneRecipePerPage]
    }

    def uploadImage = {
        String relativePath = "/tempRecipe"
        def fileContents = params.Filedata.bytes
        String filePath = config.imagesRootDir + relativePath
        File file = new File(filePath)
        file.mkdirs()
        File actualFile = new File(file, 'Img_' + System.currentTimeMillis()?.toString() + '.' + params.Filename.tokenize('.').tail().join('.'))
        actualFile.withOutputStream {out ->
            out.write fileContents
        }
        render actualFile.absolutePath as String
    }

    def reportCommentAbuse = {
        User user = User.currentUser
        Comment comment = Comment.get(params.id)
        new CommentAbuse(comment: comment, reporter: user).s()
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
        recipeAbuse.reporter = User.currentUser
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
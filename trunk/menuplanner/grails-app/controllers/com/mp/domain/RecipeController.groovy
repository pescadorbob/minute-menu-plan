package com.mp.domain

import grails.converters.JSON

class RecipeController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }
    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [recipeList: Recipe.list(params), recipeTotal: Recipe.count()]
    }
    def create = {
        def recipe = new Recipe()
        recipe.properties = params
        return [recipe: recipe]
    }
    def save = {
        def recipe = new Recipe(params)
        if (recipe.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'recipe.label', default: 'Recipe'), recipe.id])}"
            redirect(action: "show", id: recipe.id)
        }
        else {
            render(view: "create", model: [recipe: recipe])
        }
    }
    def show = {
        def recipe = Recipe.get(params.id)
        if (!recipe) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'recipe.label', default: 'Recipe'), params.id])}"
            redirect(action: "list")
        }
        else {
            [recipe: recipe]
        }
    }
    def edit = {
        def recipe = Recipe.get(params.id)
        if (!recipe) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'recipe.label', default: 'Recipe'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [recipe: recipe]
        }
    }
    def update = {
        def recipe = Recipe.get(params.id)
        if (recipe) {
            if (params.version) {
                def version = params.version.toLong()
                if (recipe.version > version) {

                    recipe.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'recipe.label', default: 'Recipe')] as Object[], "Another user has updated this Recipe while you were editing")
                    render(view: "edit", model: [recipe: recipe])
                    return
                }
            }
            recipe.properties = params
            if (!recipe.hasErrors() && recipe.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'recipe.label', default: 'Recipe'), recipe.id])}"
                redirect(action: "show", id: recipe.id)
            }
            else {
                render(view: "edit", model: [recipe: recipe])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'recipe.label', default: 'Recipe'), params.id])}"
            redirect(action: "list")
        }
    }
    def delete = {
        def recipe = Recipe.get(params.id)
        if (recipe) {
            try {
                recipe.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'recipe.label', default: 'Recipe'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'recipe.label', default: 'Recipe'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'recipe.label', default: 'Recipe'), params.id])}"
            redirect(action: "list")
        }
    }
  
    def createRecipe = {
        render(view: 'addEditRecipe')
    }
    def saveRecipe = {RecipeCO recipeCO ->
        println params.tags
        if(recipeCO.validate()){
            recipeCO.convertToRecipe()
            redirect(action: 'createRecipe')
        } else {
            //println "*******************Invalid.."
            render(view: 'addEditRecipe', model:[recipeCO: recipeCO])
        }
    }

    def getMatchingProducts={
        List<Product> products =Product.findAllByNameIlike(params.query + "%")
        List productsJson = products.collect { [ id: it.id, name: it.name ] }
        render (productsJson as JSON)
    }

    def getMatchingCategories = {
        List<Category> categories =Category.findAllByNameIlike(params.query + "%")
        List categoriesJson = categories.collect { [ id: it.id, name: it.name ] }
        render (categoriesJson as JSON)
    }

}

class RecipeCO {
    RecipeCO() {} //constructor

    String name
    String difficulty
    Boolean shareWithCommunity
    Integer makesServing
    Integer preparationTime
    Integer cookTime

    Set<Long> categoryIds = []
    List<BigDecimal> ingredientQuantities = []
    List<Long> ingredientUnitIds = []
    List<Long> ingredientProductIds = []
    List<String> directions = []
    List<String> hiddenDirections = []
    List<String> hiddenIngredients = []

    static constraints = {
        name(blank: false, validator: {val, obj ->
            if(Recipe.countByName(val)){
                return 'default.invalid.message'
            }
        })
        difficulty(blank:false, inList: RecipeDifficulty.list()*.name)
        makesServing(min:1)
        preparationTime(min:1)
        cookTime(min:1)
        categoryIds(minSize:1)

        ingredientQuantities(minSize: 1, validator:{val, obj ->
            if((val.any{!it}) || (val.size()!=obj.ingredientUnitIds?.size())||(val.size()!=obj.ingredientProductIds?.size())){
                return 'default.invalid.message'
            }
        })
        ingredientUnitIds(minSize: 1, validator:{val, obj ->
            if((val.any{!it})||(val.size()!=obj.ingredientQuantities?.size())||(val.size()!=obj.ingredientProductIds?.size())){
                return 'default.invalid.message'
            }
        })
        ingredientProductIds(minSize: 1, validator:{val, obj ->
            if((val.any{!it})|| (val == val.unique()) || (val.size()!=obj.ingredientUnitIds?.size())||(val.size()!=obj.ingredientQuantities?.size())){
                return 'default.invalid.message'
            }
        })
        directions(minSize: 1, validator:{val, obj ->
            if((val.any{!it})|| (val == val.unique())){
                return 'default.invalid.message'
            }
        })
    }        

   public convertToRecipe() {

        Recipe recipe = new Recipe()
        recipe.name = name
        recipe.shareWithCommunity=shareWithCommunity
        recipe.servings=makesServing
        recipe.difficulty = RecipeDifficulty."${difficulty}"
        recipe.preparationTime=preparationTime
        recipe.cookingTime=cookTime
        recipe.s()

        categoryIds.eachWithIndex {Long categoryId, Integer index ->
            recipe.addToCategories(Category.get(categoryId))
        }

        directions.eachWithIndex {String step, Integer index ->
            new RecipeDirection(recipe: recipe, sequence: (index + 1), step: step).s()
        }

        ingredientQuantities.eachWithIndex {BigDecimal amount, Integer index ->
            MeasuredProduct product = MeasuredProduct.get(ingredientProductIds[index])
            Unit unit = Unit.get(ingredientUnitIds[index])
            Quantity quantity = new Quantity(unit: unit, amount: amount).s()
            new RecipeIngredient(sequence: (index + 1), recipe: recipe, ingredient: product, quantity: quantity).s()
        }
    }
}



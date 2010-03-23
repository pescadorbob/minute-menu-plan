package com.mp.domain

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
    def saveRecipe = {AddRecipeCO addRecipeCO ->
        addRecipeCO.convertToRecipe()
        redirect(actin: 'createRecipe')
    }

//    def editRecipe = {EditRecipeCO recipeDetailCO ->
//        def recipe = Recipe.get(params.id)
//        EditRecipeCO.populateRecipeDetail(recipe)
//        render(view: 'editRecipe', model: [recipeDetail: recipeDetail])
//    }
}

class AddRecipeCO {
    String name
    String difficulty
    Boolean shareWithCommunity
    Integer makesServing
    Integer preparationTime
    Integer cookTime

    List<Long> categoryIds = []
    List<BigDecimal> ingredientQuantities = []
    List<Long> ingredientUnitIds = []
    List<Long> ingredientProductIds = []
    List<String> directions = []

    AddRecipeCO() {}

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


/*
class EditRecipeCO {
    String name
    String difficulty
    Boolean shareWithCommunity
    Integer makesServing
    Integer preparationTime
    Integer cookTime

    List<Long> category = []
    List<BigDecimal> ingredientQuantities = []
    List<Long> ingredientUnitIds = []
    List<Long> ingredientProductIds = []
    List<String> directions = []

    EditRecipeCO() {}

    public populateRecipeDetail(Recipe recipe){
        name=recipe.name
        difficulty=recipe.difficulty
        shareWithCommunity=recipe.shareWithCommunity
        makesServing=recipe.makesServing
        preparationTime=recipe.preparationTime
        cookTime=recipe.cookTime

       recipe.directions.each {RecipeDirection recipeDirection ->
           directions<<recipeDirection.toString()
       }
        recipe.categories.each {
            category<<it.toString()
        }

    }

    public convertToRecipe() {

        Recipe recipe = new Recipe()
        recipe.name = name
        recipe.shareWithCommunity=shareWithCommunity
        recipe.makesServing=makesServing
        recipe.difficulty = RecipeDifficulty."${difficulty}"
        recipe.preparationTime=preparationTime
        recipe.cookTime=cookTime
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
} */
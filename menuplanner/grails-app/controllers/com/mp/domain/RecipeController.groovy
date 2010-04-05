package com.mp.domain

import grails.converters.JSON
import static com.mp.MenuConstants.*

class RecipeController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }
    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [recipeList: Recipe.list(params), recipeTotal: Recipe.count()]
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

    def saveRecipe = {RecipeCO recipeCO ->
        if (recipeCO.validate()) {
            recipeCO.convertToRecipe()
            redirect(action: 'create')
        } else {
            println recipeCO.errors.allErrors.each {
                println it
            }
            SystemOfUnit sys = SystemOfUnit.findBySystemName(SYSTEM_OF_UNIT_USA)
            List<Unit> timeUnits = Unit.findAllByMetricType(MetricType.TIME)
            timeUnits = timeUnits.findAll {(sys.id in it.systemOfUnits*.id)}
            List<Unit> metricUnits = Unit.findAllByMetricType(MetricType.METRIC)
            metricUnits = metricUnits.findAll {sys.id in it.systemOfUnits*.id}
            List<Nutrient> nutrients = Nutrient.list()
            render(view: 'create', model: [recipeCO: recipeCO, timeUnits: timeUnits, metricUnits: metricUnits, nutrients: nutrients])
        }
    }

    def getMatchingProducts = {
        List<Product> products = Product.findAllByNameIlike(params.query + "%")
        List productsJson = products.collect { [id: it.id, name: it.name] }
        render(productsJson as JSON)
    }

    def getMatchingCategories = {
        List<Category> categories = Category.findAllByNameIlike(params.query + "%")
        List categoriesJson = categories.collect { [id: it.id, name: it.name] }
        render(categoriesJson as JSON)
    }

    def create = {
        SystemOfUnit sys = SystemOfUnit.findBySystemName(SYSTEM_OF_UNIT_USA)

        List<Unit> timeUnits = Unit.findAllByMetricType(MetricType.TIME)
        timeUnits = timeUnits.findAll {(sys.id in it.systemOfUnits*.id)}

        List<Unit> metricUnits = Unit.findAllByMetricType(MetricType.METRIC)
        metricUnits = metricUnits.findAll {sys.id in it.systemOfUnits*.id}

        List<Nutrient> nutrients = Nutrient.list()
        render(view: 'create', model: [timeUnits: timeUnits, metricUnits: metricUnits, nutrients: nutrients])
    }

}

class RecipeCO {
    RecipeCO() {} //constructor

    String name
    String difficulty
    Boolean shareWithCommunity
    Integer makesServing
    Integer preparationTime
    Integer preparationUnitId
    Integer cookTime
    Integer cookUnitId
    def recipeImage

    Set<Long> categoryIds = []
    List<BigDecimal> ingredientQuantities = []
    List<BigDecimal> nutrientQuantities = []

    List<Long> ingredientUnitIds = []
    List<Long> ingredientProductIds = []
    List<Long> nutrientIds = []

    List<String> directions = []

    List<String> hiddenDirections = []
    List<String> hiddenIngredients = []
    List<String> hiddenNutrients = []

    static constraints = {
        name(blank: false, validator: {val, obj ->
            if (Recipe.countByName(val)) {
                return 'default.invalid.message'
            }
        })
        difficulty(blank: false, inList: RecipeDifficulty.list()*.name())
        makesServing(min: 1)
        recipeImage(blank: true)
        preparationTime(min: 1)
        cookTime(min: 1)
        categoryIds(minSize: 1)

        nutrientQuantities()

        ingredientQuantities(minSize: 1, validator: {val, obj ->
            if ((val.any {!it}) || (val.size() != obj.ingredientUnitIds?.size()) || (val.size() != obj.ingredientProductIds?.size())) {
                return 'default.invalid.message'
            }
        })
        ingredientUnitIds(minSize: 1, validator: {val, obj ->
            if ((val.any {!it}) || (val.size() != obj.ingredientQuantities?.size()) || (val.size() != obj.ingredientProductIds?.size())) {
                return 'default.invalid.message'
            }
        })
        ingredientProductIds(minSize: 1, validator: {val, obj ->
            if ((val.any {!it}) || (val != val.unique()) || (val.size() != obj.ingredientUnitIds?.size()) || (val.size() != obj.ingredientQuantities?.size())) {
                return 'default.invalid.message'
            }
        })
        directions(minSize: 1, validator: {val, obj ->
            if ((val.any {!it}) || (val != val.unique())) {
                return 'default.invalid.message'
            }
        })
    }

    public convertToRecipe() {
        Recipe recipe = new Recipe()
        recipe.name = name
        recipe.shareWithCommunity = shareWithCommunity
        recipe.servings = makesServing
        recipe.difficulty = RecipeDifficulty."${difficulty}"

        Quantity quantityPreparationTime = new Quantity()
        quantityPreparationTime.unit = Unit.get(preparationUnitId)
        if (quantityPreparationTime.unit.name == TIME_UNIT_HOURS) preparationTime *= 60;
        quantityPreparationTime.value = preparationTime
        quantityPreparationTime.s()

        Quantity quantityCookTime = new Quantity()
        quantityCookTime.unit = Unit.get(cookUnitId)
        if (quantityCookTime.unit.name == TIME_UNIT_HOURS) cookTime *= 60;
        quantityCookTime.value = cookTime
        quantityCookTime.s()

        recipe.preparationTime = quantityPreparationTime
        recipe.cookingTime = quantityCookTime

        recipe.s()

//       Image image =    Image.createFile(recipe.id, "/recipes", recipeImage.originalFilename, recipeImage.bytes,"Some alt text").s()

        categoryIds.eachWithIndex {Long categoryId, Integer index ->
            recipe.addToCategories(Category.get(categoryId))
        }

        directions.eachWithIndex {String step, Integer index ->
            new RecipeDirection(recipe: recipe, sequence: (index + 1), step: step).s()
        }

        ingredientQuantities.eachWithIndex {BigDecimal amount, Integer index ->
            MeasuredProduct product = MeasuredProduct.get(ingredientProductIds[index])
            Unit unit = Unit.get(ingredientUnitIds[index])
            Quantity quantity = new Quantity(unit: unit, value: amount).s()
            new RecipeIngredient(sequence: (index + 1), recipe: recipe, ingredient: product, quantity: quantity).s()
        }
        nutrientQuantities.eachWithIndex {BigDecimal quantity, Integer index ->
            RecipeNutrient nutrient = new RecipeNutrient()
            nutrient.recipe = recipe
            nutrient.nutrient = Nutrient.get(nutrientIds[index])
            if (nutrientQuantities[index]) {
                Quantity recipeNutrientQuantity = new Quantity()
                recipeNutrientQuantity.value = nutrientQuantities[index]
                recipeNutrientQuantity.unit = Nutrient.get(nutrientIds[index]).preferredUnit
                recipeNutrientQuantity.s()
                nutrient.quantity = recipeNutrientQuantity
                nutrient.s()
            }
        }
    }
}
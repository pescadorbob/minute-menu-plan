package com.mp.domain

import org.codehaus.groovy.grails.commons.ConfigurationHolder
import static com.mp.MenuConstants.*

class RecipeService {

    boolean transactional = true
}

class RecipeCO {
    static config = ConfigurationHolder.config

    RecipeCO() {} //constructor

    Long id
    String name
    String difficulty
    Boolean shareWithCommunity
    Integer makesServing
    Integer preparationTime
    Integer preparationUnitId
    Integer cookTime
    Integer cookUnitId
    def selectRecipeImage
    def selectRecipeImagePath


    Set<Long> categoryIds = []
    Set<String> categoryNames = []

    Set<Long> serveWithItems = []

    List<BigDecimal> ingredientQuantities = []
    List<Long> ingredientUnitIds = []
    List<Long> ingredientProductIds = []
    List<String> hiddenIngredientUnitNames = []
    List<String> hiddenIngredientProductNames = []

    List<String> directions = []

    List<Long> nutrientIds = []
    def nutrientQuantities = []

    RecipeCO(Recipe recipe) {
        id = recipe?.id
        name = recipe?.name

        difficulty = recipe?.difficulty?.name()
        shareWithCommunity = recipe?.shareWithCommunity
        makesServing = recipe?.servings

        preparationUnitId = recipe?.preparationTime?.unit?.id
        if (recipe?.preparationTime?.unit?.name == TIME_UNIT_HOURS) {
            preparationTime = recipe?.preparationTime?.value / 60
        } else {
            preparationTime = recipe?.preparationTime?.value
        }
        cookUnitId = recipe?.cookingTime?.unit?.id
        if (recipe?.cookingTime?.unit?.name == TIME_UNIT_HOURS) {
            cookTime = recipe?.cookingTime?.value / 60
        } else {
            cookTime = recipe?.cookingTime?.value
        }
        categoryIds = recipe?.categories*.id
        directions = recipe?.directions*.step

        ingredientQuantities = recipe?.ingredients*.quantity?.value
        ingredientUnitIds = recipe?.ingredients*.quantity?.unit?.id
        ingredientProductIds = recipe?.ingredients*.ingredient.id

        hiddenIngredientUnitNames = recipe?.ingredients*.quantity?.unit?.name
        hiddenIngredientProductNames = recipe.ingredients*.ingredient?.name

        nutrientIds = Nutrient.list()*.id
        Nutrient.count().times {
            nutrientQuantities[it] = ""
        }
        recipe.nutrients.each {RecipeNutrient recipeNutrient ->
            nutrientQuantities[recipeNutrient?.nutrient?.id?.toInteger() - 1] = recipeNutrient?.quantity?.value
        }
    } // parameterized constructor


    void setNutrientQuantities(def listOfNq) {
        [listOfNq].flatten().each {
            try {nutrientQuantities << new BigDecimal(it)} catch (ex) {nutrientQuantities << it}
        }
    }

    static constraints = {
        id(nullable: true)
        name(blank: false, matches: /[a-zA-Z0-9\s\&]*/)

        difficulty(blank: false, inList: RecipeDifficulty.list()*.name())
        makesServing(nullable: true, blank: true)
        selectRecipeImagePath(nullable: true)
        selectRecipeImage(blank: true)
        preparationTime(nullable: true, blank: true)
        cookTime(nullable: true, blank: true)

        nutrientQuantities(validator: {val ->
            if (val.findAll {!(it instanceof BigDecimal || it == "")}.size() > 0) {
                return 'recipeCO.nutrientQuantities.matches.invalid.nutrientQuantities'
            }
        })

        ingredientQuantities(validator: {val, obj ->
            if (val.size() < 1) {
                return 'recipeCO.ingredientQuantities.not.Amount.message'
            }
            if (val.size() != obj.ingredientUnitIds?.size()) {
                return 'recipeCO.ingredientProduct.Repeated.message'
            }
        })
        ingredientProductIds(validator: {val, obj ->
            if (val.size() != obj.ingredientProductIds?.unique()?.size()) {
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
        recipe.shareWithCommunity = shareWithCommunity
        recipe.servings = makesServing
        recipe.difficulty = RecipeDifficulty."${difficulty}"
        recipe.preparationTime = makeTimeQuantity(preparationTime, preparationUnitId)
        recipe.cookingTime = makeTimeQuantity(cookTime, cookUnitId)
        attachImage(recipe, selectRecipeImagePath)

        def temp = recipe.recipeCategories
        recipe.recipeCategories = []
        temp*.delete(flush: true)
        createCategories(recipe, categoryIds)

        def temp0 = recipe.items
        recipe.items = []
        temp0*.delete(flush: true)
        createServeWith(recipe, serveWithItems)

        def temp1 = recipe.directions
        recipe.directions = []
        temp1*.delete(flush: true)
        createDirections(recipe, directions)


        def temp2 = recipe.ingredients
        recipe.ingredients = []
        temp2*.delete(flush: true)
        createIngredients(recipe, ingredientQuantities, ingredientUnitIds, hiddenIngredientProductNames)

        def temp3 = recipe.nutrients
        recipe.nutrients = []
        temp3*.delete(flush: true)
        createNutrients(recipe, nutrientQuantities, nutrientIds)

        return recipe
    }

    public Recipe convertToRecipe() {
        Recipe recipe = new Recipe()

        recipe.name = name
        recipe.shareWithCommunity = shareWithCommunity
        recipe.servings = makesServing
        recipe.difficulty = RecipeDifficulty."${difficulty}"
        recipe.preparationTime = makeTimeQuantity(preparationTime, preparationUnitId)
        recipe.cookingTime = makeTimeQuantity(cookTime, cookUnitId)
        attachImage(recipe, selectRecipeImagePath)

        createCategories(recipe, categoryIds)
        createDirections(recipe, directions)
        createIngredients(recipe, ingredientQuantities, ingredientUnitIds, hiddenIngredientProductNames)
        createNutrients(recipe, nutrientQuantities, nutrientIds)

        return recipe
    }

    public boolean attachImage(Recipe recipe, def imagePath) {
        if (!imagePath) {
            return false
        }
        Image image = new Image(imagePath, "Some alt text")
        recipe.image = image
        image.s()
        recipe.s()
    }

    public Quantity makeTimeQuantity(Integer minutes, Integer unitId) {
        if (!(minutes && unitId)) {
            return null
        }
        Quantity time = new Quantity()
        time.unit = Unit.get(unitId)
        if (time.unit.name == TIME_UNIT_HOURS) minutes *= 60;
        time.value = minutes
        time.s()
        if (time) {
            return time
        } else {
            return null
        }
    }

    public boolean createIngredients(Recipe recipe, List<BigDecimal> amounts, List<Long> unitIds, List<String> productNames) {
        productNames?.eachWithIndex {String productName, Integer index ->
            Item product = Item.findByName(productName)
            Unit unit = (unitIds[index]) ? Unit.get(unitIds[index]) : null
            Quantity quantity = new Quantity(unit: unit, value: amounts[index]).s()

            if (!product) {
                if (unit) {
                    product = new MeasurableProduct(name: productNames[index], isVisible: false)
                } else {
                    product = new Product(name: productNames[index], isVisible: false)
                }
                product.s()
            }

            RecipeIngredient recipeIngredient = new RecipeIngredient()
            recipeIngredient.recipe = recipe
            recipeIngredient.ingredient = product
            recipeIngredient.quantity = quantity

            recipe.addToIngredients(recipeIngredient)
            recipe.s()
        }
        return true
    }

    public boolean createDirections(Recipe recipe, List<String> directions) {
        directions?.eachWithIndex {String step, Integer index ->
            RecipeDirection recipeDirection = new RecipeDirection()
            recipeDirection.recipe = recipe
            recipeDirection.step = step
            recipe.addToDirections(recipeDirection)
            recipe.s()
        }
        return true
    }

    public boolean createNutrients(Recipe recipe, def amounts, List<Long> nutrientIds) {
        amounts.eachWithIndex {def amount, Integer index ->
            RecipeNutrient nutrient = new RecipeNutrient()
            nutrient.recipe = recipe
            nutrient.nutrient = Nutrient.get(nutrientIds[index])
            if (amount) {
                Quantity recipeNutrientQuantity = new Quantity()
                recipeNutrientQuantity.value = amount
                recipeNutrientQuantity.unit = Nutrient.get(nutrientIds[index]).preferredUnit
                recipeNutrientQuantity.s()
                nutrient.quantity = recipeNutrientQuantity
                recipe.addToNutrients(nutrient)
                recipe.s()
            }
        }
        return true
    }

    public boolean createServeWith(Recipe recipe, Set<Long> itemIds) {
        itemIds.eachWithIndex {Long itemId, Integer index ->
            recipe.addToItems(Item.get(itemId))
            recipe.s()
        }
        return true
    }

    public boolean createCategories(Recipe recipe, Set<Long> categoryIds) {
        categoryIds.eachWithIndex {Long categoryId, Integer index ->
            recipe.addToItems(Item.get(categoryId))
            recipe.s()
        }
        return true
    }
}

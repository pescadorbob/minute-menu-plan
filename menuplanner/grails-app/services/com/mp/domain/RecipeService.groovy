package com.mp.domain

import org.codehaus.groovy.grails.commons.ConfigurationHolder
import static com.mp.MenuConstants.*
import org.apache.commons.math.fraction.Fraction

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
    Long preparationUnitId
    Integer cookTime
    Long cookUnitId
    def selectRecipeImage
    def selectRecipeImagePath


    Set<Long> categoryIds = []
    Set<String> categoryNames = []

    Set<Long> serveWithItems = []

    List<String> ingredientQuantities = []
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
            preparationTime = recipe?.preparationTime?.value?.toBigDecimal() / 60
        } else {
            preparationTime = recipe?.preparationTime?.value?.toBigDecimal()
        }
        cookUnitId = recipe?.cookingTime?.unit?.id
        if (recipe?.cookingTime?.unit?.name == TIME_UNIT_HOURS) {
            cookTime = recipe?.cookingTime?.value?.toBigDecimal() / 60
        } else {
            cookTime = recipe?.cookingTime?.value?.toBigDecimal()
        }
        categoryIds = recipe?.categories*.id as Set
        directions = recipe?.directions

        hiddenIngredientProductNames = recipe.ingredients*.ingredient?.name
        ingredientProductIds = recipe?.ingredients*.ingredient.id

        recipe?.ingredients*.quantity?.unit?.eachWithIndex {Unit unit, Integer index ->
            ingredientUnitIds.add(unit?.id)
            hiddenIngredientUnitNames.add(unit?.name)
        }

        recipe?.ingredients*.quantity?.value?.eachWithIndex {BigDecimal val, Integer index ->
            if (recipe?.ingredients?.getAt(index)?.quantity?.unit?.belongsToUsaSystem()) {
                ingredientQuantities.add(StandardConversion.getUsaString(val, recipe?.ingredients?.getAt(index)?.quantity?.unit))
            } else {
                ingredientQuantities.add(val ? val.toString() : '')
            }
        }

        nutrientIds = Nutrient.list()*.id
        Nutrient.count().times {
            nutrientQuantities[it] = ""
        }
        recipe.nutrients.each {RecipeNutrient recipeNutrient ->
            nutrientQuantities[recipeNutrient?.nutrient?.id?.toInteger() - 1] = recipeNutrient?.quantity?.value
        }
    }


    void setNutrientQuantities(def listOfNq) {
        [listOfNq].flatten().each {
            try {nutrientQuantities << new BigDecimal(it)} catch (ex) {nutrientQuantities << it}
        }
    }

    static constraints = {
        id(nullable: true)
        name(blank: false, matches: /[a-zA-Z0-9\s\&]*/)

        difficulty(blank: true, nullable: true)
        makesServing(nullable: true, blank: true)
        selectRecipeImagePath(nullable: true, blank: true)
        selectRecipeImage(nullable: true, blank: true)
        preparationTime(nullable: true, blank: true)
        cookTime(nullable: true, blank: true)

        nutrientQuantities(validator: {val ->
            if (val.findAll {!(it instanceof BigDecimal || it == "")}.size() > 0) {
                return 'recipeCO.nutrientQuantities.matches.invalid.nutrientQuantities'
            }
        })

        ingredientQuantities(nullable: true, blank: true)

        hiddenIngredientProductNames(validator: {val, obj ->
            List<String> tempProd = []
            val.each { tempProd.add(it) }
            if (val.size() < 1) {
                return 'recipeCO.ingredient.not.Provided.message'
            }
            if (val.size() != tempProd?.unique()?.size()) {
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
        if (difficulty) {
            recipe.difficulty = RecipeDifficulty."${difficulty}"
        }
        recipe.preparationTime = makeTimeQuantity(preparationTime, preparationUnitId)
        recipe.cookingTime = makeTimeQuantity(cookTime, cookUnitId)

        attachImage(recipe, selectRecipeImagePath)

        def tempRecipeCategories = recipe.recipeCategories
        recipe.recipeCategories = []
        tempRecipeCategories*.delete(flush: true)
        addCategoriesToRecipe(recipe, categoryIds)

        def tempIngredients = recipe.ingredients
        recipe.ingredients = []
        tempIngredients*.delete(flush: true)
        addIngredientsToRecipe(recipe, ingredientQuantities, ingredientUnitIds, hiddenIngredientProductNames)

        recipe.directions = []
        addDirectionsToRecipe(recipe, directions)

        addServeWithToRecipe(recipe, serveWithItems)

        def tempNutrients = recipe.nutrients
        recipe.nutrients = []
        tempNutrients*.delete(flush: true)
        addNutrientsToRecipe(recipe, nutrientQuantities, nutrientIds)

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

        addCategoriesToRecipe(recipe, categoryIds)
        addDirectionsToRecipe(recipe, directions)
        addIngredientsToRecipe(recipe, ingredientQuantities, ingredientUnitIds, hiddenIngredientProductNames)
        addServeWithToRecipe(recipe, serveWithItems)
        addNutrientsToRecipe(recipe, nutrientQuantities, nutrientIds)

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

    public Quantity makeTimeQuantity(Integer minutes, Long unitId) {
        if (!minutes) {
            return null
        }
        Quantity time = new Quantity()
        time.unit = Unit.get(unitId)
        time.savedUnit = Unit.findByName(TIME_UNIT_MINUTES)
        if (time.unit.name == TIME_UNIT_HOURS) minutes *= 60;
        time.value = minutes
        time.s()
        if (time) {
            return time
        } else {
            return null
        }
    }

    public List<RecipeIngredient> recipeIngredientList(List<String> amounts, List<Long> unitIds, List<String> productNames) {
        List<RecipeIngredient> recipeIngredients = []
        productNames?.eachWithIndex {String productName, Integer index ->
            RecipeIngredient recipeIngredient = new RecipeIngredient()
            Item product = Item.findByName(productName)
            Unit unit = (unitIds[index]) ? Unit.get(unitIds[index]) : null
            Quantity quantity = StandardConversion.getMetricQuantity(amounts[index], unit)

            if (!product) {
                if (unit) {
                    product = new MeasurableProduct(name: productNames[index], isVisible: false).s()
                } else {
                    product = new Product(name: productNames[index], isVisible: false).s()
                }
            }
            quantity.s()
            recipeIngredient.ingredient = product
            recipeIngredient.quantity = quantity
            recipeIngredients.add(recipeIngredient)
        }
        return recipeIngredients
    }

    public boolean addIngredientsToRecipe(Recipe recipe, List<String> amounts, List<Long> unitIds, List<String> productNames) {
        List<RecipeIngredient> recipeIngredients = recipeIngredientList(amounts, unitIds, productNames)
        recipeIngredients?.eachWithIndex {RecipeIngredient recipeIngredient, Integer index ->
            recipeIngredient.recipe = recipe
            recipe.addToIngredients(recipeIngredient)
            recipe.s()
        }
        return true
    }

    public boolean addDirectionsToRecipe(Recipe recipe, List<String> directions) {
        directions = directions.findAll { it && it != "" }
        recipe.directions = directions
        recipe.s()
        return true
    }

    public List<RecipeNutrient> recipeNutrientList(def amounts, List<Long> nutrientIds) {
        List<RecipeNutrient> recipeNutrientList = []
        amounts.eachWithIndex {def amount, Integer index ->
            if (amount) {
                RecipeNutrient recipeNutrient = new RecipeNutrient()
                recipeNutrient.nutrient = Nutrient.get(nutrientIds[index])
                Quantity quantity = StandardConversion.getMetricQuantity(amount.toString(), Nutrient.get(nutrientIds[index]).preferredUnit) 
                quantity.s()
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
            recipe.s()
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

    public boolean addServeWithToRecipe(Recipe recipe, Set<Long> itemIds) {
        recipe.items = (serveWithItems) ? Item.getAll(serveWithItems as List) as Set : []
        recipe.s()
        return true
    }

    public List<RecipeCategory> recipeCategoryList(Set<Long> categoryIds) {
        List<RecipeCategory> recipeCategories = []
        categoryIds.each {Long categoryId ->
            RecipeCategory category = new RecipeCategory()
            category.category = Category.get(categoryId)
            recipeCategories.add(category)
        }
        return recipeCategories
    }

    public boolean addCategoriesToRecipe(Recipe recipe, Set<Long> categoryIds) {
        List<RecipeCategory> categories = recipeCategoryList(categoryIds)
        categories.each {RecipeCategory category ->
            category.recipe = recipe
            recipe.addToRecipeCategories(category)
            recipe.s()
        }
        return true
    }
}
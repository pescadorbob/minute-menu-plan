package com.mp.domain

import org.codehaus.groovy.grails.commons.ConfigurationHolder
import static com.mp.MenuConstants.*
import org.apache.commons.math.fraction.Fraction
import org.codehaus.groovy.grails.commons.ApplicationHolder

class RecipeService {

    boolean transactional = true

    public boolean deleteRecipe(Recipe recipe, User user) {
        try {
            user.removeFromContributions(recipe)
            user.s()
            recipe.delete()
        } catch (e) {
            e.printStackTrace()
            println "************************ Problem while Deleting recipe: ${recipe?.name}....."
        }
        return true
    }
        
}

class RecipeCO {
    static config = ConfigurationHolder.config

    RecipeCO() {} //constructor
    Long imageId
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

    Set<String> serveWithItems = []

    List<String> ingredientQuantities = []
    List<String> ingredientUnitIds = []
    List<String> ingredientProductIds = []
    List<String> hiddenIngredientUnitNames = []
    List<String> hiddenIngredientUnitSymbols = []
    List<String> hiddenIngredientProductNames = []

    List<String> directions = []

    List<Long> nutrientIds = []
    def nutrientQuantities = []

    RecipeCO(Recipe recipe) {
        id = recipe?.id
        imageId = recipe?.image?.id
        name = recipe?.name

        if(recipe?.image){
            selectRecipeImagePath = recipe?.image?.path + recipe?.image?.storedName
        } else{
            selectRecipeImagePath = ''            
        }

        difficulty = recipe?.difficulty?.name()
        shareWithCommunity = recipe?.shareWithCommunity
        makesServing = recipe?.servings
        serveWithItems = (recipe?.items) ? (recipe?.items*.name) : []


        preparationUnitId = recipe?.preparationTime?.unit?.id
        preparationTime = recipe?.preparationTime ? StandardConversion.getQuantityValueString(recipe?.preparationTime)?.toInteger() : null

        cookUnitId = recipe?.cookingTime?.unit?.id
        cookTime = recipe?.cookingTime ? StandardConversion.getQuantityValueString(recipe?.cookingTime)?.toInteger() : null

        categoryIds = recipe?.categories*.id as Set
        directions = recipe?.directions

        hiddenIngredientProductNames = recipe?.ingredients*.ingredient?.name
        ingredientProductIds = recipe?.ingredients*.ingredient?.id

        recipe?.ingredients*.quantity?.unit?.eachWithIndex {Unit unit, Integer index ->
            ingredientUnitIds.add(unit?.id)
            hiddenIngredientUnitNames.add(unit?.name)
            hiddenIngredientUnitSymbols.add(unit?.symbol)
        }

        recipe?.ingredients*.quantity?.value?.eachWithIndex {Float val, Integer index ->
            String usValue = StandardConversion.getQuantityValueString(recipe?.ingredients?.getAt(index)?.quantity)
            ingredientQuantities.add(usValue)
        }

        nutrientIds = Nutrient.list()*.id
        Nutrient.count().times {
            nutrientQuantities[it] = ""
        }
        recipe?.nutrients.each {RecipeNutrient recipeNutrient ->
            Integer val = StandardConversion.getQuantityValueString(recipeNutrient?.quantity)?.toBigDecimal()
            nutrientQuantities[recipeNutrient?.nutrient?.id?.toInteger() - 1] = val
        }
    }

    void setNutrientQuantities(def listOfNq) {
        [listOfNq].flatten().each {
            try {nutrientQuantities << new Float(it)} catch (ex) {nutrientQuantities << it}
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
            if (val.findAll {!(it instanceof Float || it == "")}.size() > 0) {
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

        recipe.s()
        attachImage(recipe, selectRecipeImagePath)
        recipe.s()

        return recipe
    }

    public Recipe convertToRecipe(User byUser) {
        Recipe recipe = new Recipe()

        recipe.name = name
        recipe.shareWithCommunity = shareWithCommunity
        recipe.servings = makesServing
        recipe.difficulty = RecipeDifficulty."${difficulty}"
        recipe.preparationTime = makeTimeQuantity(preparationTime, preparationUnitId)
        recipe.cookingTime = makeTimeQuantity(cookTime, cookUnitId)

        addCategoriesToRecipe(recipe, categoryIds)
        addDirectionsToRecipe(recipe, directions)
        addIngredientsToRecipe(recipe, ingredientQuantities, ingredientUnitIds, hiddenIngredientProductNames)
        addServeWithToRecipe(recipe, serveWithItems)
        addNutrientsToRecipe(recipe, nutrientQuantities, nutrientIds)

        recipe.s()
        attachImage(recipe, selectRecipeImagePath)
        recipe.s()

        return recipe
    }

    public boolean attachImage(Recipe recipe, def imagePath) {
        if (!imagePath) {
            Image image = recipe?.image
            recipe.image = null
            image?.delete(flush:true)
            return false
        } else {
            File sourceImage = new File(imagePath)
            if (sourceImage) {
                String recipeImageDirectory = config.imagesRootDir + "/recipes/" + recipe?.id + '/'
                File file = new File(recipeImageDirectory)
                file.mkdirs()
                String targetImagePath = recipeImageDirectory + recipe?.id + '.' + sourceImage.name.tokenize('.').tail().join('.')
                if(!(imagePath==targetImagePath)){

                    Image tempImage = recipe?.image
                    recipe.image = null
                    tempImage?.delete(flush:true)
                    
                    new File(targetImagePath).withOutputStream {out ->
                        out.write sourceImage.readBytes()
                    }
                    com.mp.domain.Image image = new com.mp.domain.Image(imagePath, recipeImageDirectory, recipe?.id?.toString(), "")
                    recipe.image = image
                    image.s()
                }
            }
        }
    }

    public Quantity makeTimeQuantity(Integer minutes, Long unitId) {
        if (minutes == null) {
            return null
        }
        Quantity time = new Quantity()
        Unit unit = Unit.get(unitId)
        time = StandardConversion.getQuantityToSave(minutes.toString(), unit)
        time?.s()
        if (time) {
            return time
        } else {
            return null
        }
    }

    public List<RecipeIngredient> recipeIngredientList(List<String> amounts, List<String> unitIds, List<String> productNames) {
        List<RecipeIngredient> recipeIngredients = []
        productNames?.eachWithIndex {String productName, Integer index ->
            RecipeIngredient recipeIngredient = new RecipeIngredient()
            Item product = Item.findByName(productName)
            Unit unit = (unitIds[index]) ? Unit?.get(unitIds[index]?.toLong()) : null
            Quantity quantity = StandardConversion.getQuantityToSave(amounts?.getAt(index) ? amounts[index] : null, unit)

            if (!product) {
                if (unit) {
                    product = new MeasurableProduct(name: productNames[index], isVisible: false, preferredUnit: unit).s()
                } else {
                    product = new Product(name: productNames[index], isVisible: false).s()
                }
            }

            quantity?.s()
            recipeIngredient.ingredient = product
            recipeIngredient.quantity = quantity
            recipeIngredients.add(recipeIngredient)
        }
        return recipeIngredients
    }

    public boolean addIngredientsToRecipe(Recipe recipe, List<String> amounts, List<String> unitIds, List<String> productNames) {
        List<RecipeIngredient> recipeIngredients = recipeIngredientList(amounts, unitIds, productNames)
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
                Quantity quantity = StandardConversion.getQuantityToSave(amount?.toInteger()?.toString(), Nutrient.get(nutrientIds[index]).preferredUnit)
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
        itemIds.each {String itemName ->
            if (itemName) {
                Item item = Item.countByName(itemName) ? Item.findByName(itemName) : new Product(name: itemName).s()
                items.add(item)
            }
        }
        recipe.items = items
        return true
    }

    public List<RecipeCategory> recipeCategoryList(Set<Long> categoryIds) {

        List<RecipeCategory> recipeCategories = []
        categoryIds.each {Long categoryId ->
            if (categoryId) {
                RecipeCategory category = new RecipeCategory()
                category.category = Category.get(categoryId)
                recipeCategories.add(category)
            }
        }
        return recipeCategories
    }

    public boolean addCategoriesToRecipe(Recipe recipe, Set<Long> categoryIds) {
        List<RecipeCategory> categories = recipeCategoryList(categoryIds)
        categories.each {RecipeCategory category ->
            category.recipe = recipe
            recipe.addToRecipeCategories(category)
        }
        return true
    }
}
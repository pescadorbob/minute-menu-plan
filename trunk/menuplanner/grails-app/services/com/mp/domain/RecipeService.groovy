package com.mp.domain

import org.codehaus.groovy.grails.commons.ConfigurationHolder
import static com.mp.MenuConstants.*
import org.apache.commons.math.fraction.ProperFractionFormat


class RecipeService {

    static config = ConfigurationHolder.config
    boolean transactional = true

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

    public List<Recipe> getFilteredRecipeList(Integer max = 15, Long offset = 0) {
        Party currentUser = LoginCredential.currentUser?.party
        Set<Recipe> currentUserRecipes = currentUser?.contributions
        List<Recipe> recipes = Recipe.createCriteria().list {
            or {
                eq('shareWithCommunity', true)
                if (currentUserRecipes) {
                    'in'('id', currentUserRecipes*.id)
                }
            }
            if (!currentUser.showAlcoholicContent) {
                eq('isAlcoholic', false)
            }
            maxResults(max)
            firstResult(offset.toInteger())
        }
        return recipes
    }

    public Integer getFilteredRecipeCount() {
        Party currentUser = LoginCredential.currentUser?.party
        Set<Recipe> currentUserRecipes = currentUser?.contributions
        Integer count = Recipe.createCriteria().count {
            or {
                eq('shareWithCommunity', true)
                if (currentUserRecipes) {
                    'in'('id', currentUserRecipes*.id)
                }
            }
            if (!currentUser.showAlcoholicContent) {
                eq('isAlcoholic', false)
            }
        }
        return count
    }

    public List<Item> getFilteredItemList(Integer max = 15, Long offset = 0) {
        Party currentParty = LoginCredential.currentUser?.party
        Set<Item> currentUserItems = []
        currentUserItems.addAll(currentParty?.contributions)
        currentUserItems.addAll(currentParty?.ingredients)
        List<Item> items = Item.createCriteria().list {
            or {
                eq('shareWithCommunity', true)
                'in'('id', currentUserItems*.id)
            }
            if (!currentParty.showAlcoholicContent) {
                eq('isAlcoholic', false)
            }
            maxResults(max)
            firstResult(offset.toInteger())
        }
        return items
    }

    public Integer getFilteredItemCount() {
        Party currentParty = LoginCredential.currentUser?.party
        Set<Item> currentUserItems = []
        currentUserItems.addAll(currentParty?.contributions)
        currentUserItems.addAll(currentParty?.ingredients)
        Integer count = Item.createCriteria().count {
            or {
                eq('shareWithCommunity', true)
                'in'('id', currentUserItems*.id)
            }
            if (!currentParty.showAlcoholicContent) {
                eq('isAlcoholic', false)
            }
        }
        return count
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
            Item ingredient = new Item()
            ingredient.name = recipeIngredient?.ingredient?.name
            recipeIngredientNew.ingredient = ingredient

            Quantity quantity = new Quantity()
            quantity.unit = recipeIngredient?.quantity?.unit
            quantity.value = recipeIngredient?.quantity?.value
            quantity.savedUnit = recipeIngredient?.quantity?.savedUnit
            recipeIngredientNew.quantity = quantity

            recipeIngredientNew.aisle = recipeIngredient.aisle
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
}

class RecipeCO {
    static config = ConfigurationHolder.config
    def recipeService
    def searchableService

    RecipeCO() {} //constructor
    Long imageId
    Long id
    String name
    String difficulty
    String description
    Boolean shareWithCommunity
    Boolean isAlcoholic
    Integer makesServing
    Integer preparationTime
    Long preparationUnitId
    Integer cookTime
    Long cookUnitId
    def selectRecipeImage
    def selectRecipeImagePath

    Set<Long> subCategoryIds = []
    Set<String> categoryNames = []

    Set<String> serveWithItems = []

    List<String> ingredientQuantities = []
    List<String> ingredientUnitIds = []
    List<String> ingredientProductIds = []
    List<String> ingredientPreparationMethodIds = []
    List<String> ingredientAisleIds = []
    List<String> hiddenIngredientUnitNames = []
    List<String> hiddenIngredientUnitSymbols = []
    List<String> hiddenIngredientProductNames = []
    List<String> hiddenIngredientPreparationMethodNames = []
    List<String> hiddenIngredientAisleNames = []

    List<String> directions = []

    List<Long> nutrientIds = []
    def nutrientQuantities = []

    RecipeCO(Recipe recipe) {
        id = recipe?.id
        imageId = recipe?.image?.id
        name = recipe?.name
        description = recipe?.description

        if (recipe?.image) {
            int firstIndex = recipe?.image?.storedName?.indexOf('.')
            String name = recipe?.image?.storedName?.substring(0, firstIndex)
            selectRecipeImagePath = recipe?.image?.path + name + "_640.jpg"
        } else {
            selectRecipeImagePath = ''
        }

        difficulty = recipe?.difficulty?.name()
        shareWithCommunity = recipe?.shareWithCommunity
        makesServing = recipe?.servings
        serveWithItems = (recipe?.items) ? (recipe?.items*.name) : []
        isAlcoholic = recipe?.isAlcoholic


        preparationUnitId = recipe?.preparationTime?.unit?.id
        preparationTime = recipe?.preparationTime ? StandardConversion.getQuantityValueString(recipe?.preparationTime)?.toInteger() : null

        cookUnitId = recipe?.cookingTime?.unit?.id
        cookTime = recipe?.cookingTime ? StandardConversion.getQuantityValueString(recipe?.cookingTime)?.toInteger() : null

        subCategoryIds = recipe?.subCategories*.id as Set
        directions = recipe?.directions

        hiddenIngredientProductNames = recipe?.ingredients*.ingredient?.name
        ingredientProductIds = recipe?.ingredients*.ingredient?.id

        recipe?.ingredients*.aisle?.each {Aisle aisle ->
            ingredientAisleIds.add(aisle?.id?.toString())
            hiddenIngredientAisleNames.add(aisle?.name)
        }

        recipe?.ingredients*.preparationMethod?.each {PreparationMethod preparationMethod ->
            ingredientPreparationMethodIds.add(preparationMethod?.id?.toString())
            hiddenIngredientPreparationMethodNames.add(preparationMethod?.name)
        }
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
        name(validator: {val ->
            if (!val) {
                return 'recipeCO.name.blank.error.name'
            }
        })

        difficulty(blank: true, nullable: true)
        description(blank: true, nullable: true)
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

        ingredientQuantities(validator: { values ->
            for(value in values) {
                if (value && !value.isNumber()) {
                    try {
                        new ProperFractionFormat().parse(value)?.floatValue()
                    }
                    catch (Exception e) {
                        println "Exception caught"
                        return 'recipeCO.ingredientQuantities.validator.error.java.util.List'
                    }
                }
            }
        })

        hiddenIngredientProductNames(validator: {val, obj ->
            List<String> tempProd = []
            val.each { tempProd.add(it) }
            if (!val.any {it}) {
                return 'recipeCO.ingredient.not.Provided.message'
            }
            if ((val.size() != tempProd?.unique()?.size()) && !(val.contains(''))) {
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
        recipe.description = description
        recipe.shareWithCommunity = shareWithCommunity
        recipe.isAlcoholic = isAlcoholic
        recipe.servings = makesServing
        if (difficulty) {
            recipe.difficulty = RecipeDifficulty."${difficulty}"
        }
        recipe.preparationTime = makeTimeQuantity(preparationTime, preparationUnitId)
        recipe.cookingTime = makeTimeQuantity(cookTime, cookUnitId)

        recipe.subCategories = []
        addSubCategoriesToRecipe(recipe, subCategoryIds)

        def tempIngredients = recipe.ingredients
        recipe.ingredients = []
        tempIngredients*.delete(flush: true)
        addIngredientsToRecipe(recipe, ingredientQuantities, ingredientUnitIds, hiddenIngredientProductNames, hiddenIngredientAisleNames, hiddenIngredientPreparationMethodNames)

        recipe.directions = []
        addDirectionsToRecipe(recipe, directions)

        addServeWithToRecipe(recipe, serveWithItems)

        def tempNutrients = recipe.nutrients
        recipe.nutrients = []
        tempNutrients*.delete(flush: true)
        addNutrientsToRecipe(recipe, nutrientQuantities, nutrientIds)

        recipe.s()
        attachImage(recipe, selectRecipeImagePath)
        recipe.isAlcoholic = recipe.isAlcoholic ? true : recipeService.isRecipeAlcoholic(recipe?.id)
        recipe.s()

        return recipe
    }

    public Recipe convertToRecipe(Party byUser) {
        Recipe recipe = new Recipe()

        recipe.name = name
        recipe.description = description
        recipe.shareWithCommunity = shareWithCommunity
        recipe.servings = makesServing
        recipe.difficulty = RecipeDifficulty."${difficulty}"
        recipe.preparationTime = makeTimeQuantity(preparationTime, preparationUnitId)
        recipe.cookingTime = makeTimeQuantity(cookTime, cookUnitId)
        recipe.isAlcoholic = isAlcoholic
        addSubCategoriesToRecipe(recipe, subCategoryIds)
        addDirectionsToRecipe(recipe, directions)
        addIngredientsToRecipe(recipe, ingredientQuantities, ingredientUnitIds, hiddenIngredientProductNames, hiddenIngredientAisleNames, hiddenIngredientPreparationMethodNames)
        addServeWithToRecipe(recipe, serveWithItems)
        addNutrientsToRecipe(recipe, nutrientQuantities, nutrientIds)

        recipe.s()
        attachImage(recipe, selectRecipeImagePath)
        recipe.isAlcoholic = recipe.isAlcoholic ? true : recipeService.isRecipeAlcoholic(recipe?.id)
        recipe.s()
        return recipe
    }

    public boolean attachImage(Recipe recipe, String imagePath) {
        List<Integer> imageSizes = RECIPE_IMAGE_SIZES
        return Image.updateOwnerImage(recipe, imagePath, imageSizes)
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

    public List<RecipeIngredient> recipeIngredientList(List<String> amounts, List<String> unitIds, List<String> productNames, List<String> aisleNames, List<String> preparationMethodNames) {
        List<RecipeIngredient> recipeIngredients = []
        productNames?.eachWithIndex {String productName, Integer index ->
            if (productName) {
                Party party = LoginCredential.currentUser?.party
                RecipeIngredient recipeIngredient = new RecipeIngredient()
                Unit unit = (unitIds[index]) ? Unit?.get(unitIds[index]?.toLong()) : null
                Aisle aisle = null
                if (aisleNames[index]) {
                    aisle = getAisleForRecipeIngredient(aisleNames[index], party)
                }
                if (productName) {
                    Item product = getProductForRecipeIngredient(productName, party, unit, aisle)
                    PreparationMethod preparationMethod = getPreparationMethodForRecipeIngredient(preparationMethodNames[index])
                    Quantity quantity = StandardConversion.getQuantityToSave(amounts?.getAt(index) ? amounts[index] : null, unit, product.density)
                    quantity?.s()
                    recipeIngredient.ingredient = product
                    recipeIngredient.quantity = quantity
                    recipeIngredient.preparationMethod = preparationMethod
                    recipeIngredient.aisle = aisle
                    recipeIngredients.add(recipeIngredient)
                }
            }
        }
        return recipeIngredients
    }

    public boolean addIngredientsToRecipe(Recipe recipe, List<String> amounts, List<String> unitIds, List<String> productNames, List<String> aisleNames, List<String> preparationMethodNames) {
        List<RecipeIngredient> recipeIngredients = recipeIngredientList(amounts, unitIds, productNames, aisleNames, preparationMethodNames)
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
        Party party = LoginCredential.currentUser?.party
        Item item
        itemIds.each {String itemName ->
            if (itemName) {
                item = Recipe.findByName(itemName)
                if (item) {
                    items.add(item)
                } else {
                    item = Item.countByName(itemName) ? Item.findByName(itemName) : new Product(name: itemName).s()
                    party.addToIngredients(item)
                    party.s()
                    items.add(item)
                    Item.countByName(itemName) ? searchableService.reindex(class: Item, item?.id) : searchableService.index(class: Product, item?.id)
                }
            }
        }
        recipe.items = items
        return true
    }

    public boolean addSubCategoriesToRecipe(Recipe recipe, Set<Long> subCategoryIds) {
        subCategoryIds.each {Long categoryId ->
            if (categoryId) {
                SubCategory category = SubCategory.get(categoryId)
                recipe.addToSubCategories(category)
            }
        }
        return true
    }

    public Item getProductForRecipeIngredient(String productName, Party party, Unit unit, Aisle aisle) {
        List<Item> products = Item.getItemsForCurrentUser()
        Item product = products.find {it.name == productName}
        if (!product) {
            product = Product.findByName(productName)
            if (product) {
                party.addToIngredients(product)
                party.s()
                searchableService.reindex(class: Product, product?.id)
            } else {
                if (unit) {
                    product = new MeasurableProduct(name: productName, isVisible: false, preferredUnit: unit, suggestedAisle: aisle)
                    product.s()
                    party.addToIngredients(product)
                    party.s()
                    searchableService.index(class: MeasurableProduct, product?.id)
                } else {
                    product = new Product(name: productName, isVisible: false, suggestedAisle: aisle)
                    product.s()
                    party.addToIngredients(product)
                    party.s()
                    searchableService.index(class: Product, product?.id)
                }
            }
        }
        return product
    }

    public Aisle getAisleForRecipeIngredient(String aisleName, Party party) {
        List<Aisle> aislesForUser = Aisle.getAislesForCurrentUser()
        Aisle aisle = aislesForUser.find {it.name == aisleName}
        if (!aisle) {
            Aisle aisleInList = Aisle.list().find {it.name == aisleName}
            if (aisleInList) {
                aisleInList.ownedByUser = true
                aisleInList.s()
                party.addToAisles(aisleInList)
                party.s()
            } else {
                aisle = new Aisle(name: aisleName, ownedByUser: true)
                aisle.s()
                party.addToAisles(aisle)
                party.s()
            }
        }
        return aisle
    }

    public PreparationMethod getPreparationMethodForRecipeIngredient(String methodString) {
        String preparationMethodString = methodString?.trim()
        PreparationMethod preparationMethod = (preparationMethodString) ? PreparationMethod.findByName(preparationMethodString) : null
        if (!preparationMethod && preparationMethodString) {
            preparationMethod = new PreparationMethod(name: preparationMethodString).s()
        }
        return preparationMethod
    }
}
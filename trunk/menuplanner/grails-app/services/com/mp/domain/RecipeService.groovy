package com.mp.domain

import org.codehaus.groovy.grails.commons.ConfigurationHolder
import jxl.WorkbookSettings
import org.codehaus.groovy.grails.commons.ApplicationHolder
import jxl.Workbook
import jxl.Sheet
import static com.mp.MenuConstants.*


class RecipeService {

    boolean transactional = true

    public boolean deleteRecipe(Recipe recipe, Party user) {
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

    public String fuzzySearchQuery(String query, String searchKeyword) {
        int firstIndex = query.indexOf('*')
        int lastIndex = query.lastIndexOf('*')
        String query1 = query.substring(0, firstIndex)
        String query2 = query.substring(lastIndex + 1, query.length())
        String newQuery = query1 + query2 + " ${searchKeyword}~"
        return newQuery
    }
}

class RecipeCO {
    static config = ConfigurationHolder.config

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
            selectRecipeImagePath = recipe?.image?.path + name + "_200.jpg"
        } else {
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
        name(blank: false, matches: /[a-zA-Z0-9\s\&]*/)

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

        ingredientQuantities(nullable: true, blank: true)

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

//    public Recipe updateRecipe(List<String> elementNames, List<String> serveWithElements) {

    public Recipe updateRecipe() {
        Recipe recipe = Recipe.get(id)
        recipe.name = name
        recipe.description = description
        recipe.shareWithCommunity = shareWithCommunity
//        List<String> elementsFromList = alcoholicContentList()
//        elementsFromList.each {String name ->
//            if (name) {
//                if (name.toLowerCase() in elementNames || name in serveWithElements) {
//                    recipe.isAlcoholic = true
//                }
//            }
//        }
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
        recipe.s()

        return recipe
    }

//    public Recipe convertToRecipe(Party byUser, List<String> elementNames, List<String> serveWithElements) {

    public Recipe convertToRecipe(Party byUser) {
        Recipe recipe = new Recipe()

        recipe.name = name
        recipe.description = description
        recipe.shareWithCommunity = shareWithCommunity
        recipe.servings = makesServing
        recipe.difficulty = RecipeDifficulty."${difficulty}"
        recipe.preparationTime = makeTimeQuantity(preparationTime, preparationUnitId)
        recipe.cookingTime = makeTimeQuantity(cookTime, cookUnitId)

//        List<String> elements = alcoholicContentList()
//        elements.each {String name ->
//            if (name) {
//                if (name.toLowerCase() in elementNames || name in serveWithElements) {
//                    recipe.isAlcoholic = true
//                }
//            }
//        }
        addSubCategoriesToRecipe(recipe, subCategoryIds)
        addDirectionsToRecipe(recipe, directions)
        addIngredientsToRecipe(recipe, ingredientQuantities, ingredientUnitIds, hiddenIngredientProductNames, hiddenIngredientAisleNames, hiddenIngredientPreparationMethodNames)
        addServeWithToRecipe(recipe, serveWithItems)
        addNutrientsToRecipe(recipe, nutrientQuantities, nutrientIds)

        recipe.s()
        attachImage(recipe, selectRecipeImagePath)
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
                RecipeIngredient recipeIngredient = new RecipeIngredient()
                Item product = Item.findByName(productName)
                Unit unit = (unitIds[index]) ? Unit?.get(unitIds[index]?.toLong()) : null
                Aisle aisle = Aisle.findByName(aisleNames[index])
                String preparationMethodString = (preparationMethodNames[index])?.trim()
                PreparationMethod preparationMethod = (preparationMethodString) ? PreparationMethod.findByName(preparationMethodString) : null
                if (!aisle) {
                    aisle = new Aisle(name: aisleNames[index]).s()
                }
                if (!preparationMethod && preparationMethodString) {
                    preparationMethod = new PreparationMethod(name: preparationMethodString).s()
                }
                if (!product) {
                    if (unit) {
                        product = new MeasurableProduct(name: productNames[index], isVisible: false, preferredUnit: unit, suggestedAisle: aisle).s()
                    } else {
                        product = new Product(name: productNames[index], isVisible: false, suggestedAisle: aisle).s()
                    }
                }
                Quantity quantity = StandardConversion.getQuantityToSave(amounts?.getAt(index) ? amounts[index] : null, unit, product.density)
                quantity?.s()
                recipeIngredient.ingredient = product
                recipeIngredient.quantity = quantity
                recipeIngredient.preparationMethod = preparationMethod
                recipeIngredient.aisle = aisle
                recipeIngredients.add(recipeIngredient)
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
        itemIds.each {String itemName ->
            if (itemName) {
                Item item = Item.countByName(itemName) ? Item.findByName(itemName) : new Product(name: itemName).s()
                items.add(item)
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

//    public List<String> alcoholicContentList() {
//        String filterFileName = "/bootstrapData/alcoholic_filtering.xls"
//        File filterExcelFile = new File(ApplicationHolder.application.parentContext.servletContext.getRealPath(filterFileName))
//        List<String> elements = []
//        WorkbookSettings workbookSettings
//        Workbook workbook
//        workbookSettings = new WorkbookSettings();
//        workbookSettings.setLocale(new Locale("en", "EN"));
//        workbook = Workbook.getWorkbook(filterExcelFile, workbookSettings);
//        workbook?.sheets?.each {Sheet sheet ->
//            sheet.rows.times {Integer index ->
//                String valueOne = sheet.getCell(0, index).contents.toString().trim()
//                elements.add(valueOne)
//            }
//        }
//        return elements
//    }
}
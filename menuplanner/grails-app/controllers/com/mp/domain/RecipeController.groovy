package com.mp.domain

import org.codehaus.groovy.grails.commons.ConfigurationHolder
import grails.converters.JSON
import static com.mp.MenuConstants.*

class RecipeController {
    static config = ConfigurationHolder.config

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
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
    def getMatchingItems = {
        List<Item> items = Item.findAllByNameIlike(params.query + "%")
        List itemsJson = items.collect { [id: it.id, name: it.name] }
        render(itemsJson as JSON)
    }

    def list = {
        Integer listSize = Recipe.count()
        params.max = Math.min(params.max ? params.int('max') : 15, 150)
        List<Category> categoryList = Category.list()
        List<Recipe> recipeList = Recipe.list(params)
        render(view: 'list', model: [recipeList: recipeList, categoryList: categoryList, recipeTotal: Recipe.count()])
    }

    def search = {
        List<Recipe> results = []
        String query = params.query ?: params.list("q")?.join(" ")

        Integer total
        if (query && (query != 'null')) {
            def search = Recipe.search([reload: true, max: 15, offset: params.offset ?: 0]) {
                must(queryString(query))
            }
            results = search?.results
            total = search?.total
        } else {
            results = Recipe.list(params)
            total = Recipe.count()
        }

        render(template: '/recipe/searchResultPanel', model: [recipeList: results, recipeTotal: total, query: query])
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

    def edit = {
        if (params.id) {
            Recipe recipe = Recipe.get(params.id)
            RecipeCO recipeCO = new RecipeCO(recipe)
            SystemOfUnit sys = SystemOfUnit.findBySystemName(SYSTEM_OF_UNIT_USA)
            List<Nutrient> nutrients = Nutrient.list()
            render(view: 'edit', model: [recipeCO: recipeCO, timeUnits: sys.timeUnits, metricUnits: sys.metricUnits, nutrients: nutrients])
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
            render(view: 'edit', model: [recipeCO: recipeCO, timeUnits: sys.timeUnits, metricUnits: sys.metricUnits, nutrients: nutrients])
        }
    }

    def save = {RecipeCO recipeCO ->
        if (recipeCO.validate()) {
            recipeCO.convertToRecipe()
            redirect(action: 'create')
        } else {
            println recipeCO.errors.allErrors.each {
                println it
            }
            SystemOfUnit sys = SystemOfUnit.findBySystemName(SYSTEM_OF_UNIT_USA)
            List<Nutrient> nutrients = Nutrient.list()
            List<Category> categories = []
            if (recipeCO?.categoryIds)
                categories = Category.getAll(recipeCO?.categoryIds.flatten() as List)
//            List categoriesJson = categories.collect { [id: it.id, name: it.name] }
//            println categoriesJson as JSON

            render(view: 'create', model: [recipeCO: recipeCO, timeUnits: sys.timeUnits, metricUnits: sys.metricUnits, nutrients: nutrients, categories: categories])
        }
    }

    def create = {
        SystemOfUnit sys = SystemOfUnit.findBySystemName(SYSTEM_OF_UNIT_USA)

        List<Nutrient> nutrients = Nutrient.list()
        render(view: 'create', model: [timeUnits: sys.timeUnits, metricUnits: sys.metricUnits, nutrients: nutrients])
    }

    def show = {
        Recipe recipe = Recipe.findById(params.id)
        render(view: 'show', model: [recipe: recipe])
    }

    def uploadImage = {
        String relativePath = "/recipes"
        def fileContents = params.Filedata.bytes
        String filePath = config.imagesRootDir + relativePath
        File file = new File(filePath)
        file.mkdirs()
        File actualFile = new File(file, params.Filedata.originalFilename)
        actualFile.withOutputStream {out ->
            out.write fileContents
        }
        render actualFile.absolutePath as String
    }

    def showImage = {
        Image image
        if (params.selectRecipeImagePath) {
            image = new Image(params.selectRecipeImagePath, "Some alt Text")
        }
        if (params.id) {
            image = Recipe.get(params.id.toLong()).image
        }
        if (image) {
            byte[] fileContent = image.readFile()
            String fileName = image.actualName + "." + image.extension
            response.setContentLength(fileContent.size())
            response.setHeader("Content-disposition", "attachment; filename=" + fileName)
            response.setContentType("image/${image.extension}")
            OutputStream out = response.getOutputStream()
            out.write(fileContent)
            out.flush()
            out.close()
        }
    }
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
        id = recipe.id
        name = recipe.name
        difficulty = recipe.difficulty.name()
        shareWithCommunity = recipe.shareWithCommunity
        makesServing = recipe.servings
        preparationUnitId = recipe.preparationTime.unit.id
        if (recipe.preparationTime?.unit?.name == TIME_UNIT_HOURS) {
            preparationTime = recipe.preparationTime.value / 60
        } else {
            preparationTime = recipe.preparationTime.value
        }
        cookUnitId = recipe.cookingTime.unit.id
        if (recipe.cookingTime?.unit?.name == TIME_UNIT_HOURS) {
            cookTime = recipe.cookingTime.value / 60
        } else {
            cookTime = recipe.cookingTime.value
        }
        categoryIds = recipe.categories*.id
        directions = recipe.directions*.step
        ingredientQuantities = recipe?.ingredients*.quantity?.value
        ingredientUnitIds = recipe?.ingredients*.quantity?.unit?.id
        ingredientProductIds = recipe.ingredients*.ingredient.id

        hiddenIngredientUnitNames = recipe?.ingredients*.quantity?.unit?.name
        hiddenIngredientProductNames = recipe.ingredients*.ingredient?.name

        nutrientIds = Nutrient.list()*.id
        Nutrient.count().times {
            nutrientQuantities[it] = ""
        }
        recipe.nutrients.each {RecipeNutrient recipeNutrient ->
            nutrientQuantities[recipeNutrient.nutrient.id.toInteger() - 1] = recipeNutrient.quantity.value
        }
    } // parameterized constructor


    void setNutrientQuantities(def listOfNq) {
        [listOfNq].flatten().each {
            try {nutrientQuantities << new BigDecimal(it)} catch (ex) {nutrientQuantities << it}
        }
    }

    static constraints = {
        id(nullable: true)
        name(blank: false, matches: /[a-zA-Z0-9\s]*/)

        difficulty(blank: false, inList: RecipeDifficulty.list()*.name())
        makesServing(nullable: false, min: 1)
        selectRecipeImagePath(nullable: true)
        selectRecipeImage(blank: true)
        preparationTime(nullable: false, min: 0)
        cookTime(nullable: false, min: 0)
        categoryIds(minSize: 1)

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

    public updateRecipe() {
        Recipe recipe = Recipe.get(id)
        recipe.name = name
        recipe.shareWithCommunity = shareWithCommunity
        recipe.servings = makesServing
        recipe.difficulty = RecipeDifficulty."${difficulty}"

        recipe.preparationTime.unit = Unit.get(preparationUnitId)
        if (recipe.preparationTime.unit.name == TIME_UNIT_HOURS) preparationTime *= 60;
        recipe.preparationTime.value = preparationTime


        recipe.cookingTime.unit = Unit.get(cookUnitId)
        if (recipe.cookingTime.unit.name == TIME_UNIT_HOURS) cookTime *= 60;
        recipe.cookingTime.value = cookTime

        if (selectRecipeImagePath) {
            Image image = new Image(selectRecipeImagePath, "Some alt text")
            recipe.image = image
            image.s()
        }

        def temp = recipe.recipeCategories
        recipe.recipeCategories = []
        temp*.delete()
        categoryIds.eachWithIndex {Long categoryId, Integer index ->
            Category category = Category.get(categoryId)
            recipe.addToCategories(category)
        }

        def temp0 = recipe.items
        recipe.items = []
        temp0*.delete()
        serveWithItems.eachWithIndex {Long itemId, Integer index ->
            Item item = Item.get(itemId)
            recipe.addToItems(item)
        }

        def temp1 = recipe.directions
        recipe.directions = []
        temp1*.delete()
        directions.eachWithIndex {String step, Integer index ->
            new RecipeDirection(recipe: recipe, sequence: (index + 1), step: step).s()
        }

        def temp2 = recipe.ingredients
        recipe.ingredients = []
        temp2*.delete()
        ingredientQuantities.eachWithIndex {BigDecimal amount, Integer index ->
            MeasurableProduct product = MeasurableProduct.findByName(hiddenIngredientProductNames[index])
            if (!product) {
                MeasurableProduct newProduct = new Product(name: 'hiddenIngredientProductNames[index]', isVisible: false)
                newProduct.s()
            }
            product = MeasurableProduct.findByName(hiddenIngredientProductNames[index])
            Unit unit = Unit.get(ingredientUnitIds[index])
            Quantity quantity = new Quantity(unit: unit, value: amount).s()
            new RecipeIngredient(sequence: (index + 1), recipe: recipe, ingredient: product, quantity: quantity).s()
        }

        def temp3 = recipe.nutrients
        recipe.nutrients = []
        temp3*.delete()
        nutrientQuantities.eachWithIndex {def quantity, Integer index ->
            RecipeNutrient nutrient = new RecipeNutrient()
            nutrient.recipe = recipe
            nutrient.nutrient = Nutrient.get(nutrientIds[index])
            if (nutrientQuantities[index]) {
                Quantity recipeNutrientQuantity = new Quantity()
                recipeNutrientQuantity.value = quantity
                recipeNutrientQuantity.unit = Nutrient.get(nutrientIds[index]).preferredUnit
                recipeNutrientQuantity.s()
                nutrient.quantity = recipeNutrientQuantity
                nutrient.s()
            }
        }
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

        serveWithItems.eachWithIndex {Long itemId, Integer index ->
            recipe.addToItems(Item.get(itemId))
        }

        recipe.s()

        if (selectRecipeImagePath) {
            Image image = new Image(selectRecipeImagePath, "Some alt text")
            recipe.image = image
            image.s()
        }

        categoryIds.eachWithIndex {Long categoryId, Integer index ->
            recipe.addToCategories(Category.get(categoryId))
        }

        directions.eachWithIndex {String step, Integer index ->
            new RecipeDirection(recipe: recipe, sequence: (index + 1), step: step).s()
        }

        ingredientQuantities.eachWithIndex {BigDecimal amount, Integer index ->
            MeasurableProduct product = MeasurableProduct.findByName(hiddenIngredientProductNames[index])
            if (!product) {
                MeasurableProduct newProduct = new Product(name: 'hiddenIngredientProductNames[index]', isVisible: false)
                newProduct.s()
            }
            product = MeasurableProduct.findByName(hiddenIngredientProductNames[index])
            Unit unit = Unit.get(ingredientUnitIds[index])
            Quantity quantity = new Quantity(unit: unit, value: amount).s()
            new RecipeIngredient(sequence: (index + 1), recipe: recipe, ingredient: product, quantity: quantity).s()
        }
        nutrientQuantities.eachWithIndex {def quantity, Integer index ->
            RecipeNutrient nutrient = new RecipeNutrient()
            nutrient.recipe = recipe
            nutrient.nutrient = Nutrient.get(nutrientIds[index])
            if (nutrientQuantities[index]) {
                Quantity recipeNutrientQuantity = new Quantity()
                recipeNutrientQuantity.value = quantity
                recipeNutrientQuantity.unit = Nutrient.get(nutrientIds[index]).preferredUnit
                recipeNutrientQuantity.s()
                nutrient.quantity = recipeNutrientQuantity
                nutrient.s()
            }
        }
    }
}
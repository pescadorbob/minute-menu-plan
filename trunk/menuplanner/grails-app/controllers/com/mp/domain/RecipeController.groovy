package com.mp.domain

import org.codehaus.groovy.grails.commons.ConfigurationHolder
import grails.converters.JSON
import static com.mp.MenuConstants.*
import org.codehaus.groovy.grails.commons.ApplicationHolder

class RecipeController {
    static config = ConfigurationHolder.config
    def recipeService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

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
        println "x: ${items}"
        items.each {
            itemsJson += it.name + "|" + it.id + "\n"
        }
        println "xxx : ${itemsJson}"
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
            params.max = 15
            results = Recipe.list(params)
            total = Recipe.count()
        }

        render(template: '/recipe/searchResultRecipe', model: [recipeList: results, recipeTotal: total, query: query])
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
            Recipe recipe = Recipe.get(params.long('id'))
            RecipeCO recipeCO = new RecipeCO(recipe)
            SystemOfUnit sys = SystemOfUnit.findBySystemName(SYSTEM_OF_UNIT_USA)
            List<Nutrient> nutrients = Nutrient.list()
            List<Category> categories = Category.list()
            println "x : " + recipeCO.serveWithItems
            render(view: 'edit', model: [recipeCO: recipeCO, timeUnits: sys.timeUnits, metricUnits: sys.getMetricUnits(), nutrients: nutrients, categories: categories])
        }
    }

    def update = {RecipeCO recipeCO ->
        println "Updating..."
        if (recipeCO.validate()) {
            println "Validated..."
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
            Recipe recipe = recipeCO.convertToRecipe()
            redirect(action: 'show', id: recipe?.id)
        } else {
            println recipeCO.errors.allErrors.each {
                println it
            }
            SystemOfUnit sys = SystemOfUnit.findBySystemName(SYSTEM_OF_UNIT_USA)
            List<Nutrient> nutrients = Nutrient.list()
            List<Category> categories = Category.list()
            render(view: 'create', model: [recipeCO: recipeCO, timeUnits: sys.timeUnits, metricUnits: sys.metricUnits, nutrients: nutrients, categories: categories])
        }
    }

    def create = {
        SystemOfUnit sys = SystemOfUnit.findBySystemName(SYSTEM_OF_UNIT_USA)
        List<Nutrient> nutrients = Nutrient.list()
        List<Category> categories = Category.list()
        render(view: 'create', model: [timeUnits: sys.timeUnits, metricUnits: sys.getMetricUnits(), nutrients: nutrients, categories: categories])
    }

    def show = {
        Recipe recipe = Recipe.findById(params?.id)
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
        byte[] fileContent
        if (params.selectRecipeImagePath) {
            image = new Image(params.selectRecipeImagePath, "Some alt Text")
        }
        if (params.id) {
            image = Recipe.get(params.id.toLong()).image
        }
        if (image) {
            fileContent = image.readFile()
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

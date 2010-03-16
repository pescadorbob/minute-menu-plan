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
}

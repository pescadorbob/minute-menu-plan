package com.mp.domain

class RecipeIngredientController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [recipeIngredientList: RecipeIngredient.list(params), recipeIngredientTotal: RecipeIngredient.count()]
    }

    def create = {
        def recipeIngredient = new RecipeIngredient()
        recipeIngredient.properties = params
        return [recipeIngredient: recipeIngredient]
    }

    def save = {
        def recipeIngredient = new RecipeIngredient(params)
        if (recipeIngredient.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'recipeIngredient.label', default: 'RecipeIngredient'), recipeIngredient.id])}"
            redirect(action: "show", id: recipeIngredient.id)
        }
        else {
            render(view: "create", model: [recipeIngredient: recipeIngredient])
        }
    }

    def show = {
        def recipeIngredient = RecipeIngredient.get(params.id)
        if (!recipeIngredient) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'recipeIngredient.label', default: 'RecipeIngredient'), params.id])}"
            redirect(action: "list")
        }
        else {
            [recipeIngredient: recipeIngredient]
        }
    }

    def edit = {
        def recipeIngredient = RecipeIngredient.get(params.id)
        if (!recipeIngredient) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'recipeIngredient.label', default: 'RecipeIngredient'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [recipeIngredient: recipeIngredient]
        }
    }

    def update = {
        def recipeIngredient = RecipeIngredient.get(params.id)
        if (recipeIngredient) {
            if (params.version) {
                def version = params.version.toLong()
                if (recipeIngredient.version > version) {
                    
                    recipeIngredient.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'recipeIngredient.label', default: 'RecipeIngredient')] as Object[], "Another user has updated this RecipeIngredient while you were editing")
                    render(view: "edit", model: [recipeIngredient: recipeIngredient])
                    return
                }
            }
            recipeIngredient.properties = params
            if (!recipeIngredient.hasErrors() && recipeIngredient.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'recipeIngredient.label', default: 'RecipeIngredient'), recipeIngredient.id])}"
                redirect(action: "show", id: recipeIngredient.id)
            }
            else {
                render(view: "edit", model: [recipeIngredient: recipeIngredient])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'recipeIngredient.label', default: 'RecipeIngredient'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def recipeIngredient = RecipeIngredient.get(params.id)
        if (recipeIngredient) {
            try {
                recipeIngredient.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'recipeIngredient.label', default: 'RecipeIngredient'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'recipeIngredient.label', default: 'RecipeIngredient'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'recipeIngredient.label', default: 'RecipeIngredient'), params.id])}"
            redirect(action: "list")
        }
    }
}

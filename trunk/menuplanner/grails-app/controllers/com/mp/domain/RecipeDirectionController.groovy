package com.mp.domain

class RecipeDirectionController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [recipeDirectionList: RecipeDirection.list(params), recipeDirectionTotal: RecipeDirection.count()]
    }

    def create = {
        def recipeDirection = new RecipeDirection()
        recipeDirection.properties = params
        return [recipeDirection: recipeDirection]
    }

    def save = {
        def recipeDirection = new RecipeDirection(params)
        if (recipeDirection.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'recipeDirection.label', default: 'RecipeDirection'), recipeDirection.id])}"
            redirect(action: "show", id: recipeDirection.id)
        }
        else {
            render(view: "create", model: [recipeDirection: recipeDirection])
        }
    }

    def show = {
        def recipeDirection = RecipeDirection.get(params.id)
        if (!recipeDirection) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'recipeDirection.label', default: 'RecipeDirection'), params.id])}"
            redirect(action: "list")
        }
        else {
            [recipeDirection: recipeDirection]
        }
    }

    def edit = {
        def recipeDirection = RecipeDirection.get(params.id)
        if (!recipeDirection) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'recipeDirection.label', default: 'RecipeDirection'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [recipeDirection: recipeDirection]
        }
    }

    def update = {
        def recipeDirection = RecipeDirection.get(params.id)
        if (recipeDirection) {
            if (params.version) {
                def version = params.version.toLong()
                if (recipeDirection.version > version) {
                    
                    recipeDirection.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'recipeDirection.label', default: 'RecipeDirection')] as Object[], "Another user has updated this RecipeDirection while you were editing")
                    render(view: "edit", model: [recipeDirection: recipeDirection])
                    return
                }
            }
            recipeDirection.properties = params
            if (!recipeDirection.hasErrors() && recipeDirection.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'recipeDirection.label', default: 'RecipeDirection'), recipeDirection.id])}"
                redirect(action: "show", id: recipeDirection.id)
            }
            else {
                render(view: "edit", model: [recipeDirection: recipeDirection])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'recipeDirection.label', default: 'RecipeDirection'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def recipeDirection = RecipeDirection.get(params.id)
        if (recipeDirection) {
            try {
                recipeDirection.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'recipeDirection.label', default: 'RecipeDirection'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'recipeDirection.label', default: 'RecipeDirection'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'recipeDirection.label', default: 'RecipeDirection'), params.id])}"
            redirect(action: "list")
        }
    }
}

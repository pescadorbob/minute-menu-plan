package com.mp.domain.ndb

class NDBFoodController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [NDBFoodList: NDBFood.list(params), NDBFoodTotal: NDBFood.count()]
    }

    def create = {
        def NDBFood = new NDBFood()
        NDBFood.properties = params
        return [NDBFood: NDBFood]
    }

    def save = {
        def NDBFood = new NDBFood(params)
        if (NDBFood.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'NDBFood.label', default: 'NDBFood'), NDBFood.id])}"
            redirect(action: "show", id: NDBFood.id)
        }
        else {
            render(view: "create", model: [NDBFood: NDBFood])
        }
    }

    def show = {
        def NDBFood = NDBFood.get(params.id)
        if (!NDBFood) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'NDBFood.label', default: 'NDBFood'), params.id])}"
            redirect(action: "list")
        }
        else {
            [NDBFood: NDBFood]
        }
    }

    def edit = {
        def NDBFood = NDBFood.get(params.id)
        if (!NDBFood) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'NDBFood.label', default: 'NDBFood'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [NDBFood: NDBFood]
        }
    }

    def update = {
        def NDBFood = NDBFood.get(params.id)
        if (NDBFood) {
            if (params.version) {
                def version = params.version.toLong()
                if (NDBFood.version > version) {
                    
                    NDBFood.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'NDBFood.label', default: 'NDBFood')] as Object[], "Another user has updated this NDBFood while you were editing")
                    render(view: "edit", model: [NDBFood: NDBFood])
                    return
                }
            }
            NDBFood.properties = params
            if (!NDBFood.hasErrors() && NDBFood.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'NDBFood.label', default: 'NDBFood'), NDBFood.id])}"
                redirect(action: "show", id: NDBFood.id)
            }
            else {
                render(view: "edit", model: [NDBFood: NDBFood])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'NDBFood.label', default: 'NDBFood'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def NDBFood = NDBFood.get(params.id)
        if (NDBFood) {
            try {
                NDBFood.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'NDBFood.label', default: 'NDBFood'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'NDBFood.label', default: 'NDBFood'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'NDBFood.label', default: 'NDBFood'), params.id])}"
            redirect(action: "list")
        }
    }
}

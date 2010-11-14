package com.mp.domain

class AisleController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [aisleList: Aisle.list(params), aisleTotal: Aisle.count()]
    }

    def create = {
        def aisle = new Aisle()
        aisle.properties = params
        return [aisle: aisle]
    }

    def save = {
        def aisle = new Aisle(params)
        if (aisle.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'aisle.label', default: 'Aisle'), aisle.id])}"
            redirect(action: "show", id: aisle.id)
        }
        else {
            render(view: "create", model: [aisle: aisle])
        }
    }

    def show = {
        def aisle = Aisle.get(params.id)
        if (!aisle) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'aisle.label', default: 'Aisle'), params.id])}"
            redirect(action: "list")
        }
        else {
            [aisle: aisle]
        }
    }

    def edit = {
        def aisle = Aisle.get(params.id)
        if (!aisle) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'aisle.label', default: 'Aisle'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [aisle: aisle]
        }
    }

    def update = {
        def aisle = Aisle.get(params.id)
        if (aisle) {
            if (params.version) {
                def version = params.version.toLong()
                if (aisle.version > version) {
                    
                    aisle.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'aisle.label', default: 'Aisle')] as Object[], "Another user has updated this Aisle while you were editing")
                    render(view: "edit", model: [aisle: aisle])
                    return
                }
            }
            aisle.properties = params
            if (!aisle.hasErrors() && aisle.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'aisle.label', default: 'Aisle'), aisle.id])}"
                redirect(action: "show", id: aisle.id)
            }
            else {
                render(view: "edit", model: [aisle: aisle])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'aisle.label', default: 'Aisle'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def aisle = Aisle.get(params.id)
        if (aisle) {
            try {
                aisle.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'aisle.label', default: 'Aisle'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'aisle.label', default: 'Aisle'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'aisle.label', default: 'Aisle'), params.id])}"
            redirect(action: "list")
        }
    }
}

package com.mp.domain

class QuantityController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [quantityList: Quantity.list(params), quantityTotal: Quantity.count()]
    }

    def create = {
        def quantity = new Quantity()
        quantity.properties = params
        return [quantity: quantity]
    }

    def save = {
        def quantity = new Quantity(params)
        if (quantity.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'quantity.label', default: 'Quantity'), quantity.id])}"
            redirect(action: "show", id: quantity.id)
        }
        else {
            render(view: "create", model: [quantity: quantity])
        }
    }

    def show = {
        def quantity = Quantity.get(params.id)
        if (!quantity) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'quantity.label', default: 'Quantity'), params.id])}"
            redirect(action: "list")
        }
        else {
            [quantity: quantity]
        }
    }

    def edit = {
        def quantity = Quantity.get(params.id)
        if (!quantity) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'quantity.label', default: 'Quantity'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [quantity: quantity]
        }
    }

    def update = {
        def quantity = Quantity.get(params.id)
        if (quantity) {
            if (params.version) {
                def version = params.version.toLong()
                if (quantity.version > version) {
                    
                    quantity.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'quantity.label', default: 'Quantity')] as Object[], "Another user has updated this Quantity while you were editing")
                    render(view: "edit", model: [quantity: quantity])
                    return
                }
            }
            quantity.properties = params
            if (!quantity.hasErrors() && quantity.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'quantity.label', default: 'Quantity'), quantity.id])}"
                redirect(action: "show", id: quantity.id)
            }
            else {
                render(view: "edit", model: [quantity: quantity])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'quantity.label', default: 'Quantity'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def quantity = Quantity.get(params.id)
        if (quantity) {
            try {
                quantity.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'quantity.label', default: 'Quantity'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'quantity.label', default: 'Quantity'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'quantity.label', default: 'Quantity'), params.id])}"
            redirect(action: "list")
        }
    }
}

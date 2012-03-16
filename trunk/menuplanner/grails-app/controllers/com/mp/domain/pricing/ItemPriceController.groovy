package com.mp.domain.pricing

class ItemPriceController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [itemPriceList: ItemPrice.list(params), itemPriceTotal: ItemPrice.count()]
    }

    def create = {
        def itemPrice = new ItemPrice()
        itemPrice.properties = params
        return [itemPrice: itemPrice]
    }

    def save = {
        def itemPrice = new ItemPrice(params)
        if (itemPrice.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'itemPrice.label', default: 'ItemPrice'), itemPrice.id])}"
            redirect(action: "show", id: itemPrice.id)
        }
        else {
            render(view: "create", model: [itemPrice: itemPrice])
        }
    }

    def show = {
        def itemPrice = ItemPrice.get(params.id)
        if (!itemPrice) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'itemPrice.label', default: 'ItemPrice'), params.id])}"
            redirect(action: "list")
        }
        else {
            [itemPrice: itemPrice]
        }
    }

    def edit = {
        def itemPrice = ItemPrice.get(params.id)
        if (!itemPrice) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'itemPrice.label', default: 'ItemPrice'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [itemPrice: itemPrice]
        }
    }

    def update = {
        def itemPrice = ItemPrice.get(params.id)
        if (itemPrice) {
            if (params.version) {
                def version = params.version.toLong()
                if (itemPrice.version > version) {
                    
                    itemPrice.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'itemPrice.label', default: 'ItemPrice')] as Object[], "Another user has updated this ItemPrice while you were editing")
                    render(view: "edit", model: [itemPrice: itemPrice])
                    return
                }
            }
            itemPrice.properties = params
            if (!itemPrice.hasErrors() && itemPrice.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'itemPrice.label', default: 'ItemPrice'), itemPrice.id])}"
                redirect(action: "show", id: itemPrice.id)
            }
            else {
                render(view: "edit", model: [itemPrice: itemPrice])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'itemPrice.label', default: 'ItemPrice'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def itemPrice = ItemPrice.get(params.id)
        if (itemPrice) {
            try {
                itemPrice.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'itemPrice.label', default: 'ItemPrice'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'itemPrice.label', default: 'ItemPrice'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'itemPrice.label', default: 'ItemPrice'), params.id])}"
            redirect(action: "list")
        }
    }
}

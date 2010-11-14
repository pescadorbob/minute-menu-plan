package com.mp.domain.subscriptions

class BasePriceController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [basePriceList: BasePrice.list(params), basePriceTotal: BasePrice.count()]
    }

    def create = {
        def basePrice = new BasePrice()
        basePrice.properties = params
        return [basePrice: basePrice]
    }

    def save = {
        def basePrice = new BasePrice(params)
        if (basePrice.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'basePrice.label', default: 'BasePrice'), basePrice.id])}"
            redirect(action: "show", id: basePrice.id)
        }
        else {
            render(view: "create", model: [basePrice: basePrice])
        }
    }

    def show = {
        def basePrice = BasePrice.get(params.id)
        if (!basePrice) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'basePrice.label', default: 'BasePrice'), params.id])}"
            redirect(action: "list")
        }
        else {
            [basePrice: basePrice]
        }
    }

    def edit = {
        def basePrice = BasePrice.get(params.id)
        if (!basePrice) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'basePrice.label', default: 'BasePrice'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [basePrice: basePrice]
        }
    }

    def update = {
        def basePrice = BasePrice.get(params.id)
        if (basePrice) {
            if (params.version) {
                def version = params.version.toLong()
                if (basePrice.version > version) {
                    
                    basePrice.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'basePrice.label', default: 'BasePrice')] as Object[], "Another user has updated this BasePrice while you were editing")
                    render(view: "edit", model: [basePrice: basePrice])
                    return
                }
            }
            basePrice.properties = params
            if (!basePrice.hasErrors() && basePrice.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'basePrice.label', default: 'BasePrice'), basePrice.id])}"
                redirect(action: "show", id: basePrice.id)
            }
            else {
                render(view: "edit", model: [basePrice: basePrice])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'basePrice.label', default: 'BasePrice'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def basePrice = BasePrice.get(params.id)
        if (basePrice) {
            try {
                basePrice.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'basePrice.label', default: 'BasePrice'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'basePrice.label', default: 'BasePrice'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'basePrice.label', default: 'BasePrice'), params.id])}"
            redirect(action: "list")
        }
    }
}

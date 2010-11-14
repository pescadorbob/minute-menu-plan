package com.mp.domain.subscriptions

class ProductOfferingController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [productOfferingList: ProductOffering.list(params), productOfferingTotal: ProductOffering.count()]
    }

    def create = {
        def productOffering = new ProductOffering()
        productOffering.properties = params
        return [productOffering: productOffering]
    }

    def save = {
        def productOffering = new ProductOffering(params)
        if (productOffering.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'productOffering.label', default: 'ProductOffering'), productOffering.id])}"
            redirect(action: "show", id: productOffering.id)
        }
        else {
            render(view: "create", model: [productOffering: productOffering])
        }
    }

    def show = {
        def productOffering = ProductOffering.get(params.id)
        if (!productOffering) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'productOffering.label', default: 'ProductOffering'), params.id])}"
            redirect(action: "list")
        }
        else {
            [productOffering: productOffering]
        }
    }

    def edit = {
        def productOffering = ProductOffering.get(params.id)
        if (!productOffering) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'productOffering.label', default: 'ProductOffering'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [productOffering: productOffering]
        }
    }

    def update = {
        def productOffering = ProductOffering.get(params.id)
        if (productOffering) {
            if (params.version) {
                def version = params.version.toLong()
                if (productOffering.version > version) {
                    
                    productOffering.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'productOffering.label', default: 'ProductOffering')] as Object[], "Another user has updated this ProductOffering while you were editing")
                    render(view: "edit", model: [productOffering: productOffering])
                    return
                }
            }
            productOffering.properties = params
            if (!productOffering.hasErrors() && productOffering.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'productOffering.label', default: 'ProductOffering'), productOffering.id])}"
                redirect(action: "show", id: productOffering.id)
            }
            else {
                render(view: "edit", model: [productOffering: productOffering])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'productOffering.label', default: 'ProductOffering'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def productOffering = ProductOffering.get(params.id)
        if (productOffering) {
            try {
                productOffering.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'productOffering.label', default: 'ProductOffering'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'productOffering.label', default: 'ProductOffering'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'productOffering.label', default: 'ProductOffering'), params.id])}"
            redirect(action: "list")
        }
    }
}

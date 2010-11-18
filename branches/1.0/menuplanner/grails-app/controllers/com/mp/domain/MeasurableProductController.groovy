package com.mp.domain

class MeasurableProductController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [measurableProductList: MeasurableProduct.list(params), measurableProductTotal: MeasurableProduct.count()]
    }

    def create = {
        def measurableProduct = new MeasurableProduct()
        measurableProduct.properties = params
        return [measurableProduct: measurableProduct]
    }

    def save = {
        def measurableProduct = new MeasurableProduct(params)
        if (measurableProduct.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'measurableProduct.label', default: 'MeasurableProduct'), measurableProduct.id])}"
            redirect(action: "show", id: measurableProduct.id)
        }
        else {
            render(view: "create", model: [measurableProduct: measurableProduct])
        }
    }

    def show = {
        def measurableProduct = MeasurableProduct.get(params.id)
        if (!measurableProduct) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'measurableProduct.label', default: 'MeasurableProduct'), params.id])}"
            redirect(action: "list")
        }
        else {
            [measurableProduct: measurableProduct]
        }
    }

    def edit = {
        def measurableProduct = MeasurableProduct.get(params.id)
        if (!measurableProduct) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'measurableProduct.label', default: 'MeasurableProduct'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [measurableProduct: measurableProduct]
        }
    }

    def update = {
        def measurableProduct = MeasurableProduct.get(params.id)
        if (measurableProduct) {
            if (params.version) {
                def version = params.version.toLong()
                if (measurableProduct.version > version) {
                    
                    measurableProduct.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'measurableProduct.label', default: 'MeasurableProduct')] as Object[], "Another user has updated this MeasurableProduct while you were editing")
                    render(view: "edit", model: [measurableProduct: measurableProduct])
                    return
                }
            }
            measurableProduct.properties = params
            if (!measurableProduct.hasErrors() && measurableProduct.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'measurableProduct.label', default: 'MeasurableProduct'), measurableProduct.id])}"
                redirect(action: "show", id: measurableProduct.id)
            }
            else {
                render(view: "edit", model: [measurableProduct: measurableProduct])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'measurableProduct.label', default: 'MeasurableProduct'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def measurableProduct = MeasurableProduct.get(params.id)
        if (measurableProduct) {
            try {
                measurableProduct.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'measurableProduct.label', default: 'MeasurableProduct'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'measurableProduct.label', default: 'MeasurableProduct'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'measurableProduct.label', default: 'MeasurableProduct'), params.id])}"
            redirect(action: "list")
        }
    }
}

package com.mp.domain

class MeasuredProductController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [measuredProductList: MeasuredProduct.list(params), measuredProductTotal: MeasuredProduct.count()]
    }

    def create = {
        def measuredProduct = new MeasuredProduct()
        measuredProduct.properties = params
        return [measuredProduct: measuredProduct]
    }

    def save = {
        def measuredProduct = new MeasuredProduct(params)
        if (measuredProduct.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'measuredProduct.label', default: 'MeasuredProduct'), measuredProduct.id])}"
            redirect(action: "show", id: measuredProduct.id)
        }
        else {
            render(view: "create", model: [measuredProduct: measuredProduct])
        }
    }

    def show = {
        def measuredProduct = MeasuredProduct.get(params.id)
        if (!measuredProduct) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'measuredProduct.label', default: 'MeasuredProduct'), params.id])}"
            redirect(action: "list")
        }
        else {
            [measuredProduct: measuredProduct]
        }
    }

    def edit = {
        def measuredProduct = MeasuredProduct.get(params.id)
        if (!measuredProduct) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'measuredProduct.label', default: 'MeasuredProduct'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [measuredProduct: measuredProduct]
        }
    }

    def update = {
        def measuredProduct = MeasuredProduct.get(params.id)
        if (measuredProduct) {
            if (params.version) {
                def version = params.version.toLong()
                if (measuredProduct.version > version) {
                    
                    measuredProduct.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'measuredProduct.label', default: 'MeasuredProduct')] as Object[], "Another user has updated this MeasuredProduct while you were editing")
                    render(view: "edit", model: [measuredProduct: measuredProduct])
                    return
                }
            }
            measuredProduct.properties = params
            if (!measuredProduct.hasErrors() && measuredProduct.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'measuredProduct.label', default: 'MeasuredProduct'), measuredProduct.id])}"
                redirect(action: "show", id: measuredProduct.id)
            }
            else {
                render(view: "edit", model: [measuredProduct: measuredProduct])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'measuredProduct.label', default: 'MeasuredProduct'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def measuredProduct = MeasuredProduct.get(params.id)
        if (measuredProduct) {
            try {
                measuredProduct.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'measuredProduct.label', default: 'MeasuredProduct'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'measuredProduct.label', default: 'MeasuredProduct'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'measuredProduct.label', default: 'MeasuredProduct'), params.id])}"
            redirect(action: "list")
        }
    }
}

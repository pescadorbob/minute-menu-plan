package com.mp.domain

class ProductController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [productList: Product.list(params), productTotal: Product.count()]
    }

    def create = {
        def product = new Product()
        product.properties = params
        return [product: product]
    }

    def save = {
        def product = new Product(params)
        if (product.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'product.label', default: 'Product'), product.id])}"
            redirect(action: "show", id: product.id)
        }
        else {
            render(view: "create", model: [product: product])
        }
    }

    def show = {
        def product = Product.get(params.id)
        if (!product) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'product.label', default: 'Product'), params.id])}"
            redirect(action: "list")
        }
        else {
            [product: product]
        }
    }

    def edit = {
        def product = Product.get(params.id)
        if (!product) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'product.label', default: 'Product'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [product: product]
        }
    }

    def update = {
        def product = Product.get(params.id)
        if (product) {
            if (params.version) {
                def version = params.version.toLong()
                if (product.version > version) {
                    
                    product.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'product.label', default: 'Product')] as Object[], "Another user has updated this Product while you were editing")
                    render(view: "edit", model: [product: product])
                    return
                }
            }
            product.properties = params
            if (!product.hasErrors() && product.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'product.label', default: 'Product'), product.id])}"
                redirect(action: "show", id: product.id)
            }
            else {
                render(view: "edit", model: [product: product])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'product.label', default: 'Product'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def product = Product.get(params.id)
        if (product) {
            try {
                product.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'product.label', default: 'Product'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'product.label', default: 'Product'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'product.label', default: 'Product'), params.id])}"
            redirect(action: "list")
        }
    }
}

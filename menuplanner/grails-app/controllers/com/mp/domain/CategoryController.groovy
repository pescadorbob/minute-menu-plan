package com.mp.domain

class CategoryController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [categoryList: Category.list(params), categoryTotal: Category.count()]
    }

    def create = {
        def category = new Category()
        category.properties = params
        return [category: category]
    }

    def save = {
        def category = new Category(params)
        if (category.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'category.label', default: 'Category'), category.id])}"
            redirect(action: "show", id: category.id)
        }
        else {
            render(view: "create", model: [category: category])
        }
    }

    def show = {
        def category = Category.get(params.id)
        if (!category) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'category.label', default: 'Category'), params.id])}"
            redirect(action: "list")
        }
        else {
            [category: category]
        }
    }

    def edit = {
        def category = Category.get(params.id)
        if (!category) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'category.label', default: 'Category'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [category: category]
        }
    }

    def update = {
        def category = Category.get(params.id)
        if (category) {
            if (params.version) {
                def version = params.version.toLong()
                if (category.version > version) {
                    
                    category.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'category.label', default: 'Category')] as Object[], "Another user has updated this Category while you were editing")
                    render(view: "edit", model: [category: category])
                    return
                }
            }
            category.properties = params
            if (!category.hasErrors() && category.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'category.label', default: 'Category'), category.id])}"
                redirect(action: "show", id: category.id)
            }
            else {
                render(view: "edit", model: [category: category])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'category.label', default: 'Category'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def category = Category.get(params.id)
        if (category) {
            try {
                category.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'category.label', default: 'Category'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'category.label', default: 'Category'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'category.label', default: 'Category'), params.id])}"
            redirect(action: "list")
        }
    }
}

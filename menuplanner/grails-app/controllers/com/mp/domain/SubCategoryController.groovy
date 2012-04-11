package com.mp.domain

class SubCategoryController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [subCategoryList: SubCategory.list(params), subCategoryTotal: SubCategory.count()]
    }

    def create = {
        def subCategory = new SubCategory()
        subCategory.properties = params
        return [subCategory: subCategory]
    }

    def save = {
        def subCategory = new SubCategory(params)
        if (subCategory.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'subCategory.label', default: 'SubCategory'), subCategory.id])}"
            redirect(action: "show", id: subCategory.id)
        }
        else {
            render(view: "create", model: [subCategory: subCategory])
        }
    }

    def show = {
        def subCategory = SubCategory.get(params.id)
        if (!subCategory) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'subCategory.label', default: 'SubCategory'), params.id])}"
            redirect(action: "list")
        }
        else {
            [subCategory: subCategory]
        }
    }

    def edit = {
        def subCategory = SubCategory.get(params.id)
        if (!subCategory) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'subCategory.label', default: 'SubCategory'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [subCategory: subCategory]
        }
    }

    def update = {
        def subCategory = SubCategory.get(params.id)
        if (subCategory) {
            if (params.version) {
                def version = params.version.toLong()
                if (subCategory.version > version) {
                    
                    subCategory.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'subCategory.label', default: 'SubCategory')] as Object[], "Another user has updated this SubCategory while you were editing")
                    render(view: "edit", model: [subCategory: subCategory])
                    return
                }
            }
            subCategory.properties = params
            if (!subCategory.hasErrors() && subCategory.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'subCategory.label', default: 'SubCategory'), subCategory.id])}"
                redirect(action: "show", id: subCategory.id)
            }
            else {
                render(view: "edit", model: [subCategory: subCategory])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'subCategory.label', default: 'SubCategory'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def subCategory = SubCategory.get(params.id)
        if (subCategory) {
            try {
                subCategory.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'subCategory.label', default: 'SubCategory'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'subCategory.label', default: 'SubCategory'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'subCategory.label', default: 'SubCategory'), params.id])}"
            redirect(action: "list")
        }
    }
}

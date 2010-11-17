package com.mp.domain.themes

class PageElementController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [pageElementList: PageElement.list(params), pageElementTotal: PageElement.count()]
    }

    def create = {
        def pageElement = new PageElement()
        pageElement.properties = params
        return [pageElement: pageElement]
    }

    def save = {
        def pageElement = new PageElement(params)
        if (pageElement.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'pageElement.label', default: 'PageElement'), pageElement.id])}"
            redirect(action: "show", id: pageElement.id)
        }
        else {
            render(view: "create", model: [pageElement: pageElement])
        }
    }

    def show = {
        def pageElement = PageElement.get(params.id)
        if (!pageElement) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'pageElement.label', default: 'PageElement'), params.id])}"
            redirect(action: "list")
        }
        else {
            [pageElement: pageElement]
        }
    }

    def edit = {
        def pageElement = PageElement.get(params.id)
        if (!pageElement) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'pageElement.label', default: 'PageElement'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [pageElement: pageElement]
        }
    }

    def update = {
        def pageElement = PageElement.get(params.id)
        if (pageElement) {
            if (params.version) {
                def version = params.version.toLong()
                if (pageElement.version > version) {
                    
                    pageElement.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'pageElement.label', default: 'PageElement')] as Object[], "Another user has updated this PageElement while you were editing")
                    render(view: "edit", model: [pageElement: pageElement])
                    return
                }
            }
            pageElement.properties = params
            if (!pageElement.hasErrors() && pageElement.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'pageElement.label', default: 'PageElement'), pageElement.id])}"
                redirect(action: "show", id: pageElement.id)
            }
            else {
                render(view: "edit", model: [pageElement: pageElement])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'pageElement.label', default: 'PageElement'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def pageElement = PageElement.get(params.id)
        if (pageElement) {
            try {
                pageElement.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'pageElement.label', default: 'PageElement'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'pageElement.label', default: 'PageElement'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'pageElement.label', default: 'PageElement'), params.id])}"
            redirect(action: "list")
        }
    }
}

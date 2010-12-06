package com.mp.domain.themes

class WebPageController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [webPageList: WebPage.list(params), webPageTotal: WebPage.count()]
    }

    def create = {
        def webPage = new WebPage()
        webPage.properties = params
        return [webPage: webPage]
    }

    def save = {
        def webPage = new WebPage(params)
        if (webPage.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'webPage.label', default: 'WebPage'), webPage.id])}"
            redirect(action: "show", id: webPage.id)
        }
        else {
            render(view: "create", model: [webPage: webPage])
        }
    }

    def show = {
        def webPage = WebPage.get(params.id)
        if (!webPage) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'webPage.label', default: 'WebPage'), params.id])}"
            redirect(action: "list")
        }
        else {
            [webPage: webPage]
        }
    }

    def edit = {
        def webPage = WebPage.get(params.id)
        if (!webPage) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'webPage.label', default: 'WebPage'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [webPage: webPage]
        }
    }

    def update = {
        def webPage = WebPage.get(params.id)
        if (webPage) {
            if (params.version) {
                def version = params.version.toLong()
                if (webPage.version > version) {
                    
                    webPage.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'webPage.label', default: 'WebPage')] as Object[], "Another user has updated this WebPage while you were editing")
                    render(view: "edit", model: [webPage: webPage])
                    return
                }
            }
            webPage.properties = params
            if (!webPage.hasErrors() && webPage.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'webPage.label', default: 'WebPage'), webPage.id])}"
                redirect(action: "show", id: webPage.id)
            }
            else {
                render(view: "edit", model: [webPage: webPage])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'webPage.label', default: 'WebPage'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def webPage = WebPage.get(params.id)
        if (webPage) {
            try {
                webPage.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'webPage.label', default: 'WebPage'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'webPage.label', default: 'WebPage'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'webPage.label', default: 'WebPage'), params.id])}"
            redirect(action: "list")
        }
    }
}

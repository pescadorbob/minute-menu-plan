package com.mp.domain.subscriptions

class ContentController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [contentList: Content.list(params), contentTotal: Content.count()]
    }

    def create = {
        def content = new Content()
        content.properties = params
        return [content: content]
    }

    def save = {
        def content = new Content(params)
        if (content.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'content.label', default: 'Content'), content.id])}"
            redirect(action: "show", id: content.id)
        }
        else {
            render(view: "create", model: [content: content])
        }
    }

    def show = {
        def content = Content.get(params.id)
        if (!content) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'content.label', default: 'Content'), params.id])}"
            redirect(action: "list")
        }
        else {
            [content: content]
        }
    }

    def edit = {
        def content = Content.get(params.id)
        if (!content) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'content.label', default: 'Content'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [content: content]
        }
    }

    def update = {
        def content = Content.get(params.id)
        if (content) {
            if (params.version) {
                def version = params.version.toLong()
                if (content.version > version) {
                    
                    content.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'content.label', default: 'Content')] as Object[], "Another user has updated this Content while you were editing")
                    render(view: "edit", model: [content: content])
                    return
                }
            }
            content.properties = params
            if (!content.hasErrors() && content.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'content.label', default: 'Content'), content.id])}"
                redirect(action: "show", id: content.id)
            }
            else {
                render(view: "edit", model: [content: content])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'content.label', default: 'Content'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def content = Content.get(params.id)
        if (content) {
            try {
                content.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'content.label', default: 'Content'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'content.label', default: 'Content'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'content.label', default: 'Content'), params.id])}"
            redirect(action: "list")
        }
    }
}

package com.mp.domain.subscriptions

class ContentSubscriptionController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [contentSubscriptionList: ContentSubscription.list(params), contentSubscriptionTotal: ContentSubscription.count()]
    }

    def create = {
        def contentSubscription = new ContentSubscription()
        contentSubscription.properties = params
        return [contentSubscription: contentSubscription]
    }

    def save = {
        def contentSubscription = new ContentSubscription(params)
        if (contentSubscription.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'contentSubscription.label', default: 'ContentSubscription'), contentSubscription.id])}"
            redirect(action: "show", id: contentSubscription.id)
        }
        else {
            render(view: "create", model: [contentSubscription: contentSubscription])
        }
    }

    def show = {
        def contentSubscription = ContentSubscription.get(params.id)
        if (!contentSubscription) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'contentSubscription.label', default: 'ContentSubscription'), params.id])}"
            redirect(action: "list")
        }
        else {
            [contentSubscription: contentSubscription]
        }
    }

    def edit = {
        def contentSubscription = ContentSubscription.get(params.id)
        if (!contentSubscription) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'contentSubscription.label', default: 'ContentSubscription'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [contentSubscription: contentSubscription]
        }
    }

    def update = {
        def contentSubscription = ContentSubscription.get(params.id)
        if (contentSubscription) {
            if (params.version) {
                def version = params.version.toLong()
                if (contentSubscription.version > version) {
                    
                    contentSubscription.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'contentSubscription.label', default: 'ContentSubscription')] as Object[], "Another user has updated this ContentSubscription while you were editing")
                    render(view: "edit", model: [contentSubscription: contentSubscription])
                    return
                }
            }
            contentSubscription.properties = params
            if (!contentSubscription.hasErrors() && contentSubscription.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'contentSubscription.label', default: 'ContentSubscription'), contentSubscription.id])}"
                redirect(action: "show", id: contentSubscription.id)
            }
            else {
                render(view: "edit", model: [contentSubscription: contentSubscription])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'contentSubscription.label', default: 'ContentSubscription'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def contentSubscription = ContentSubscription.get(params.id)
        if (contentSubscription) {
            try {
                contentSubscription.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'contentSubscription.label', default: 'ContentSubscription'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'contentSubscription.label', default: 'ContentSubscription'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'contentSubscription.label', default: 'ContentSubscription'), params.id])}"
            redirect(action: "list")
        }
    }
}

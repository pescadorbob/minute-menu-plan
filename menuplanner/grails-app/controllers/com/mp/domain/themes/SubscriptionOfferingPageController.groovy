package com.mp.domain.themes

class SubscriptionOfferingPageController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [subscriptionOfferingPageList: SubscriptionOfferingPage.list(params), subscriptionOfferingPageTotal: SubscriptionOfferingPage.count()]
    }

    def create = {
        def subscriptionOfferingPage = new SubscriptionOfferingPage()
        subscriptionOfferingPage.properties = params
        return [subscriptionOfferingPage: subscriptionOfferingPage]
    }

    def save = {
        def subscriptionOfferingPage = new SubscriptionOfferingPage(params)
        if (subscriptionOfferingPage.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'subscriptionOfferingPage.label', default: 'SubscriptionOfferingPage'), subscriptionOfferingPage.id])}"
            redirect(action: "show", id: subscriptionOfferingPage.id)
        }
        else {
            render(view: "create", model: [subscriptionOfferingPage: subscriptionOfferingPage])
        }
    }

    def show = {
        def subscriptionOfferingPage = SubscriptionOfferingPage.get(params.id)
        if (!subscriptionOfferingPage) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'subscriptionOfferingPage.label', default: 'SubscriptionOfferingPage'), params.id])}"
            redirect(action: "list")
        }
        else {
            [subscriptionOfferingPage: subscriptionOfferingPage]
        }
    }

    def edit = {
        def subscriptionOfferingPage = SubscriptionOfferingPage.get(params.id)
        if (!subscriptionOfferingPage) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'subscriptionOfferingPage.label', default: 'SubscriptionOfferingPage'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [subscriptionOfferingPage: subscriptionOfferingPage]
        }
    }

    def update = {
        def subscriptionOfferingPage = SubscriptionOfferingPage.get(params.id)
        if (subscriptionOfferingPage) {
            if (params.version) {
                def version = params.version.toLong()
                if (subscriptionOfferingPage.version > version) {
                    
                    subscriptionOfferingPage.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'subscriptionOfferingPage.label', default: 'SubscriptionOfferingPage')] as Object[], "Another user has updated this SubscriptionOfferingPage while you were editing")
                    render(view: "edit", model: [subscriptionOfferingPage: subscriptionOfferingPage])
                    return
                }
            }
            subscriptionOfferingPage.properties = params
            if (!subscriptionOfferingPage.hasErrors() && subscriptionOfferingPage.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'subscriptionOfferingPage.label', default: 'SubscriptionOfferingPage'), subscriptionOfferingPage.id])}"
                redirect(action: "show", id: subscriptionOfferingPage.id)
            }
            else {
                render(view: "edit", model: [subscriptionOfferingPage: subscriptionOfferingPage])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'subscriptionOfferingPage.label', default: 'SubscriptionOfferingPage'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def subscriptionOfferingPage = SubscriptionOfferingPage.get(params.id)
        if (subscriptionOfferingPage) {
            try {
                subscriptionOfferingPage.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'subscriptionOfferingPage.label', default: 'SubscriptionOfferingPage'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'subscriptionOfferingPage.label', default: 'SubscriptionOfferingPage'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'subscriptionOfferingPage.label', default: 'SubscriptionOfferingPage'), params.id])}"
            redirect(action: "list")
        }
    }
}

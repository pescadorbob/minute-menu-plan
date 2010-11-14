package com.mp.domain.subscriptions

class PricingComponentController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [pricingComponentList: PricingComponent.list(params), pricingComponentTotal: PricingComponent.count()]
    }

    def create = {
        def pricingComponent = new PricingComponent()
        pricingComponent.properties = params
        return [pricingComponent: pricingComponent]
    }

    def save = {
        def pricingComponent = new PricingComponent(params)
        if (pricingComponent.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'pricingComponent.label', default: 'PricingComponent'), pricingComponent.id])}"
            redirect(action: "show", id: pricingComponent.id)
        }
        else {
            render(view: "create", model: [pricingComponent: pricingComponent])
        }
    }

    def show = {
        def pricingComponent = PricingComponent.get(params.id)
        if (!pricingComponent) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'pricingComponent.label', default: 'PricingComponent'), params.id])}"
            redirect(action: "list")
        }
        else {
            [pricingComponent: pricingComponent]
        }
    }

    def edit = {
        def pricingComponent = PricingComponent.get(params.id)
        if (!pricingComponent) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'pricingComponent.label', default: 'PricingComponent'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [pricingComponent: pricingComponent]
        }
    }

    def update = {
        def pricingComponent = PricingComponent.get(params.id)
        if (pricingComponent) {
            if (params.version) {
                def version = params.version.toLong()
                if (pricingComponent.version > version) {
                    
                    pricingComponent.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'pricingComponent.label', default: 'PricingComponent')] as Object[], "Another user has updated this PricingComponent while you were editing")
                    render(view: "edit", model: [pricingComponent: pricingComponent])
                    return
                }
            }
            pricingComponent.properties = params
            if (!pricingComponent.hasErrors() && pricingComponent.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'pricingComponent.label', default: 'PricingComponent'), pricingComponent.id])}"
                redirect(action: "show", id: pricingComponent.id)
            }
            else {
                render(view: "edit", model: [pricingComponent: pricingComponent])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'pricingComponent.label', default: 'PricingComponent'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def pricingComponent = PricingComponent.get(params.id)
        if (pricingComponent) {
            try {
                pricingComponent.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'pricingComponent.label', default: 'PricingComponent'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'pricingComponent.label', default: 'PricingComponent'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'pricingComponent.label', default: 'PricingComponent'), params.id])}"
            redirect(action: "list")
        }
    }
}

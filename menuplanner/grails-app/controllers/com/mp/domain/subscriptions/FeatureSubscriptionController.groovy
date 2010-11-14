package com.mp.domain.subscriptions

class FeatureSubscriptionController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [featureSubscriptionList: FeatureSubscription.list(params), featureSubscriptionTotal: FeatureSubscription.count()]
    }

    def create = {
        def featureSubscription = new FeatureSubscription()
        featureSubscription.properties = params
        return [featureSubscription: featureSubscription]
    }

    def save = {
        def featureSubscription = new FeatureSubscription(params)
        if (featureSubscription.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'featureSubscription.label', default: 'FeatureSubscription'), featureSubscription.id])}"
            redirect(action: "show", id: featureSubscription.id)
        }
        else {
            render(view: "create", model: [featureSubscription: featureSubscription])
        }
    }

    def show = {
        def featureSubscription = FeatureSubscription.get(params.id)
        if (!featureSubscription) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'featureSubscription.label', default: 'FeatureSubscription'), params.id])}"
            redirect(action: "list")
        }
        else {
            [featureSubscription: featureSubscription]
        }
    }

    def edit = {
        def featureSubscription = FeatureSubscription.get(params.id)
        if (!featureSubscription) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'featureSubscription.label', default: 'FeatureSubscription'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [featureSubscription: featureSubscription]
        }
    }

    def update = {
        def featureSubscription = FeatureSubscription.get(params.id)
        if (featureSubscription) {
            if (params.version) {
                def version = params.version.toLong()
                if (featureSubscription.version > version) {
                    
                    featureSubscription.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'featureSubscription.label', default: 'FeatureSubscription')] as Object[], "Another user has updated this FeatureSubscription while you were editing")
                    render(view: "edit", model: [featureSubscription: featureSubscription])
                    return
                }
            }
            featureSubscription.properties = params
            if (!featureSubscription.hasErrors() && featureSubscription.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'featureSubscription.label', default: 'FeatureSubscription'), featureSubscription.id])}"
                redirect(action: "show", id: featureSubscription.id)
            }
            else {
                render(view: "edit", model: [featureSubscription: featureSubscription])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'featureSubscription.label', default: 'FeatureSubscription'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def featureSubscription = FeatureSubscription.get(params.id)
        if (featureSubscription) {
            try {
                featureSubscription.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'featureSubscription.label', default: 'FeatureSubscription'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'featureSubscription.label', default: 'FeatureSubscription'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'featureSubscription.label', default: 'FeatureSubscription'), params.id])}"
            redirect(action: "list")
        }
    }
}

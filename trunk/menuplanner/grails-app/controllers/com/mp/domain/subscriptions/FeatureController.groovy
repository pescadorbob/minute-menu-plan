package com.mp.domain.subscriptions

class FeatureController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [featureList: Feature.list(params), featureTotal: Feature.count()]
    }

    def create = {
        def feature = new Feature()
        feature.properties = params
        return [feature: feature]
    }

    def save = {
        def feature = new Feature(params)
        if (feature.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'feature.label', default: 'Feature'), feature.id])}"
            redirect(action: "show", id: feature.id)
        }
        else {
            render(view: "create", model: [feature: feature])
        }
    }

    def show = {
        def feature = Feature.get(params.id)
        if (!feature) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'feature.label', default: 'Feature'), params.id])}"
            redirect(action: "list")
        }
        else {
            [feature: feature]
        }
    }

    def edit = {
        def feature = Feature.get(params.id)
        if (!feature) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'feature.label', default: 'Feature'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [feature: feature]
        }
    }

    def update = {
        def feature = Feature.get(params.id)
        if (feature) {
            if (params.version) {
                def version = params.version.toLong()
                if (feature.version > version) {
                    
                    feature.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'feature.label', default: 'Feature')] as Object[], "Another user has updated this Feature while you were editing")
                    render(view: "edit", model: [feature: feature])
                    return
                }
            }
            feature.properties = params
            if (!feature.hasErrors() && feature.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'feature.label', default: 'Feature'), feature.id])}"
                redirect(action: "show", id: feature.id)
            }
            else {
                render(view: "edit", model: [feature: feature])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'feature.label', default: 'Feature'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def feature = Feature.get(params.id)
        if (feature) {
            try {
                feature.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'feature.label', default: 'Feature'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'feature.label', default: 'Feature'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'feature.label', default: 'Feature'), params.id])}"
            redirect(action: "list")
        }
    }
}

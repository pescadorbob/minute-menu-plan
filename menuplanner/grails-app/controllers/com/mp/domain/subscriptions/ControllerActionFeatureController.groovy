package com.mp.domain.subscriptions

class ControllerActionFeatureController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [controllerActionFeatureList: ControllerActionFeature.list(params), controllerActionFeatureTotal: ControllerActionFeature.count()]
    }

    def create = {
        def controllerActionFeature = new ControllerActionFeature()
        controllerActionFeature.properties = params
        return [controllerActionFeature: controllerActionFeature]
    }

    def save = {
        def controllerActionFeature = new ControllerActionFeature(params)
        if (controllerActionFeature.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'controllerActionFeature.label', default: 'ControllerActionFeature'), controllerActionFeature.id])}"
            redirect(action: "show", id: controllerActionFeature.id)
        }
        else {
            render(view: "create", model: [controllerActionFeature: controllerActionFeature])
        }
    }

    def show = {
        def controllerActionFeature = ControllerActionFeature.get(params.id)
        if (!controllerActionFeature) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'controllerActionFeature.label', default: 'ControllerActionFeature'), params.id])}"
            redirect(action: "list")
        }
        else {
            [controllerActionFeature: controllerActionFeature]
        }
    }

    def edit = {
        def controllerActionFeature = ControllerActionFeature.get(params.id)
        if (!controllerActionFeature) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'controllerActionFeature.label', default: 'ControllerActionFeature'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [controllerActionFeature: controllerActionFeature]
        }
    }

    def update = {
        def controllerActionFeature = ControllerActionFeature.get(params.id)
        if (controllerActionFeature) {
            if (params.version) {
                def version = params.version.toLong()
                if (controllerActionFeature.version > version) {
                    
                    controllerActionFeature.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'controllerActionFeature.label', default: 'ControllerActionFeature')] as Object[], "Another user has updated this ControllerActionFeature while you were editing")
                    render(view: "edit", model: [controllerActionFeature: controllerActionFeature])
                    return
                }
            }
            controllerActionFeature.properties = params
            if (!controllerActionFeature.hasErrors() && controllerActionFeature.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'controllerActionFeature.label', default: 'ControllerActionFeature'), controllerActionFeature.id])}"
                redirect(action: "show", id: controllerActionFeature.id)
            }
            else {
                render(view: "edit", model: [controllerActionFeature: controllerActionFeature])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'controllerActionFeature.label', default: 'ControllerActionFeature'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def controllerActionFeature = ControllerActionFeature.get(params.id)
        if (controllerActionFeature) {
            try {
                controllerActionFeature.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'controllerActionFeature.label', default: 'ControllerActionFeature'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'controllerActionFeature.label', default: 'ControllerActionFeature'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'controllerActionFeature.label', default: 'ControllerActionFeature'), params.id])}"
            redirect(action: "list")
        }
    }
}

package com.mp.domain.access

class AccessFilterSetController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [accessFilterSetList: AccessFilterSet.list(params), accessFilterSetTotal: AccessFilterSet.count()]
    }

    def create = {
        def accessFilterSet = new AccessFilterSet()
        accessFilterSet.properties = params
        return [accessFilterSet: accessFilterSet]
    }

    def save = {
        def accessFilterSet = new AccessFilterSet(params)
        if (accessFilterSet.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'accessFilterSet.label', default: 'AccessFilterSet'), accessFilterSet.id])}"
            redirect(action: "show", id: accessFilterSet.id)
        }
        else {
            render(view: "create", model: [accessFilterSet: accessFilterSet])
        }
    }

    def show = {
        def accessFilterSet = AccessFilterSet.get(params.id)
        if (!accessFilterSet) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'accessFilterSet.label', default: 'AccessFilterSet'), params.id])}"
            redirect(action: "list")
        }
        else {
            [accessFilterSet: accessFilterSet]
        }
    }

    def edit = {
        def accessFilterSet = AccessFilterSet.get(params.id)
        if (!accessFilterSet) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'accessFilterSet.label', default: 'AccessFilterSet'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [accessFilterSet: accessFilterSet]
        }
    }

    def update = {
        def accessFilterSet = AccessFilterSet.get(params.id)
        if (accessFilterSet) {
            if (params.version) {
                def version = params.version.toLong()
                if (accessFilterSet.version > version) {
                    
                    accessFilterSet.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'accessFilterSet.label', default: 'AccessFilterSet')] as Object[], "Another user has updated this AccessFilterSet while you were editing")
                    render(view: "edit", model: [accessFilterSet: accessFilterSet])
                    return
                }
            }
            accessFilterSet.properties = params
            if (!accessFilterSet.hasErrors() && accessFilterSet.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'accessFilterSet.label', default: 'AccessFilterSet'), accessFilterSet.id])}"
                redirect(action: "show", id: accessFilterSet.id)
            }
            else {
                render(view: "edit", model: [accessFilterSet: accessFilterSet])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'accessFilterSet.label', default: 'AccessFilterSet'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def accessFilterSet = AccessFilterSet.get(params.id)
        if (accessFilterSet) {
            try {
                accessFilterSet.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'accessFilterSet.label', default: 'AccessFilterSet'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'accessFilterSet.label', default: 'AccessFilterSet'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'accessFilterSet.label', default: 'AccessFilterSet'), params.id])}"
            redirect(action: "list")
        }
    }
}

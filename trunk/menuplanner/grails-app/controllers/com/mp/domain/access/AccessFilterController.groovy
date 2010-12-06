package com.mp.domain.access

class AccessFilterController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [accessFilterList: AccessFilter.list(params), accessFilterTotal: AccessFilter.count()]
    }

    def create = {
        def accessFilter = new AccessFilter()
        accessFilter.properties = params
        return [accessFilter: accessFilter]
    }

    def save = {
        def accessFilter = new AccessFilter(params)
        if (accessFilter.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'accessFilter.label', default: 'AccessFilter'), accessFilter.id])}"
            redirect(action: "show", id: accessFilter.id)
        }
        else {
            render(view: "create", model: [accessFilter: accessFilter])
        }
    }

    def show = {
        def accessFilter = AccessFilter.get(params.id)
        if (!accessFilter) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'accessFilter.label', default: 'AccessFilter'), params.id])}"
            redirect(action: "list")
        }
        else {
            [accessFilter: accessFilter]
        }
    }

    def edit = {
        def accessFilter = AccessFilter.get(params.id)
        if (!accessFilter) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'accessFilter.label', default: 'AccessFilter'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [accessFilter: accessFilter]
        }
    }

    def update = {
        def accessFilter = AccessFilter.get(params.id)
        if (accessFilter) {
            if (params.version) {
                def version = params.version.toLong()
                if (accessFilter.version > version) {
                    
                    accessFilter.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'accessFilter.label', default: 'AccessFilter')] as Object[], "Another user has updated this AccessFilter while you were editing")
                    render(view: "edit", model: [accessFilter: accessFilter])
                    return
                }
            }
            accessFilter.properties = params
            if (!accessFilter.hasErrors() && accessFilter.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'accessFilter.label', default: 'AccessFilter'), accessFilter.id])}"
                redirect(action: "show", id: accessFilter.id)
            }
            else {
                render(view: "edit", model: [accessFilter: accessFilter])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'accessFilter.label', default: 'AccessFilter'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def accessFilter = AccessFilter.get(params.id)
        if (accessFilter) {
            try {
                accessFilter.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'accessFilter.label', default: 'AccessFilter'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'accessFilter.label', default: 'AccessFilter'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'accessFilter.label', default: 'AccessFilter'), params.id])}"
            redirect(action: "list")
        }
    }
}

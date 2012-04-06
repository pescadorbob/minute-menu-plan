package com.mp.domain.party

class OrganizationNameController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [organizationNameList: OrganizationName.list(params), organizationNameTotal: OrganizationName.count()]
    }

    def create = {
        def organizationName = new OrganizationName()
        organizationName.properties = params
        return [organizationName: organizationName]
    }

    def save = {
        def organizationName = new OrganizationName(params)
        if (organizationName.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'organizationName.label', default: 'OrganizationName'), organizationName.id])}"
            redirect(action: "show", id: organizationName.id)
        }
        else {
            render(view: "create", model: [organizationName: organizationName])
        }
    }

    def show = {
        def organizationName = OrganizationName.get(params.id)
        if (!organizationName) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'organizationName.label', default: 'OrganizationName'), params.id])}"
            redirect(action: "list")
        }
        else {
            [organizationName: organizationName]
        }
    }

    def edit = {
        def organizationName = OrganizationName.get(params.id)
        if (!organizationName) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'organizationName.label', default: 'OrganizationName'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [organizationName: organizationName]
        }
    }

    def update = {
        def organizationName = OrganizationName.get(params.id)
        if (organizationName) {
            if (params.version) {
                def version = params.version.toLong()
                if (organizationName.version > version) {
                    
                    organizationName.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'organizationName.label', default: 'OrganizationName')] as Object[], "Another user has updated this OrganizationName while you were editing")
                    render(view: "edit", model: [organizationName: organizationName])
                    return
                }
            }
            organizationName.properties = params
            if (!organizationName.hasErrors() && organizationName.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'organizationName.label', default: 'OrganizationName'), organizationName.id])}"
                redirect(action: "show", id: organizationName.id)
            }
            else {
                render(view: "edit", model: [organizationName: organizationName])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'organizationName.label', default: 'OrganizationName'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def organizationName = OrganizationName.get(params.id)
        if (organizationName) {
            try {
                organizationName.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'organizationName.label', default: 'OrganizationName'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'organizationName.label', default: 'OrganizationName'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'organizationName.label', default: 'OrganizationName'), params.id])}"
            redirect(action: "list")
        }
    }
}

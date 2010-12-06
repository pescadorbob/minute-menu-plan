package com.mp.domain.access

class RoleAccessController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [roleAccessList: RoleAccess.list(params), roleAccessTotal: RoleAccess.count()]
    }

    def create = {
        def roleAccess = new RoleAccess()
        roleAccess.properties = params
        return [roleAccess: roleAccess]
    }

    def save = {
        def roleAccess = new RoleAccess(params)
        if (roleAccess.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'roleAccess.label', default: 'RoleAccess'), roleAccess.id])}"
            redirect(action: "show", id: roleAccess.id)
        }
        else {
            render(view: "create", model: [roleAccess: roleAccess])
        }
    }

    def show = {
        def roleAccess = RoleAccess.get(params.id)
        if (!roleAccess) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'roleAccess.label', default: 'RoleAccess'), params.id])}"
            redirect(action: "list")
        }
        else {
            [roleAccess: roleAccess]
        }
    }

    def edit = {
        def roleAccess = RoleAccess.get(params.id)
        if (!roleAccess) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'roleAccess.label', default: 'RoleAccess'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [roleAccess: roleAccess]
        }
    }

    def update = {
        def roleAccess = RoleAccess.get(params.id)
        if (roleAccess) {
            if (params.version) {
                def version = params.version.toLong()
                if (roleAccess.version > version) {
                    
                    roleAccess.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'roleAccess.label', default: 'RoleAccess')] as Object[], "Another user has updated this RoleAccess while you were editing")
                    render(view: "edit", model: [roleAccess: roleAccess])
                    return
                }
            }
            roleAccess.properties = params
            if (!roleAccess.hasErrors() && roleAccess.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'roleAccess.label', default: 'RoleAccess'), roleAccess.id])}"
                redirect(action: "show", id: roleAccess.id)
            }
            else {
                render(view: "edit", model: [roleAccess: roleAccess])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'roleAccess.label', default: 'RoleAccess'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def roleAccess = RoleAccess.get(params.id)
        if (roleAccess) {
            try {
                roleAccess.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'roleAccess.label', default: 'RoleAccess'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'roleAccess.label', default: 'RoleAccess'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'roleAccess.label', default: 'RoleAccess'), params.id])}"
            redirect(action: "list")
        }
    }
}

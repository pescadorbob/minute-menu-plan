package com.mp.domain.themes

class PermissionDenialPageController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [permissionDenialPageList: PermissionDenialPage.list(params), permissionDenialPageTotal: PermissionDenialPage.count()]
    }

    def create = {
        def permissionDenialPage = new PermissionDenialPage()
        permissionDenialPage.properties = params
        return [permissionDenialPage: permissionDenialPage]
    }

    def save = {
        def permissionDenialPage = new PermissionDenialPage(params)
        if (permissionDenialPage.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'permissionDenialPage.label', default: 'PermissionDenialPage'), permissionDenialPage.id])}"
            redirect(action: "show", id: permissionDenialPage.id)
        }
        else {
            render(view: "create", model: [permissionDenialPage: permissionDenialPage])
        }
    }

    def show = {
        def permissionDenialPage = PermissionDenialPage.get(params.id)
        if (!permissionDenialPage) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'permissionDenialPage.label', default: 'PermissionDenialPage'), params.id])}"
            redirect(action: "list")
        }
        else {
            [permissionDenialPage: permissionDenialPage]
        }
    }

    def edit = {
        def permissionDenialPage = PermissionDenialPage.get(params.id)
        if (!permissionDenialPage) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'permissionDenialPage.label', default: 'PermissionDenialPage'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [permissionDenialPage: permissionDenialPage]
        }
    }

    def update = {
        def permissionDenialPage = PermissionDenialPage.get(params.id)
        if (permissionDenialPage) {
            if (params.version) {
                def version = params.version.toLong()
                if (permissionDenialPage.version > version) {
                    
                    permissionDenialPage.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'permissionDenialPage.label', default: 'PermissionDenialPage')] as Object[], "Another user has updated this PermissionDenialPage while you were editing")
                    render(view: "edit", model: [permissionDenialPage: permissionDenialPage])
                    return
                }
            }
            permissionDenialPage.properties = params
            if (!permissionDenialPage.hasErrors() && permissionDenialPage.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'permissionDenialPage.label', default: 'PermissionDenialPage'), permissionDenialPage.id])}"
                redirect(action: "show", id: permissionDenialPage.id)
            }
            else {
                render(view: "edit", model: [permissionDenialPage: permissionDenialPage])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'permissionDenialPage.label', default: 'PermissionDenialPage'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def permissionDenialPage = PermissionDenialPage.get(params.id)
        if (permissionDenialPage) {
            try {
                permissionDenialPage.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'permissionDenialPage.label', default: 'PermissionDenialPage'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'permissionDenialPage.label', default: 'PermissionDenialPage'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'permissionDenialPage.label', default: 'PermissionDenialPage'), params.id])}"
            redirect(action: "list")
        }
    }
}

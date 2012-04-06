package com.mp.domain.party

class GrocerController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [grocerList: Grocer.list(params), grocerTotal: Grocer.count()]
    }

    def create = {
        def grocer = new Grocer()
        grocer.properties = params
        return [grocer: grocer]
    }

    def save = {
        def grocer = new Grocer(params)
        if (grocer.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'grocer.label', default: 'Grocer'), grocer.id])}"
            redirect(action: "show", id: grocer.id)
        }
        else {
            render(view: "create", model: [grocer: grocer])
        }
    }

    def show = {
        def grocer = Grocer.get(params.id)
        if (!grocer) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'grocer.label', default: 'Grocer'), params.id])}"
            redirect(action: "list")
        }
        else {
            [grocer: grocer]
        }
    }

    def edit = {
        def grocer = Grocer.get(params.id)
        if (!grocer) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'grocer.label', default: 'Grocer'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [grocer: grocer]
        }
    }

    def update = {
        def grocer = Grocer.get(params.id)
        if (grocer) {
            if (params.version) {
                def version = params.version.toLong()
                if (grocer.version > version) {
                    
                    grocer.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'grocer.label', default: 'Grocer')] as Object[], "Another user has updated this Grocer while you were editing")
                    render(view: "edit", model: [grocer: grocer])
                    return
                }
            }
            grocer.properties = params
            if (!grocer.hasErrors() && grocer.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'grocer.label', default: 'Grocer'), grocer.id])}"
                redirect(action: "show", id: grocer.id)
            }
            else {
                render(view: "edit", model: [grocer: grocer])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'grocer.label', default: 'Grocer'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def grocer = Grocer.get(params.id)
        if (grocer) {
            try {
                grocer.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'grocer.label', default: 'Grocer'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'grocer.label', default: 'Grocer'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'grocer.label', default: 'Grocer'), params.id])}"
            redirect(action: "list")
        }
    }
}

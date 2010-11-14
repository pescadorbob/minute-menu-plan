package com.mp.domain.subscriptions

class RecurringChargeController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [recurringChargeList: RecurringCharge.list(params), recurringChargeTotal: RecurringCharge.count()]
    }

    def create = {
        def recurringCharge = new RecurringCharge()
        recurringCharge.properties = params
        return [recurringCharge: recurringCharge]
    }

    def save = {
        def recurringCharge = new RecurringCharge(params)
        if (recurringCharge.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'recurringCharge.label', default: 'RecurringCharge'), recurringCharge.id])}"
            redirect(action: "show", id: recurringCharge.id)
        }
        else {
            render(view: "create", model: [recurringCharge: recurringCharge])
        }
    }

    def show = {
        def recurringCharge = RecurringCharge.get(params.id)
        if (!recurringCharge) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'recurringCharge.label', default: 'RecurringCharge'), params.id])}"
            redirect(action: "list")
        }
        else {
            [recurringCharge: recurringCharge]
        }
    }

    def edit = {
        def recurringCharge = RecurringCharge.get(params.id)
        if (!recurringCharge) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'recurringCharge.label', default: 'RecurringCharge'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [recurringCharge: recurringCharge]
        }
    }

    def update = {
        def recurringCharge = RecurringCharge.get(params.id)
        if (recurringCharge) {
            if (params.version) {
                def version = params.version.toLong()
                if (recurringCharge.version > version) {
                    
                    recurringCharge.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'recurringCharge.label', default: 'RecurringCharge')] as Object[], "Another user has updated this RecurringCharge while you were editing")
                    render(view: "edit", model: [recurringCharge: recurringCharge])
                    return
                }
            }
            recurringCharge.properties = params
            if (!recurringCharge.hasErrors() && recurringCharge.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'recurringCharge.label', default: 'RecurringCharge'), recurringCharge.id])}"
                redirect(action: "show", id: recurringCharge.id)
            }
            else {
                render(view: "edit", model: [recurringCharge: recurringCharge])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'recurringCharge.label', default: 'RecurringCharge'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def recurringCharge = RecurringCharge.get(params.id)
        if (recurringCharge) {
            try {
                recurringCharge.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'recurringCharge.label', default: 'RecurringCharge'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'recurringCharge.label', default: 'RecurringCharge'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'recurringCharge.label', default: 'RecurringCharge'), params.id])}"
            redirect(action: "list")
        }
    }
}

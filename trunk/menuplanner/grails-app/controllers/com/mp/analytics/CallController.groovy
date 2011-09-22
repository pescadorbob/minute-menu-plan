package com.mp.analytics

class CallController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [callList: TestInterval.list(params), callTotal: TestInterval.count()]
    }

    def create = {
        def call = new TestInterval()
        call.properties = params
        return [call: call]
    }

    def save = {
        def call = new TestInterval(params)
        if (call.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'call.label', default: 'Call'), call.id])}"
            redirect(action: "show", id: call.id)
        }
        else {
            render(view: "create", model: [call: call])
        }
    }

    def show = {
        def call = TestInterval.get(params.id)
        if (!call) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'call.label', default: 'Call'), params.id])}"
            redirect(action: "list")
        }
        else {
            [call: call]
        }
    }

    def edit = {
        def call = TestInterval.get(params.id)
        if (!call) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'call.label', default: 'Call'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [call: call]
        }
    }

    def update = {
        def call = TestInterval.get(params.id)
        if (call) {
            if (params.version) {
                def version = params.version.toLong()
                if (call.version > version) {
                    
                    call.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'call.label', default: 'Call')] as Object[], "Another user has updated this Call while you were editing")
                    render(view: "edit", model: [call: call])
                    return
                }
            }
            call.properties = params
            if (!call.hasErrors() && call.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'call.label', default: 'Call'), call.id])}"
                redirect(action: "show", id: call.id)
            }
            else {
                render(view: "edit", model: [call: call])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'call.label', default: 'Call'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def call = TestInterval.get(params.id)
        if (call) {
            try {
                call.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'call.label', default: 'Call'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'call.label', default: 'Call'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'call.label', default: 'Call'), params.id])}"
            redirect(action: "list")
        }
    }
}

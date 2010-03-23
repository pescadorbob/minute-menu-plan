package com.mp.domain

class UnitController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [unitList: Unit.list(params), unitTotal: Unit.count()]
    }

    def create = {
        def unit = new Unit()
        unit.properties = params
        return [unit: unit]
    }

    def save = {
        def unit = new Unit(params)
        if (unit.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'unit.label', default: 'Unit'), unit.id])}"
            redirect(action: "show", id: unit.id)
        }
        else {
            render(view: "create", model: [unit: unit])
        }
    }

    def show = {
        def unit = Unit.get(params.id)
        if (!unit) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'unit.label', default: 'Unit'), params.id])}"
            redirect(action: "list")
        }
        else {
            [unit: unit]
        }
    }

    def edit = {
        def unit = Unit.get(params.id)
        if (!unit) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'unit.label', default: 'Unit'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [unit: unit]
        }
    }

    def update = {
        def unit = Unit.get(params.id)
        if (unit) {
            if (params.version) {
                def version = params.version.toLong()
                if (unit.version > version) {
                    
                    unit.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'unit.label', default: 'Unit')] as Object[], "Another user has updated this Unit while you were editing")
                    render(view: "edit", model: [unit: unit])
                    return
                }
            }
            unit.properties = params
            if (!unit.hasErrors() && unit.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'unit.label', default: 'Unit'), unit.id])}"
                redirect(action: "show", id: unit.id)
            }
            else {
                render(view: "edit", model: [unit: unit])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'unit.label', default: 'Unit'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def unit = Unit.get(params.id)
        if (unit) {
            try {
                unit.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'unit.label', default: 'Unit'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'unit.label', default: 'Unit'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'unit.label', default: 'Unit'), params.id])}"
            redirect(action: "list")
        }
    }
}

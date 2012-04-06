package com.mp.domain.party

class PartyAddressController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [partyAddressList: PartyAddress.list(params), partyAddressTotal: PartyAddress.count()]
    }

    def create = {
        def partyAddress = new PartyAddress()
        partyAddress.properties = params
        return [partyAddress: partyAddress]
    }

    def save = {
        def partyAddress = new PartyAddress(params)
        if (partyAddress.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'partyAddress.label', default: 'PartyAddress'), partyAddress.id])}"
            redirect(action: "show", id: partyAddress.id)
        }
        else {
            render(view: "create", model: [partyAddress: partyAddress])
        }
    }

    def show = {
        def partyAddress = PartyAddress.get(params.id)
        if (!partyAddress) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'partyAddress.label', default: 'PartyAddress'), params.id])}"
            redirect(action: "list")
        }
        else {
            [partyAddress: partyAddress]
        }
    }

    def edit = {
        def partyAddress = PartyAddress.get(params.id)
        if (!partyAddress) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'partyAddress.label', default: 'PartyAddress'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [partyAddress: partyAddress]
        }
    }

    def update = {
        def partyAddress = PartyAddress.get(params.id)
        if (partyAddress) {
            if (params.version) {
                def version = params.version.toLong()
                if (partyAddress.version > version) {
                    
                    partyAddress.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'partyAddress.label', default: 'PartyAddress')] as Object[], "Another user has updated this PartyAddress while you were editing")
                    render(view: "edit", model: [partyAddress: partyAddress])
                    return
                }
            }
            partyAddress.properties = params
            if (!partyAddress.hasErrors() && partyAddress.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'partyAddress.label', default: 'PartyAddress'), partyAddress.id])}"
                redirect(action: "show", id: partyAddress.id)
            }
            else {
                render(view: "edit", model: [partyAddress: partyAddress])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'partyAddress.label', default: 'PartyAddress'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def partyAddress = PartyAddress.get(params.id)
        if (partyAddress) {
            try {
                partyAddress.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'partyAddress.label', default: 'PartyAddress'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'partyAddress.label', default: 'PartyAddress'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'partyAddress.label', default: 'PartyAddress'), params.id])}"
            redirect(action: "list")
        }
    }
}

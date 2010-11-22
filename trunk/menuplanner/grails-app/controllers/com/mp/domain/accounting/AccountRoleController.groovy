package com.mp.domain.accounting

class AccountRoleController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [accountRoleList: AccountRole.list(params), accountRoleTotal: AccountRole.count()]
    }

    def create = {
        def accountRole = new AccountRole()
        accountRole.properties = params
        return [accountRole: accountRole]
    }

    def save = {
        def accountRole = new AccountRole(params)
        if (accountRole.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'accountRole.label', default: 'AccountRole'), accountRole.id])}"
            redirect(action: "show", id: accountRole.id)
        }
        else {
            render(view: "create", model: [accountRole: accountRole])
        }
    }

    def show = {
        def accountRole = AccountRole.get(params.id)
        if (!accountRole) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'accountRole.label', default: 'AccountRole'), params.id])}"
            redirect(action: "list")
        }
        else {
            [accountRole: accountRole]
        }
    }

    def edit = {
        def accountRole = AccountRole.get(params.id)
        if (!accountRole) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'accountRole.label', default: 'AccountRole'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [accountRole: accountRole]
        }
    }

    def update = {
        def accountRole = AccountRole.get(params.id)
        if (accountRole) {
            if (params.version) {
                def version = params.version.toLong()
                if (accountRole.version > version) {
                    
                    accountRole.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'accountRole.label', default: 'AccountRole')] as Object[], "Another user has updated this AccountRole while you were editing")
                    render(view: "edit", model: [accountRole: accountRole])
                    return
                }
            }
            accountRole.properties = params
            if (!accountRole.hasErrors() && accountRole.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'accountRole.label', default: 'AccountRole'), accountRole.id])}"
                redirect(action: "show", id: accountRole.id)
            }
            else {
                render(view: "edit", model: [accountRole: accountRole])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'accountRole.label', default: 'AccountRole'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def accountRole = AccountRole.get(params.id)
        if (accountRole) {
            try {
                accountRole.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'accountRole.label', default: 'AccountRole'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'accountRole.label', default: 'AccountRole'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'accountRole.label', default: 'AccountRole'), params.id])}"
            redirect(action: "list")
        }
    }
}

package com.mp.domain.accounting

class AccountTransactionController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [accountTransactionList: AccountTransaction.list(params), accountTransactionTotal: AccountTransaction.count()]
    }

    def create = {
        def accountTransaction = new AccountTransaction()
        accountTransaction.properties = params
        return [accountTransaction: accountTransaction]
    }

    def save = {
        def accountTransaction = new AccountTransaction(params)
        if (accountTransaction.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'accountTransaction.label', default: 'AccountTransaction'), accountTransaction.id])}"
            redirect(action: "show", id: accountTransaction.id)
        }
        else {
            render(view: "create", model: [accountTransaction: accountTransaction])
        }
    }

    def show = {
        def accountTransaction = AccountTransaction.get(params.id)
        if (!accountTransaction) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'accountTransaction.label', default: 'AccountTransaction'), params.id])}"
            redirect(action: "list")
        }
        else {
            [accountTransaction: accountTransaction]
        }
    }

    def edit = {
        def accountTransaction = AccountTransaction.get(params.id)
        if (!accountTransaction) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'accountTransaction.label', default: 'AccountTransaction'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [accountTransaction: accountTransaction]
        }
    }

    def update = {
        def accountTransaction = AccountTransaction.get(params.id)
        if (accountTransaction) {
            if (params.version) {
                def version = params.version.toLong()
                if (accountTransaction.version > version) {
                    
                    accountTransaction.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'accountTransaction.label', default: 'AccountTransaction')] as Object[], "Another user has updated this AccountTransaction while you were editing")
                    render(view: "edit", model: [accountTransaction: accountTransaction])
                    return
                }
            }
            accountTransaction.properties = params
            if (!accountTransaction.hasErrors() && accountTransaction.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'accountTransaction.label', default: 'AccountTransaction'), accountTransaction.id])}"
                redirect(action: "show", id: accountTransaction.id)
            }
            else {
                render(view: "edit", model: [accountTransaction: accountTransaction])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'accountTransaction.label', default: 'AccountTransaction'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def accountTransaction = AccountTransaction.get(params.id)
        if (accountTransaction) {
            try {
                accountTransaction.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'accountTransaction.label', default: 'AccountTransaction'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'accountTransaction.label', default: 'AccountTransaction'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'accountTransaction.label', default: 'AccountTransaction'), params.id])}"
            redirect(action: "list")
        }
    }
}

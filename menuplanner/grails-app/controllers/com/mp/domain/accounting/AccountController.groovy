package com.mp.domain.accounting

import com.mp.domain.party.Party

class AccountController {

  static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
  def accountingService
  def masterDataBootStrapService

  def bootstrapAccounts = {
    masterDataBootStrapService.populateAccounts()
    flash.message = "Accounts Populated"
    redirect(action: "list")
  }

  def index = {
    redirect(action: "list", params: params)
  }

  def list = {
    params.max = Math.min(params.max ? params.int('max') : 10, 100)
    [accountList: Account.list(params), accountTotal: Account.count()]
  }

  def create = {
    def account = new Account()
    account.properties = params
    return [account: account]
  }

  def save = {
    def account = new Account(params)
    if (account.save(flush: true)) {
      flash.message = "${message(code: 'default.created.message', args: [message(code: 'account.label', default: 'Account'), account.id])}"
      redirect(action: "show", id: account.id)
    }
    else {
      render(view: "create", model: [account: account])
    }
  }

  def show = {
    def account = Account.get(params.id)
    if (!account) {
      flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'account.label', default: 'Account'), params.id])}"
      redirect(action: "list")
    }
    else {
      [account: account]
    }
  }
  def showUserAccount = {
    def party = Party.get(params.id)
    AccountRole ar = AccountRole.findByRoleFor(party)
    def account = ar.getDescribes()
    GregorianCalendar c = new GregorianCalendar()
    c.set(Calendar.AM_PM, Calendar.AM)
    c.set(Calendar.HOUR, 0) //set the AM_PM and HOUR fields...
    c.set(Calendar.MINUTE, 00)
    c.set(Calendar.SECOND, 0)

    def from = params.from ? params.from : new Date(c.timeInMillis) - 30
    def thru = params.thru ? params.thru : new Date(c.timeInMillis)
    if (!account) {
      flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'account.label', default: 'Account'), params.id])}"
      redirect(action: "list")
    }
    else {
      [party: party, account: account, from: from, thru: thru]
    }
  }

  def showGeneralLedger = {
    def accountNum = params.accountNum ? params.accountNum : 0
    def account = Account.findByAccountNumber(accountNum)
    GregorianCalendar c = new GregorianCalendar()
    c.set(Calendar.AM_PM, Calendar.AM)
    c.set(Calendar.HOUR, 0) //set the AM_PM and HOUR fields...
    c.set(Calendar.MINUTE, 00)
    c.set(Calendar.SECOND, 0)

    def from = params.from ? params.from : new Date(c.timeInMillis) - 30
    def thru = params.thru ? params.thru : new Date(c.timeInMillis)
    [ accountNum:accountNum,account: account, from: from, thru: thru,]
  }

  def showAffiliateAccounts = {
    def minimumBalance = params.minimumBalance ? params.float('minimumBalance') : 10.0f
    def accounts = Account.list().findAll {
      accountingService.getCurrentBalance(it) >= minimumBalance
    }
    if (log.isDebugEnabled()) log.debug "Accounts: ${accounts.size} + ${Account.count()}"
    [accounts: accounts, minimumBalance: minimumBalance]
  }

  def markPaid = {

    def doTxn = { accountNum, comments ->
      def account = Account.findByAccountNumber(accountNum)
      accountingService.createTxn(params.operationalAccountNum, accountNum, new Date(),
              0.0-accountingService.getCurrentBalance(account), comments,AccountTransactionType.AFFILIATE_PAYMENT)
    }
    if (!params.accounts.class.equals(String.class)) {
      params.accounts.each {accountNum ->
        doTxn accountNum, params.comments
      }
    } else {
      doTxn params.accounts  , params.comments
    }
    render(view: "showAffiliateAccounts", model: [accounts: Account.list(), action: 'showAffiliateAccounts'])
  }
  def edit = {
    def account = Account.get(params.id)
    if (!account) {
      flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'account.label', default: 'Account'), params.id])}"
      redirect(action: "list")
    }
    else {
      return [account: account]
    }
  }

  def update = {
    def account = Account.get(params.id)
    if (account) {
      if (params.version) {
        def version = params.version.toLong()
        if (account.version > version) {

          account.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'account.label', default: 'Account')] as Object[], "Another user has updated this Account while you were editing")
          render(view: "edit", model: [account: account])
          return
        }
      }
      account.properties = params
      if (!account.hasErrors() && account.save(flush: true)) {
        flash.message = "${message(code: 'default.updated.message', args: [message(code: 'account.label', default: 'Account'), account.id])}"
        redirect(action: "show", id: account.id)
      }
      else {
        render(view: "edit", model: [account: account])
      }
    }
    else {
      flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'account.label', default: 'Account'), params.id])}"
      redirect(action: "list")
    }
  }

  def delete = {
    def account = Account.get(params.id)
    if (account) {
      try {
        account.delete(flush: true)
        flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'account.label', default: 'Account'), params.id])}"
        redirect(action: "list")
      }
      catch (org.springframework.dao.DataIntegrityViolationException e) {
        flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'account.label', default: 'Account'), params.id])}"
        redirect(action: "show", id: params.id)
      }
    }
    else {
      flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'account.label', default: 'Account'), params.id])}"
      redirect(action: "list")
    }
  }
}

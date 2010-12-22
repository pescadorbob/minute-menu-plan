package com.mp.domain.subscriptions

import com.mp.tools.UserTools
import com.mp.subscriptions.paypal.TransactionType
import com.mp.domain.accounting.AccountRole
import com.mp.domain.accounting.AccountRoleType
import com.mp.domain.party.Party
import com.mp.domain.accounting.Account
import com.mp.domain.accounting.OperationalAccount
import static com.mp.MenuConstants.MMP_OPERATIONAL_ACCOUNT
import com.mp.domain.accounting.AccountTransactionType

class SubscriptionController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    def subscriptionService
    def accountingService
    def paymentConfirm = {
        render(view: 'confirm')
    }

    def paymentCancel = {
        render(view: 'cancel')
    }

    def paymentNotify = {
        String transactionType = params.txn_type
        Long partyId = params.long('custom')
        if (transactionType && partyId && Party.exists(partyId)) {
            Party party = Party.get(partyId)
            AccountRole accountRole = AccountRole.findByTypeAndRoleFor(AccountRoleType.OWNER, party)
            Account account = accountRole.describes
            OperationalAccount operationalAccount = OperationalAccount.findByName(MMP_OPERATIONAL_ACCOUNT)

            switch (TransactionType."${transactionType}") {
                case TransactionType.SUBSCRIPTION_SIGNUP:
                    subscriptionService.generateSubscription(partyId, params.long('item_number'))
                    Float amount = params.period1 ? params.long('amount1') : params.long('amount3')
                    accountingService.createTxn(operationalAccount.accountNumber, account.accountNumber, new Date(),
                            amount, "Payment made through Paypal: *** THANK YOU", AccountTransactionType.FUNDING)
                    break;
                case TransactionType.SUBSCRIPTION_CANCELLED:
                    accountingService.createTxn(operationalAccount.accountNumber, account.accountNumber, new Date(),
                        0.0, "Subscription Cancelled", AccountTransactionType.SUBSCRIPTION_CANCELLED)
                    break;
                case TransactionType.SUBSCRIPTION_EXPIRED:
                    accountingService.createTxn(operationalAccount.accountNumber, account.accountNumber, new Date(),
                        0.0, "Subscription Expired", AccountTransactionType.SUBSCRIPTION_EXPIRED)
                    break;
                case TransactionType.SUBSCRIPTION_FAILED:
                    accountingService.createTxn(operationalAccount.accountNumber, account.accountNumber, new Date(),
                        0.0, "Subscription Expired", AccountTransactionType.SUBSCRIPTION_PAYMENT_FAILED)
                    break;
                case TransactionType.SUBSCRIPTION_PAYMENT:
                Float amount = params.long('amount3')
                    accountingService.createTxn(operationalAccount.accountNumber, account.accountNumber, new Date(),
                        amount, "Subscription Payment", AccountTransactionType.FUNDING)
                    break;
            }
        }
        render "Notify"
    }

    def index = {
        redirect(action: "list", params: params)
    }
    def createSubscription = {
        String userId = params?.userId
        int productId = params?.int('productId')
        ProductOffering po = ProductOffering.get(productId)
        String item_name = po.name
        RecurringCharge rc = po.pricing.toList().first()
        String item_description = rc.description
        String item_price = rc.value
        String item_currency = "USD"
        String item_quantity = "1"
        String recurrence = rc.recurrence
        render(view: 'connectToPaypal', model: [recurrence: recurrence, item_name: item_name,
                item_description: item_description, item_price: item_price, item_currency: item_currency,
                item_quantity: item_quantity, userId: userId.toLong(), item_number: po.id])
    }
    def list = {
        def now = new Date()
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        def c = Subscription.createCriteria()
        def subscriptionList = c.list(params) {
            subscriptionFor {
                party {
                    idEq(UserTools.currentUser.party.id)
                }
            }
            lt('activeFrom', now)
            gt('activeTo', now)
        }
        [subscriptionList: subscriptionList, subscriptionTotal: subscriptionList.size()]
    }


    def create = {
        def subscription = new Subscription()
        subscription.properties = params
        return [subscription: subscription]
    }

    def save = {
        def subscription = new Subscription(params)
        if (subscription.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'subscription.label', default: 'Subscription'), subscription.id])}"
            redirect(action: "show", id: subscription.id)
        }
        else {
            render(view: "create", model: [subscription: subscription])
        }
    }

    def show = {
        def subscription = Subscription.get(params.id)
        if (!subscription) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'subscription.label', default: 'Subscription'), params.id])}"
            redirect(action: "list")
        }
        else {
            [subscription: subscription]
        }
    }

    def edit = {
        def subscription = Subscription.get(params.id)
        if (!subscription) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'subscription.label', default: 'Subscription'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [subscription: subscription]
        }
    }

    def update = {
        def subscription = Subscription.get(params.id)
        if (subscription) {
            if (params.version) {
                def version = params.version.toLong()
                if (subscription.version > version) {

                    subscription.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'subscription.label', default: 'Subscription')] as Object[], "Another user has updated this Subscription while you were editing")
                    render(view: "edit", model: [subscription: subscription])
                    return
                }
            }
            subscription.properties = params
            if (!subscription.hasErrors() && subscription.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'subscription.label', default: 'Subscription'), subscription.id])}"
                redirect(action: "show", id: subscription.id)
            }
            else {
                render(view: "edit", model: [subscription: subscription])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'subscription.label', default: 'Subscription'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def subscription = Subscription.get(params.id)
        if (subscription) {
            try {
                subscription.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'subscription.label', default: 'Subscription'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'subscription.label', default: 'Subscription'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'subscription.label', default: 'Subscription'), params.id])}"
            redirect(action: "list")
        }
    }
}

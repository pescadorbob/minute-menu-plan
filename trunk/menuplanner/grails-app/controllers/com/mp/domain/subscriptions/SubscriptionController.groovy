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
import com.mp.domain.accounting.AccountTransaction
import org.apache.commons.httpclient.HttpClient
import org.apache.commons.httpclient.methods.PostMethod
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import com.mp.domain.VerificationToken

class SubscriptionController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    def subscriptionService
    def accountingService
    def asynchronousMailService

    def paymentConfirm = {
        render(view: '/subscription/confirm')
    }

    def paymentCancel = {
        render(view: '/subscription/cancel')
    }

    boolean validateRequestFromPayPal(String requestParameters) {
        HttpClient client = new HttpClient()
        String notificationUrl = ConfigurationHolder.config.grails.paypal.server + "?cmd=_notify-validate&" + requestParameters
        println "Notification URL: " + notificationUrl
        PostMethod method = new PostMethod(notificationUrl)
        int returnCode = client.executeMethod(method)
        def response = method.getResponseBodyAsString()
        println "Response: " + response
        return (response == 'VERIFIED')
    }

    def paymentNotify = {
        String requestParameters = params.toQueryString()[1..-1]
        boolean isValid = validateRequestFromPayPal(requestParameters)
        println "Validation: " + isValid
        if (isValid) {
            String transactionType = params.txn_type
            String transactionId = params.txn_id ?: UUID.randomUUID().toString()
            Long partyId = params.long('custom')
            if (transactionType && partyId && Party.exists(partyId)) {
                Party party = Party.get(partyId)
                AccountRole accountRole = AccountRole.findByTypeAndRoleFor(AccountRoleType.OWNER, party)
                Account account = accountRole?.describes
                if (!account) {
                    account = accountingService.createNewAccount(party)
                }
                Date now = new Date()

                switch (transactionType) {
                    case TransactionType.SUBSCRIPTION_SIGNUP.name:
                        subscriptionService.createSubscriptionForUserSignUp(party, params.long('item_number'))
                        if (party.isEnabled == null) {
                            VerificationToken verificationToken = new VerificationToken()
                            verificationToken.party = party
                            verificationToken.s()

                            asynchronousMailService.sendAsynchronousMail {
                                to party.userLogin.email
                                subject "Email verification for Minute Menu Plan"
                                html g.render(template: '/user/accountVerification', model: [party: party, email: party.email, token: verificationToken.token])
                            }
                        }
                        break;
                    case TransactionType.SUBSCRIPTION_CANCELLED.name:
                        new AccountTransaction(uniqueId: transactionId, transactionFor: account, transactionDate: now, amount: 0.0, description: "Subscription through Paypal has been cancelled", transactionType: AccountTransactionType.SUBSCRIPTION_CANCELLED).s()
                        break;
                    case TransactionType.SUBSCRIPTION_EXPIRED.name:
                        new AccountTransaction(uniqueId: transactionId, transactionFor: account, transactionDate: now, amount: 0.0, description: "Subscription through Paypal has expired", transactionType: AccountTransactionType.SUBSCRIPTION_EXPIRED).s()
                        break;
                    case TransactionType.SUBSCRIPTION_FAILED.name:
                        new AccountTransaction(uniqueId: transactionId, transactionFor: account, transactionDate: now, amount: 0.0, description: "Subscription Payment through Paypal has failed", transactionType: AccountTransactionType.SUBSCRIPTION_PAYMENT_FAILED).s()
                        break;
                    case TransactionType.SUBSCRIPTION_PAYMENT.name:
                        Float amount = params.float('amount3') ?: params.float('payment_gross')
                        new AccountTransaction(uniqueId: transactionId, transactionFor: account, transactionDate: now, amount: amount, description: "Subscription Payment Through Paypal: *** THANK YOU", transactionType: AccountTransactionType.SUBSCRIPTION_PAYMENT).s()
                        break;
                }
            }
            render "Notify"
        } else {
            response.sendError(500)
        }
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
        String startAfter = rc.startAfter
        render(view: '/subscription/connectToPaypal', model: [startAfter: startAfter, recurrence: recurrence, item_name: item_name,
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

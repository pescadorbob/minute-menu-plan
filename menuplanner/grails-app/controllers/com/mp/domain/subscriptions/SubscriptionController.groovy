package com.mp.domain.subscriptions

import com.mp.tools.UserTools
import javax.servlet.http.Cookie
import com.mp.subscriptions.paypal.PayPalTransactionType
import com.mp.domain.accounting.AccountRole
import com.mp.domain.accounting.AccountRoleType
import com.mp.domain.party.Party
import com.mp.domain.accounting.Account

import com.mp.domain.accounting.AccountTransactionType
import com.mp.domain.accounting.AccountTransaction
import org.apache.commons.httpclient.HttpClient
import org.apache.commons.httpclient.methods.PostMethod
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import com.mp.domain.VerificationToken
import com.mp.domain.UserCO
import com.mp.domain.PartyRoleType
import org.apache.commons.codec.digest.DigestUtils
import javax.servlet.http.HttpServletRequest
import com.mp.domain.UserLogin
import com.mp.subscriptions.clickBank.ClickBankTransactionType

class SubscriptionController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    def subscriptionService
    def accountingService
    def asynchronousMailService

    private String getCoachIdForClickBank(String customVariablesString) {
        Map customVariables = [:]
        customVariablesString.tokenize('&').each {String keyValue ->
            def (String key, String value) = keyValue.tokenize('=')
            if (key && value) {
                customVariables[key] = value
            }
        }
        return customVariables['coachId']
    }

    def paymentConfirm = {
        render(view: '/subscription/confirm')
    }

    def paymentCancel = {
        render(view: '/subscription/cancel')
    }

    def clickBankConfirm = {
        String email = params.cemail
        String name = params.cname
        render(view: "/subscription/clickBankConfirm", model: [name: name, email: email])
    }

    def clickBankNotify = {
        if (params.ctransaction == 'TEST') {
            render 'NOTIFICATION RECEIVED'
        } else {

            boolean isValid = validateRequestFromClickBank(request)
            println "Validation: " + isValid
            if (isValid) {
                Party.withTransaction {
                    String transactionType = params.ctransaction
                    String transactionId = params.ctransreceipt ?: UUID.randomUUID().toString()

                    if (!AccountTransaction.countByUniqueId(transactionId)) {
                        UserLogin userLogin = UserLogin.findByEmail(params.ccustemail)
                        Party party
                        AccountRole accountRole
                        Account account
                        Date now = new Date()
                        if (userLogin) {
                            party = userLogin.party
                            accountRole = AccountRole.findByTypeAndRoleFor(AccountRoleType.OWNER, party)
                            account = accountRole?.describes
                        } else {
                            UserCO userCO = new UserCO()
                            userCO.name = params.ccustfullname
                            userCO.email = params.ccustemail
                            userCO.roles = [PartyRoleType.Subscriber.name()]
                            userCO.password = params.ctransreceipt
                            userCO.confirmPassword = params.ctransreceipt
                            userCO.city = params.ccustcity
                            String coachId = getCoachIdForClickBank(params.cvendthru)
                            if (coachId) {
                                userCO.coachId = coachId
                            }
                            party = userCO.createParty()
                            account = accountingService.findOrCreateNewAccount(party)
                        }

                        if (transactionType.startsWith('TEST')) {transactionType -= 'TEST_'}
                        switch (transactionType) {
                            case ClickBankTransactionType.SUBSCRIPTION_SIGNUP.name:
                                subscriptionService.createSubscriptionForUserSignUp(party, params.long('cproditem'), now, transactionId)
                                Float amount = params.crebillamnt ? (params.long('crebillamnt') / 100).toFloat() : 0.0f
                                new AccountTransaction(uniqueId: transactionId, transactionFor: account, transactionDate: now, amount: amount, description: "Subscription Payment Received: *** THANK YOU", transactionType: AccountTransactionType.SUBSCRIPTION_PAYMENT).s()
                                party.isEnabled = true
                                party.s()
                                sendWelcomeEmail(party)
                                break;
                            case ClickBankTransactionType.SUBSCRIPTION_CANCELLED.name:
                                new AccountTransaction(uniqueId: transactionId, transactionFor: account, transactionDate: now, amount: 0.0, description: "Subscription has been cancelled", transactionType: AccountTransactionType.SUBSCRIPTION_CANCELLED).s()
                                break;
                            case ClickBankTransactionType.SUBSCRIPTION_PAYMENT.name:
                                Float amount = params.crebillamnt ? (params.long('crebillamnt') / 100).toFloat() : 0.0f
                                new AccountTransaction(uniqueId: transactionId, transactionFor: account, transactionDate: now, amount: amount, description: "Subscription Payment Received: *** THANK YOU", transactionType: AccountTransactionType.SUBSCRIPTION_PAYMENT).s()
                                subscriptionService.makeCoachAndDirectorPayments(party, amount, now, transactionId)
                                break;
                        }
                    }

                }
                render 'NOTIFICATION RECEIVED'
            }
        }

    }

    def paymentNotify = {
        String requestParameters = params.toQueryString()[1..-1]
        boolean isValid = validateRequestFromPayPal(requestParameters)
        println "Validation: " + isValid
        if (isValid) {
            String transactionType = params.txn_type
            String transactionId = params.txn_id ?: UUID.randomUUID().toString()
            String partyUniqueId = params.custom
            if (!AccountTransaction.countByUniqueId(transactionId) && transactionType && partyUniqueId && Party.countByUniqueId(partyUniqueId)) {
                Party party = Party.findByUniqueId(partyUniqueId)
                Account account = accountingService.findOrCreateNewAccount(party)
                Date now = new Date()

                switch (transactionType) {
                    case PayPalTransactionType.SUBSCRIPTION_SIGNUP.name:
                        subscriptionService.createSubscriptionForUserSignUp(party, params.long('item_number'), now, transactionId)
                        party.isEnabled = true
                        party.s()
                        sendWelcomeEmail(party)
                        break;
                    case PayPalTransactionType.SUBSCRIPTION_CANCELLED.name:
                        new AccountTransaction(uniqueId: transactionId, transactionFor: account, transactionDate: now, amount: 0.0, description: "Subscription has been cancelled", transactionType: AccountTransactionType.SUBSCRIPTION_CANCELLED).s()
                        break;
                    case PayPalTransactionType.SUBSCRIPTION_EXPIRED.name:
                        new AccountTransaction(uniqueId: transactionId, transactionFor: account, transactionDate: now, amount: 0.0, description: "Subscription has expired", transactionType: AccountTransactionType.SUBSCRIPTION_EXPIRED).s()
                        break;
                    case PayPalTransactionType.SUBSCRIPTION_FAILED.name:
                        new AccountTransaction(uniqueId: transactionId, transactionFor: account, transactionDate: now, amount: 0.0, description: "Subscription Payment has failed", transactionType: AccountTransactionType.SUBSCRIPTION_PAYMENT_FAILED).s()
                        break;
                    case PayPalTransactionType.SUBSCRIPTION_PAYMENT.name:
                        Float amount = params.float('amount3') ?: params.float('payment_gross')
                        new AccountTransaction(uniqueId: transactionId, transactionFor: account, transactionDate: now, amount: amount, description: "Subscription Payment Received: *** THANK YOU", transactionType: AccountTransactionType.SUBSCRIPTION_PAYMENT).s()
                        subscriptionService.makeCoachAndDirectorPayments(party, amount, now, transactionId)
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
        String recurrence = rc.recurrence
        String startAfter = rc.startAfter
        render(view: '/subscription/connectToPaypal', model: [startAfter: startAfter, recurrence: recurrence, item_name: item_name,
                item_description: item_description, item_price: item_price, userId: userId, item_number: po.id])
    }

    def createClickBankSubscription = {
        List<Cookie> cookies = request.cookies as List
        Cookie coachId = cookies.find {it.name == 'coachId'}
        int productId = ConfigurationHolder.config.grails.clickBank.featuredPlanId
        String vendorKey = ConfigurationHolder.config.grails.clickBank.vendorKey
        String url = "http://${productId}.${vendorKey}.pay.clickbank.net" + ((coachId) ? "?coachId=${coachId.value}" : '')
        redirect(url: url)
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

    private void sendVerificationEmail(Party party) {
        VerificationToken verificationToken = new VerificationToken()
        verificationToken.party = party
        verificationToken.s()

        asynchronousMailService.sendAsynchronousMail {
            to party.userLogin.email
            subject "Email verification for Minute Menu Plan"
            html g.render(template: '/user/accountVerification', model: [party: party, email: party.email, token: verificationToken.token])
        }
    }

    private void sendWelcomeEmail(Party party) {
        asynchronousMailService.sendAsynchronousMail {
            to party.userLogin.email
            subject "Welcome to Minute Menu Plan"
            html g.render(template: '/user/welcomeEmail', model: [party: party])
        }
    }

    private boolean validateRequestFromPayPal(String requestParameters) {
        HttpClient client = new HttpClient()
        String notificationUrl = ConfigurationHolder.config.grails.paypal.server + "?cmd=_notify-validate&" + requestParameters
        println "Notification URL: " + notificationUrl
        PostMethod method = new PostMethod(notificationUrl)
        int returnCode = client.executeMethod(method)
        def response = method.getResponseBodyAsString()
        println "Response: " + response
        return (response == 'VERIFIED')
    }

    private boolean validateRequestFromClickBank(HttpServletRequest request) {
        String secretKey = ConfigurationHolder.config.grails.clickBank.secretKey
        List ipnFields = new ArrayList();
        @SuppressWarnings("rawtypes")
        Enumeration params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String param = (String) params.nextElement();
            // cverify is computed by all POST parameters so any get parameters
            // on the notification url should be skipped as well.
            if (param.equals("cverify")) {
                continue;
            }
            ipnFields.add(param);
        }
        Collections.sort(ipnFields);
        StringBuilder pop = new StringBuilder();
        for (String field: ipnFields) {
            pop.append(request.getParameter(field));
            pop.append("|");
        }
        pop.append(secretKey);
        String expectedCVerify = DigestUtils.shaHex(pop.toString().getBytes("UTF-8")).substring(0, 8);
        return expectedCVerify.equalsIgnoreCase(request.getParameter("cverify"));
    }

}

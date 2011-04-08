package com.mp.subscriptions

import com.mp.domain.access.AccessFilter
import com.mp.domain.access.AccessFilterType
import com.mp.domain.party.Party
import com.mp.domain.party.Subscriber
import com.mp.domain.subscriptions.PricingComponent
import com.mp.domain.subscriptions.ProductOffering
import com.mp.domain.subscriptions.RecurringCharge
import groovy.time.TimeCategory
import static com.mp.MenuConstants.MMP_OPERATIONAL_ACCOUNT
import com.mp.domain.accounting.*
import com.mp.domain.subscriptions.ControllerActionFeature
import com.mp.domain.subscriptions.ProductOfferingApplicability
import com.mp.domain.subscriptions.ProductOfferingSubscription
import com.mp.domain.party.DirectorCoach
import com.mp.domain.party.CoachSubscriber
import com.mp.domain.subscriptions.BasePrice

/**
 * Created on Nov 28, 2010
 * Created by Brent Fisher
 *
 * This file contains proprietary information of Access-Wealth, LLC.
 * Copying or reproduction without prior written approval is prohibited.
 * (c) Copyright 1998-2010 Access-Wealth, LLC.
 * All rights reserved.
 */

public class SubscriptionService {

    boolean transactional = true

    def accountingService

    public void renewSubscription(Party party, ProductOffering productOffering, RecurringCharge recurringCharge, Date startDate = new Date()) {
        if (recurringCharge) {
            generateSubscription(party, productOffering, startDate)

            AccountRole accountRole = AccountRole.findByTypeAndRoleFor(AccountRoleType.OWNER, party)
            Account account = accountRole.describes
            Date now = new Date()
            OperationalAccount opAcct = OperationalAccount.findByName(MMP_OPERATIONAL_ACCOUNT)

            Float amount = recurringCharge.value
            new AccountTransaction(transactionFor: account, transactionDate: now, amount: -1 * amount, description: "Recurring Subscription Charge", transactionType: AccountTransactionType.SUBSCRIPTION_CHARGE).s()
            new AccountTransaction(transactionFor: opAcct, transactionDate: now, amount: amount, description: "Subscription Payment From Account: ${account.accountNumber}", transactionType: AccountTransactionType.SUBSCRIPTION_CHARGE).s()
        }
    }

    public void createSubscriptionForUserSignUp(Party party, long offeringId, Date startDate = new Date()) {
        ProductOffering productOffering = ProductOffering.get(offeringId)
        generateSubscription(party, productOffering, startDate)

        AccountRole accountRole = AccountRole.findByTypeAndRoleFor(AccountRoleType.OWNER, party)
        Account account = accountRole.describes
        Date now = new Date()
        OperationalAccount opAcct = OperationalAccount.findByName(MMP_OPERATIONAL_ACCOUNT)
        Float amount = productOffering.basePrice.value
        new AccountTransaction(transactionFor: account, uniqueId: UUID.randomUUID().toString(), transactionDate: now, amount: -1 * amount, description: "Initial Subscription Charge", transactionType: AccountTransactionType.SUBSCRIPTION_CHARGE).s()
        new AccountTransaction(transactionFor: opAcct, uniqueId: UUID.randomUUID().toString(), transactionDate: now, amount: amount, description: "Monthly Subscription Payment From Account: ${account.accountNumber}", transactionType: AccountTransactionType.SUBSCRIPTION_CHARGE).s()

    }

    public void makeCoachAndDirectorPayments(Party party, Float amount, Date date, String transactionId) {
        OperationalAccount opAcct = OperationalAccount.findByName(MMP_OPERATIONAL_ACCOUNT)
        CoachSubscriber coachSubscriber = CoachSubscriber.findByTo(party.subscriber)
        if (coachSubscriber) {
            Account coachAccount = accountingService.findOrCreateNewAccount(coachSubscriber.frum.party)
            accountingService.createTxn(opAcct.accountNumber, coachAccount.accountNumber, date, (amount * coachSubscriber.commission), "Coach payment for tranasaction ${transactionId}", AccountTransactionType.AFFILIATE_PAYMENT)
            DirectorCoach directorCoach = DirectorCoach.findByTo(coachSubscriber.frum)
            if (directorCoach) {
                Account directorAccount = accountingService.findOrCreateNewAccount(directorCoach.frum.party)
                accountingService.createTxn(opAcct.accountNumber, directorAccount.accountNumber, date, (amount * directorCoach.commission), "Director payment for tranasaction ${transactionId}", AccountTransactionType.AFFILIATE_PAYMENT)
            }
        }
    }

    private void generateSubscription(Party party, ProductOffering productOffering, startDate = new Date()) {
        def start
        def endDate
        Subscriber subscriber = party.subscriber
        ProductOfferingApplicability applicability = ProductOfferingApplicability.findByAvailableFor(productOffering)
        use(TimeCategory) {
            Binding binding = new Binding();
            binding.setVariable("startDate", startDate);
            GroovyShell shell = new GroovyShell(binding);
            start = shell.evaluate(applicability.applicableFrom)
            endDate = shell.evaluate(applicability.applicableThru)
        }
        ProductOfferingSubscription pos = new ProductOfferingSubscription(subscribedProductOffering: productOffering, subscriptionFor: subscriber,
                originalProductOffering: productOffering?.name, activeFrom: start, activeTo: endDate)
        assert pos.s()
    }
    // runs through all time valid subscriptions and evaluates the feature to see if it is active

    public boolean verifySubscription(user, controllerName, actionName, uri, now) {
        def subscriptionFilters = AccessFilter.withCriteria {
            filterFor {
                eq('type', AccessFilterType.SUBSCRIPTION)
                gt('activeFrom', now)
                or {
                    isNull('activeTo')
                    lt('activeTo', now)
                }
            }
        }
        boolean requiresSubscription = subscriptionFilters.find {
            (!it.controllerFilter || controllername ==~ it.controllerFilter) &&
                    (!it.actionFilter || actionName ==~ it.actionFilter) &&
                    (!it.uriFilter || uri ==~ it.uriFilter)
        }?.size() > 0
        if (!requiresSubscription) return true // its a resource unprotected by subscription access control
        if (!user) return false
        Party theParty = user?.party
        def retValue = false
        def c = Subscription.createCriteria()
        c.list {
            subscriptionFor {
                party {
                    idEq(theParty.id)
                }
            }
            le('activeFrom', now)
            ge('activeTo', now - 1)
        }.each {subscription ->
            def feature = subscription.subscribedFeature
            if (feature.class == ControllerActionFeature.class) {
                if (log.isDebugEnabled()) {
                    log.debug """feature filters:
            action:${!feature.actionFilter || actionName ==~ feature.actionFilter}
            uri:${(!feature.uriFilter || uri ==~ feature.uriFilter)}
            controller:${!feature.controllerFilter || controllerName ==~ feature.controllerFilter}
            activeFrom:${feature.activeFrom <= new Date()}
            activeTo:${(!feature.activeTo || (feature.activeTo > now))}"""
                }
                retValue |= ((!feature.uriFilter || uri ==~ feature.uriFilter)
                        && (!feature.actionFilter || actionName ==~ feature.actionFilter)
                        && (!feature.controllerFilter || controllerName ==~ feature.controllerFilter)
                        && (feature.activeFrom <= now)
                        && (!feature.activeTo || (feature.activeTo > now)))
            }

        }
        retValue
    }

}

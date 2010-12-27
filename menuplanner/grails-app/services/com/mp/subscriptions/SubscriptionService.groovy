package com.mp.subscriptions

import com.mp.domain.party.Party
import com.mp.domain.subscriptions.Subscription
import com.mp.domain.subscriptions.ControllerActionFeature
import com.mp.domain.party.Subscriber
import com.mp.domain.subscriptions.ProductOffering
import groovy.time.TimeCategory
import com.mp.domain.access.AccessFilter
import com.mp.domain.access.AccessFilterType
import com.mp.domain.subscriptions.ProductOfferingSubscription
import com.mp.domain.subscriptions.ProductOfferingApplicability
import com.mp.domain.accounting.OperationalAccount
import com.mp.domain.accounting.AccountRoleType
import com.mp.domain.accounting.AccountRole
import com.mp.domain.accounting.Account
import com.mp.domain.accounting.AccountTransactionType
import static com.mp.MenuConstants.MMP_OPERATIONAL_ACCOUNT
import com.mp.domain.subscriptions.PricingComponent
import com.mp.domain.accounting.AccountTransaction
import com.mp.domain.subscriptions.RecurringCharge
import org.apache.commons.math.stat.descriptive.summary.Product

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

    def accountingService

    public void renewSubscription(Party party, ProductOffering productOffering, Date startDate = new Date()) {
        generateSubscription(party, productOffering, startDate)

        AccountRole accountRole = AccountRole.findByTypeAndRoleFor(AccountRoleType.OWNER, party)
        Account account = accountRole.describes
        Date now = new Date()
        OperationalAccount opAcct = OperationalAccount.findByName(MMP_OPERATIONAL_ACCOUNT)
        productOffering.pricing.each {PricingComponent pricingComponent ->
            Float amount = pricingComponent.value
            new AccountTransaction(transactionFor: account, transactionDate: now, amount: -1 * amount, description: "Recurring Subscription Charge", transactionType: AccountTransactionType.SUBSCRIPTION_PAYMENT).s()
            new AccountTransaction(transactionFor: opAcct, transactionDate: now, amount: amount, description: "Subscription Payment From Account: ${account.accountNumber}", transactionType: AccountTransactionType.SUBSCRIPTION_PAYMENT).s()
        }

    }

    public void createSubscriptionForUserSignUp(Party party, long offeringId, Date startDate = new Date()) {
        ProductOffering productOffering = ProductOffering.get(offeringId)
        generateSubscription(party, productOffering, startDate)

        Account account
        AccountRole accountRole = AccountRole.findByTypeAndRoleFor(AccountRoleType.OWNER, party)
        if (accountRole) {
            account = accountRole.describes
        } else {
            account = new Account(name: "General Account:${party.name}").s()
            new AccountRole(roleFor: party, describes: account, type: AccountRoleType.OWNER).s()
        }
        Date now = new Date()
        OperationalAccount opAcct = OperationalAccount.findByName(MMP_OPERATIONAL_ACCOUNT)
        new AccountTransaction(transactionFor: account, transactionDate: now, amount: 0.0, description: "Opening Balance", transactionType: AccountTransactionType.OPENING_BALANCE).s()
        productOffering.pricing.each {PricingComponent pricingComponent ->
            Float amount = pricingComponent.value
            if (pricingComponent.instanceOf(RecurringCharge.class) && pricingComponent.startAfter) {
                amount = 0.0
            }
            new AccountTransaction(transactionFor: account, transactionDate: now, amount: -1 * amount, description: "Initial Subscription Charge", transactionType: AccountTransactionType.SUBSCRIPTION_PAYMENT).s()
            new AccountTransaction(transactionFor: opAcct, transactionDate: now, amount: amount, description: "Monthly Subscription Payment From Account: ${account.accountNumber}", transactionType: AccountTransactionType.SUBSCRIPTION_PAYMENT).s()
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

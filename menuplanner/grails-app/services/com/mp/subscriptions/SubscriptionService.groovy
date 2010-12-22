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

    public void generateSubscription(buyerId, offeringId, startDate = new Date()) {
        def start
        def endDate
        Party party = Party.get(buyerId)
        Subscriber subscriber = party.subscriber
        ProductOffering productOffering = ProductOffering.get(offeringId)
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
        accountingService.createTxn(opAcct.accountNumber, account.accountNumber, now, 0.0, "Opening Balance", AccountTransactionType.OPENING_BALANCE)
        productOffering.pricing.each {PricingComponent pricingComponent ->
            accountingService.createTxn(opAcct.accountNumber, account.accountNumber, now, pricingComponent.value, "Monthly Subscription Charge", AccountTransactionType.SUBSCRIPTION_PAYMENT)
        }
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

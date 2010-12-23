package com.mp.subscriptions

import com.mp.domain.subscriptions.ProductOfferingSubscription
import com.mp.domain.accounting.AccountRole
import com.mp.domain.accounting.AccountRoleType
import com.mp.domain.accounting.Account
import com.mp.domain.subscriptions.ProductOffering
import com.mp.domain.party.Party

class RenewSubscriptions {

    static triggers = {
        simple name: 'renewSubscriptionsTrigger', startDelay: 60 * 1000, repeatInterval: 30 * 1000
    }
    def concurrent = false

    def userService
    def subscriptionService

    def execute() {
        println "Executing Renew Subscriptions Job"
        Date now = new Date()
        List<ProductOfferingSubscription> subscriptions = ProductOfferingSubscription.findAllByStatus(SubscriptionStatus.CURRENT)
        subscriptions.each {ProductOfferingSubscription subscription ->
            if (subscription.activeTo < now) {
                subscription.status = SubscriptionStatus.EXPIRED
                subscription.s()
            }
        }

        subscriptions = ProductOfferingSubscription.findAllByStatus(SubscriptionStatus.EXPIRED)
        subscriptions.each {ProductOfferingSubscription subscription ->
            Party party = subscription.subscriptionFor.party
            if (party) {
                AccountRole accountRole = AccountRole.findByTypeAndRoleFor(AccountRoleType.OWNER, party)
                Account account = accountRole.describes
                ProductOffering productOffering = subscription.subscribedProductOffering
                Float costOfOffering = productOffering.pricing.sum {it.value} as Float
                if (account.balance > costOfOffering) {
                    subscriptionService.renewSubscription(party, productOffering, subscription.activeTo)
                    subscription.status = SubscriptionStatus.RENEWED
                    subscription.s()
                }
            }
        }

    }


}


package com.mp.subscriptions

import com.mp.domain.LoginCredential
import com.mp.domain.party.Party
import com.mp.domain.subscriptions.Subscription
import com.mp.domain.subscriptions.ControllerActionFeature
import com.mp.domain.subscriptions.FeatureSubscription
import com.mp.domain.subscriptions.Feature
import com.mp.domain.party.Subscriber
import com.mp.domain.subscriptions.ProductOffering
import groovy.time.TimeCategory
import com.mp.domain.access.AccessFilter
import com.mp.domain.access.AccessFilterType

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

    public void generateSubscription(buyerId, offeringId, startDate = new Date()) {
        Party party= Party.get(buyerId)
        Subscriber subscriber = party.subscriber
        def productOffering = ProductOffering.get(offeringId)
        productOffering?.applicableFeatures?.each {applicability ->
            def feature = applicability.describedBy
            use(TimeCategory) {
                Binding binding = new Binding();
                binding.setVariable("startDate", startDate);
                GroovyShell shell = new GroovyShell(binding);

                def start = shell.evaluate(applicability.applicableFrom)
                def endDate = shell.evaluate(applicability.applicableThru)
                def fs = new FeatureSubscription(subscribedFeature: feature, subscriptionFor: subscriber,
                        originalProductOffering: feature?.name, activeFrom: start, activeTo: endDate)
                assert fs.s()
            }
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

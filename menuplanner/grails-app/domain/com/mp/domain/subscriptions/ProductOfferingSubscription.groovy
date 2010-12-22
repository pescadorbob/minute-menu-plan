package com.mp.domain.subscriptions
/**
 * Created on Nov 13, 2010
 * Created by Brent Fisher
 *
 */

public class ProductOfferingSubscription extends Subscription {
    ProductOffering subscribedProductOffering

    static mapping = {
        tablePerHierarchy false
    }
}
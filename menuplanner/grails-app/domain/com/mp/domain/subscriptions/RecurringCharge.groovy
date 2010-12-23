package com.mp.domain.subscriptions
/**
 * Created on Nov 13, 2010
 * Created by Brent Fisher
 *
 */

public class RecurringCharge extends PricingComponent {
    String recurrence
    String startAfter

    static mapping = {
        tablePerHierarchy false
    }
    static constraints = {
        startAfter(nullable: true)
    }

}
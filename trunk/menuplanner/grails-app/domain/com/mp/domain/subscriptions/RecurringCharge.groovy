package com.mp.domain.subscriptions
/**
 * Created on Nov 13, 2010
 * Created by Brent Fisher
 *
 */
 
public class RecurringCharge extends PricingComponent {
         static mapping = {
        tablePerHierarchy false
    }
  String recurrence
}
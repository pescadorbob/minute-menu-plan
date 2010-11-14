package com.mp.domain.subscriptions
/**
 * Created on Nov 13, 2010
 * Created by Brent Fisher
 *
 * This file contains proprietary information of Norkom, Inc.
 * Copying or reproduction without prior written approval is prohibited.
 * (c) Copyright 1998-2010 Norkom, Inc.
 * All rights reserved.
 */
 
public class RecurringCharge extends PricingComponent {
         static mapping = {
        tablePerHierarchy false
    }
  String recurrence
}
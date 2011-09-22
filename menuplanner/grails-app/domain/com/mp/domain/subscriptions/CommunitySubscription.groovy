package com.mp.domain.subscriptions
/**
 * Created on Jul 18, 2011
 * Created by Brent Fisher
 *
 * This file contains proprietary information of Access-Wealth, LLC
 * Copying or reproduction without prior written approval is prohibited.
 * (c) Copyright 1998-2011 Access-Wealth, LLC
 * All rights reserved.
 */

public class CommunitySubscription extends ProductOfferingSubscription {
  static hasMany = [requirements: SubscriptionContributionRequirement]
  

}
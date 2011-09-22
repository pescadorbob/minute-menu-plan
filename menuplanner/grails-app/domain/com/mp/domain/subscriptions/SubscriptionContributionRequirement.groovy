package com.mp.domain.subscriptions
/**
 * Created on Aug 31, 2011
 * Created by Brent Fisher
 *
 * This file contains proprietary information of Access-Wealth, LLC
 * Copying or reproduction without prior written approval is prohibited.
 * (c) Copyright 1998-2011 Access-Wealth, LLC
 * All rights reserved.
 */

public class SubscriptionContributionRequirement {
static belongsTo = [requiredFor: CommunitySubscription]
  Date created
  Date requiredBy
  Date fulfilledDate
  CommunitySubscription requiredFor
  ContributionRequirement requires
  ContributionFulfillment fulfilledBy

  static constraints = {
    requiredBy(nullable: true)
    fulfilledDate(nullable: true)
    fulfilledBy(nullable: true)
  }
}
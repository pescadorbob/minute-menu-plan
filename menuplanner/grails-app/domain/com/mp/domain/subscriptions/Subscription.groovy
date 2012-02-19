package com.mp.domain.subscriptions

import com.mp.domain.party.Subscriber
import com.mp.subscriptions.SubscriptionStatus

/**
 * Created on Nov 13, 2010
 * Created by Brent Fisher
 *
 */

public class Subscription {
  static belongsTo = [subscriptionFor:Subscriber]
    static hasMany = [requirements: SubscriptionContributionRequirement]

  SubscriptionStatus status = SubscriptionStatus.CURRENT
  Subscriber subscriptionFor
  String originalProductOffering
  Date activeFrom
  Date activeTo
   static mapping = {
        tablePerHierarchy false
    }
}
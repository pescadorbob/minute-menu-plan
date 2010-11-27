package com.mp.domain.subscriptions

import com.mp.domain.party.Subscriber

/**
 * Created on Nov 13, 2010
 * Created by Brent Fisher
 *
 */

public class Subscription {
  static belongsTo = [subscriptionFor:Subscriber]

  Subscriber subscriptionFor
  String originalProductOffering
  Date activeFrom
  Date activeThru
   static mapping = {
        tablePerHierarchy false
    }
}
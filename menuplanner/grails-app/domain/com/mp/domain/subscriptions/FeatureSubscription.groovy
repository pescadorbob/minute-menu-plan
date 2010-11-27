package com.mp.domain.subscriptions
/**
 * Created on Nov 13, 2010
 * Created by Brent Fisher
 *
 */
 
public class FeatureSubscription extends Subscription{
  Feature subscribedFeature

   static mapping = {
        tablePerHierarchy false
    }
}
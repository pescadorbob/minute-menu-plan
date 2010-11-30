package com.mp.domain.subscriptions
/**
 * Created on Nov 13, 2010
 * Created by Brent Fisher
 *
 */
 
public class ProductOffering {
  Date activeTo
  Date activeFrom
  String name
  static hasMany = [pricing: PricingComponent, applicableFeatures: FeaturedOfferingApplicability]

}
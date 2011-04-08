package com.mp.domain.subscriptions
/**
 * Created on Nov 13, 2010
 * Created by Brent Fisher
 *
 */
 
public class PricingComponent {
static belongsTo = ProductOffering

ProductOffering pricingFor
  Date activeTo
  Date activeFrom
  Float value
  String name
  String description

    static mapping = {
        tablePerHierarchy false
    }

}
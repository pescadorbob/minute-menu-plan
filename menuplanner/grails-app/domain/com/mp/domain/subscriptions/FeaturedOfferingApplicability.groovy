package com.mp.domain.subscriptions
/**
 * Created on Nov 13, 2010
 * Created by Brent Fisher
 *
 */
 
public class FeaturedOfferingApplicability {
  ProductOffering availableFor
  Feature describedBy
  String applicableFrom //purchaseDate
  String applicableFromDescription
  String applicableThru //purchaseDate + 1m
  String applicableThruDescription //purchaseDate + 1m
  static belongsTo = [availableFor: ProductOffering]

}
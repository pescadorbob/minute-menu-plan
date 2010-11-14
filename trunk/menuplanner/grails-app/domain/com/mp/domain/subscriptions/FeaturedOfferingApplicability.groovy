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
 
public class FeaturedOfferingApplicability {
  ProductOffering availableFor
  Feature describedBy
  String applicableFrom //purchaseDate
  String applicableFromDescription
  String applicableThru //purchaseDate + 1m
  String applicableThruDescription //purchaseDate + 1m
  static belongsTo = [availableFor: ProductOffering]

}
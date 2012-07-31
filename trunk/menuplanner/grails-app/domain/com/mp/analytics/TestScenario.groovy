package com.mp.analytics
/**
 * Created on Jun 19, 2011
 * Created by Brent Fisher
 *
 * This file contains proprietary information of Access-Wealth, LLC
 * Copying or reproduction without prior written approval is prohibited.
 * (c) Copyright 1998-2011 Access-Wealth, LLC
 * All rights reserved.
 */

public class TestScenario {   
  String name
  String description
  String notes
  boolean active
  static hasMany = [requests: AppRequest]
  static constraints = {
    description(nullable: true, blank: true, maxSize: 5000)
    notes(nullable: true, blank: true, maxSize: 5000)
  }
}
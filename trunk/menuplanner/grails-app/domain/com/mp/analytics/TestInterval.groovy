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

public class TestInterval {

  static belongsTo = [within: AppRequest]
  AppRequest within
  String name
  long inTime;
  long outTime;
  long total;
  String details;
  static constraints = {
    outTime(nullable: true)
    total(nullable: true)
    details(nullable: true, blank: true, maxSize: 5000)
  }
}
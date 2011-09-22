package com.mp.analytics
/**
 * Created on Jun 18, 2011
 * Created by Brent Fisher
 *
 * This file contains proprietary information of COMPANY
 * Copying or reproduction without prior written approval is prohibited.
 * (c) Copyright 1998-2011 COMPANY
 * All rights reserved.
 */

class AppRequest implements Cloneable {
  String uniqueId
  static belongsTo = [within: TestScenario]
  TestScenario within
  static hasMany = [calls:TestInterval]
  static constraints = {
      uniqueId(nullable: true)
  }
}
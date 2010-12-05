package com.mp.domain.access
/**
 * Created on Dec 4, 2010
 * Created by Brent Fisher
 *
 * This file contains proprietary information of Access-Wealth, LLC.
 * Copying or reproduction without prior written approval is prohibited.
 * (c) Copyright 1998-2010 Access-Wealth, LLC.
 * All rights reserved.
 */

public class AccessFilter {
  String name
  String description
  String controllerFilter
  String actionFilter
  String uriFilter
  static belongsTo = [filterFor: AccessFilterSet]
  static hasMany = [roleAccess: RoleAccess]
  
  static constraints = {
    name(nullable: true)
    controllerFilter(nullable: true)
    actionFilter(nullable: true)
    uriFilter(nullable: true)
  }
}
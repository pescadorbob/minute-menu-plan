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

public class AccessFilterSet {
  static hasMany = [accessFilters: AccessFilter]

  String name
  String description
  Date activeFrom = new Date()
  Date activeTo
  AccessFilterType type
  static constraints = {
    name(nullable: true)
    description(nullable: true)
    activeTo(nullable: true)
  }

}
package com.mp.domain.themes
/**
 * Created on Dec 4, 2010
 * Created by Brent Fisher
 *
 * This file contains proprietary information of Access-Wealth, LLC.
 * Copying or reproduction without prior written approval is prohibited.
 * (c) Copyright 1998-2010 Access-Wealth, LLC.
 * All rights reserved.
 */
 
public class WebPage {
  String name
   String activeFrom
  String activeTo
  String template
  Date lastModified = new Date()

  static constraints = {
      name(unique: true, blank: false)
      template(nullable: true, blank: true, maxSize: 10000)
      lastModified(nullable: true, blank: true)
      activeTo(nullable: true, blank: true)
  }
  static mapping = {
        tablePerHierarchy false
    }

}
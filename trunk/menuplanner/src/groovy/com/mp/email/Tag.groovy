package com.mp.email

import grails.util.GrailsUtil

/**
 * Created on Apr 7, 2011
 * Created by Brent Fisher
 *
 * This file contains proprietary information of COMPANY
 * Copying or reproduction without prior written approval is prohibited.
 * (c) Copyright 1998-2011 COMPANY
 * All rights reserved.
 */
 
public class Tag {
  static  comment = {
    GrailsUtil.environment == "production"?"[tag: comment]":''
  }
  static  shoppingList = {
    GrailsUtil.environment == "production"?"[tag: shopping-list]":''
  }
  static  note = {
    GrailsUtil.environment == "production"?"[tag: note] ":''
  }
  static  passwordReset = {
    GrailsUtil.environment == "production"?"[tag: password-reset]":''
  }
  static  emailVerification = {
    GrailsUtil.environment == "production"?"[tag: email-verification]":''
  }
  static  message = {
    GrailsUtil.environment == "production"?"[tag: message]":''
  }
}
package com.mp.domain.accounting
/**
 * Created on Nov 19, 2010
 * Created by Brent Fisher
 *
 * This file contains proprietary information of Norkom, Inc.
 * Copying or reproduction without prior written approval is prohibited.
 * (c) Copyright 1998-2010 Norkom, Inc.
 * All rights reserved.
 */
 
enum AccountRoleType {
   OWNER('OWNER')

  public AccountRoleType(name){
    this.name = name
  }
  final String name

  public String toString(){

  }
}
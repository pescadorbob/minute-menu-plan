package com.mp.domain.ndb
/**
 * Created on Apr 10, 2011
 * Created by Brent Fisher
 *
 * This file contains proprietary information of COMPANY
 * Copying or reproduction without prior written approval is prohibited.
 * (c) Copyright 1998-2011 COMPANY
 * All rights reserved.
 */
 
public class NDBWeight {
  NDBFood weightFor
  Integer seq
  Float amount
  String msreDesc
  Float gmWgt
  Float numDataPts
  Float stdDev
  static constraints = {
     numDataPts(nullable:true)
     stdDev(nullable:true)
  }
}
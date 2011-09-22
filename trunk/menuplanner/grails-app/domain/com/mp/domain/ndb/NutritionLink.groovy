package com.mp.domain.ndb

import com.mp.domain.Unit

/**
 * Created on Apr 10, 2011
 * Created by Brent Fisher
 *
 * This file contains proprietary information of COMPANY
 * Copying or reproduction without prior written approval is prohibited.
 * (c) Copyright 1998-2011 COMPANY
 * All rights reserved.
 */

public class NutritionLink {
  NDBWeight nutrition
  Unit unit
  Float factor
  Date frum
  Date thru
  static constraints = {
       thru(nullable: true)
       unit(nullable: true)
   }
  
}
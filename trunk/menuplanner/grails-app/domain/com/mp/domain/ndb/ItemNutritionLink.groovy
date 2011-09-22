package com.mp.domain.ndb

import com.mp.domain.PreparationMethod
import com.mp.domain.Item

/**
 * Created on Apr 10, 2011
 * Created by Brent Fisher
 *
 * This file contains proprietary information of COMPANY
 * Copying or reproduction without prior written approval is prohibited.
 * (c) Copyright 1998-2011 COMPANY
 * All rights reserved.
 */

public class ItemNutritionLink extends NutritionLink {
  Item product
  PreparationMethod prep
  static constraints = {
    prep(nullable:true)
  }

  String toString() {
    def result = "Link:${product} (${prep}) ${unit} ${factor}"
    return result.trim()
  }

}
package com.mp.domain.pricing
/**
 * Created by IntelliJ IDEA.
 * User: Brent Fisher
 * Date: Mar 9, 2012
 * Time: 1:04:07 PM
 * To change this template use File | Settings | File Templates.
 */

enum PriceType {
  AVE('Average'),
  SINGLE('Single'),
  HIGH('High'),
  LOW('Low')

  final String name

  PriceType(String name) {
      this.name = name
  }

  public String toString() {
      return name
  }

}

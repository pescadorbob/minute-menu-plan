package com.mp.domain
/**
 * Created on Nov 15, 2010
 * Created by Brent Fisher
 *
 */

enum ElementLocation {

  CSS('CSS'),
  HEADER('HEADER'),
  MENU('MENU'),
  TOP('TOP'),
  RIGHT('RIGHT'),
  FOOTER('FOOTER'),
  LEFT('LEFT'),
  CENTER('CENTER')

  final String name

  ElementLocation(String name) {
    this.name = name
  }

  public String toString() {
    return name
  }

}
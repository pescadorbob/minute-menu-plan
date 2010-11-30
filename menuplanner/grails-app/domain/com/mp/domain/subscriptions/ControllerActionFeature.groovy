package com.mp.domain.subscriptions
/**
 * Created on Nov 13, 2010
 * Created by Brent Fisher
 *
 */
                           
public class ControllerActionFeature extends Feature {
  String controllerFilter
  String actionFilter
  String uriFilter
  static constraints = {
        controllerFilter(nullable: true)
        actionFilter(nullable: true)
        uriFilter(nullable: true)
    }
}

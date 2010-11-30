package com.mp.domain.subscriptions
/**
 * Created on Nov 13, 2010
 * Created by Brent Fisher
 *
 */

public class Feature {
  Date activeTo
  Date activeFrom
  String name
  String description
  String rule
  static hasMany = [usedToDefine: FeaturedOfferingApplicability]
  static constraints = {
    rule(nullable: true)
    activeTo(nullable: true)
  }
}
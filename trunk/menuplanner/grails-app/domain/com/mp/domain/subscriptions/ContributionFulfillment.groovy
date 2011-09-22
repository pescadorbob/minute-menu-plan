package com.mp.domain.subscriptions

class ContributionFulfillment {

  Date created
  Date completed
  static constraints = {
      completed(nullable: true)
  }

}

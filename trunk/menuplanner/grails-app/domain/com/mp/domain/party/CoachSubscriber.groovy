package com.mp.domain.party

class CoachSubscriber extends PartyRelationship {

  Float commission =0;// the commission this coach receives for this 'coaching' this subscriber
  static constraints = {
    commission(nullable:true)
  }

  static mapping = {
    tablePerHierarchy false
  }

}

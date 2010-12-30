package com.mp.domain.party

class DirectorCoach extends PartyRelationship {

  Float commission = 0// the commission that a Director receives for coaching from each subscriber payment
    static constraints = {
      commission(nullable:true)
    }

    static mapping = {
        tablePerHierarchy false
    }

}

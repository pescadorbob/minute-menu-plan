package com.mp.domain.party

class DirectorCoach extends PartyRelationship {

  Float commission = 0// the commission that a Director receives for coaching from each subscriber payment
  Float defaultClientCommission = 0 // the default commission that a coach gets from a client
    static constraints = {
      commission(nullable:true)
      defaultClientCommission(nullable:true)
    }

    static mapping = {
        tablePerHierarchy false
    }

}

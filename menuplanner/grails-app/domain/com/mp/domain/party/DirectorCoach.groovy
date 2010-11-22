package com.mp.domain.party

class DirectorCoach extends PartyRoleRelationship {

  float commission // the commission that a Director receives from a coach
  float defaultClientCommission// the default commission that a coach gets from a client
    static constraints = {
    }

    static mapping = {
        tablePerHierarchy false
    }

}

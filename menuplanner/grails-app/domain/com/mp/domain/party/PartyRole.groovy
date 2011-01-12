package com.mp.domain.party

import com.mp.domain.access.RoleAccess

class PartyRole {

    Party party
    Date activeFrom = new Date()
    Date activeTo
    static belongsTo = [party: Party]
  static hasMany = [roleAccess: RoleAccess]

    static constraints = {
      activeTo(nullable:true)
    }

    static mapping = {
        tablePerHierarchy false
    }

}

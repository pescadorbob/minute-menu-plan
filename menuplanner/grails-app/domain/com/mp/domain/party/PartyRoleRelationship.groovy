package com.mp.domain.party

class PartyRoleRelationship {

    PartyRole client
    PartyRole supplier

  Date activeFrom
  Date activeTo

    static belongsTo = [client: PartyRole]

    static constraints = {
      activeTo(nullable: true)
    }

    static mapping = {
        tablePerHierarchy false
    }

}

package com.mp.domain.party

class PartyRole {

    Party party
    Date activeFrom = new Date()
    Date activeTo
    static belongsTo = [party: Party]

    static constraints = {
      activeTo(nullable:true)
    }

    static mapping = {
        tablePerHierarchy false
    }

}

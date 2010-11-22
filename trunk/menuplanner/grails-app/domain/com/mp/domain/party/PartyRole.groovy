package com.mp.domain.party

class PartyRole {

    Party party

    static belongsTo = [party: Party]

    static constraints = {
    }

    static mapping = {
        tablePerHierarchy false
    }

}

package com.mp.domain

class PartyRole {

    Party party

    static belongsTo = [party: Party]

    static constraints = {
    }

    static mapping = {
        tablePerHierarchy false
    }

}

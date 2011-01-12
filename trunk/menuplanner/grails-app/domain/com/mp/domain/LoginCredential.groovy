package com.mp.domain

import com.mp.domain.party.Party

public class LoginCredential {
    Party party
    static belongsTo = [party: Party]

    static constraints = {
    }

    static mapping = {
        party lazy: false
        tablePerHierarchy false
    }

}

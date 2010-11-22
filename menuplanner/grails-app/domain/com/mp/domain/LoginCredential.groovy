package com.mp.domain

import com.mp.domain.party.Party

class LoginCredential {
    Party party
    static belongsTo = [party: Party]

    static transients = ['currentUser']

    static LoginCredential getCurrentUser() {
        Long userId = SessionUtils?.session?.loggedUserId?.toLong()
        LoginCredential current
        if (userId) {
            LoginCredential.withSession {
                Party currentParty = com.mp.domain.party.Party.get(userId)
                if (currentParty) {
                    current = LoginCredential.findByParty(currentParty)
                }
            }
        }
        return current
    }

    static constraints = {
    }

    static mapping = {
        party lazy: false
        tablePerHierarchy false
    }

}

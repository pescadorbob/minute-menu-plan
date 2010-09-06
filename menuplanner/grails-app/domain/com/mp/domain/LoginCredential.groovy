package com.mp.domain

class LoginCredential {
    Party party
    static belongsTo = [party: Party]

    static transients = ['currentUser']

    static LoginCredential getCurrentUser() {
        Long userId = SessionUtils?.session?.loggedUserId?.toLong()
        LoginCredential current
        if(userId){
            LoginCredential.withSession{
                current = LoginCredential.get(userId)
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

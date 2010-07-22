package com.mp.domain

class LoginCredential {
    Party party
    static belongsTo = [party: Party]

    static transients = ['currentUser']

    static LoginCredential getCurrentUser() {
        Long userId = SessionUtils.session.loggedUserId?.toLong()
        return ((userId) ? LoginCredential.get(userId) : null)
    }

    static constraints = {
    }

    static mapping = {
        tablePerHierarchy false
    }

}

package com.mp.domain

class LoginCredential {
    Party party
    String email
    String password
    static belongsTo = [party: Party]

    static transients = ['currentUser']

    static LoginCredential getCurrentUser() {
        Long userId = SessionUtils.session.loggedUserId?.toLong()
        return ((userId) ? LoginCredential.get(userId) : null)
    }

    static constraints = {
        email(email: true, unique: true)
        password(nullable: true, blank: true)
    }
}

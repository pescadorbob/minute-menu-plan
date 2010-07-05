package com.mp.domain

class LoginCredential {
    User user
    String email
    String password
    static belongsTo = [user: User]

    static constraints = {
        email(email: true, unique: true)
        password(nullable: true, blank: true)
    }
}

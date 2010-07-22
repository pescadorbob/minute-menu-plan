package com.mp.domain

class UserLogin extends LoginCredential{

    String email
    String password

    static constraints = {
        email(email: true, unique: true)
        password(nullable: true, blank: true)
    }

    static mapping = {
        tablePerHierarchy false
    }

}

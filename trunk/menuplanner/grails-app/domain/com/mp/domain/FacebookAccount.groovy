package com.mp.domain

class FacebookAccount extends LoginCredential {

    Long uid
    String oauthToken

    static constraints = {
    }

    static mapping = {
        tablePerHierarchy false
    }

}

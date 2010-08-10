package com.mp.domain

class FacebookAccount extends LoginCredential {

    Long uid
    String oauthToken

    static constraints = {
        uid(nullable:false,blank:false,unique:true)
        oauthToken(nullable:false,blank:false)
    }

    static mapping = {
        tablePerHierarchy false
    }

}

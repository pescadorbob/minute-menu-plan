package com.mp.domain

class FacebookAccount {

    Party party
    Long uid
    String oauthToken

    static belongsTo = [party: Party]

    static constraints = {
    }


}

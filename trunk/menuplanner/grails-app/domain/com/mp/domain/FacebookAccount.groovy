package com.mp.domain

class FacebookAccount {

    User user
    Long uid
    String oauthToken

    static belongsTo = [user: User]

    static constraints = {
    }


}

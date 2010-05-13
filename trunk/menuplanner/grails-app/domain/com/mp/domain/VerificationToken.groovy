package com.mp.domain

class VerificationToken {

    String token = UUID.randomUUID().toString()
    User user

}

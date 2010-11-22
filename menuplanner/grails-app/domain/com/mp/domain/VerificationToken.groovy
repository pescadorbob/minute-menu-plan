package com.mp.domain

import com.mp.domain.party.Party

class VerificationToken {

    String token = UUID.randomUUID().toString()
    Party party
}

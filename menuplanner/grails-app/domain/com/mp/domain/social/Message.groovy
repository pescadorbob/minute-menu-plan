package com.mp.domain.social

import com.mp.domain.party.Party

public class Message {

  static hasMany = [history: MessageStatus,replies: Message]

    Party frum
    Party to
    String subject
    String body
    Message replyTo

    static constraints = {
        body(blank:true, size:0..5000)
        subject(blank:true, nullable:true)
        replyTo(nullable:true)
    }

}
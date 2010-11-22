package com.mp.domain

import org.grails.comments.Comment
import com.mp.domain.party.Party

class CommentAbuse {

    Comment comment
    Party reporter

    static constraints = {
    }
}

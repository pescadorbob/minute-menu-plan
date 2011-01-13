package com.mp.domain

import com.mp.domain.party.Party

class MenuPlan {

    String name
    List<Week> weeks = []

    static hasMany = [weeks: Week]
    static belongsTo = [owner: Party]

    static constraints = {
    }

    static mapping = {
        weeks casade: 'all-delete-orphan', fetch: 'join'
        sort 'name'
    }

    String toString() {
        return name
    }
}

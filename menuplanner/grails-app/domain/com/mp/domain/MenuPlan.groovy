package com.mp.domain

import com.mp.domain.party.Party
import com.mp.domain.pricing.Price

class MenuPlan {

    String name
    List<Week> weeks = []
    BigDecimal cost

    static hasMany = [weeks: Week]
    static belongsTo = [owner: Party]

    static constraints = {
      cost(nullable:true)
    }

    static mapping = {
        weeks casade: 'all-delete-orphan', fetch: 'join'
        sort 'name'
    }

    String toString() {
        return name
    }
}

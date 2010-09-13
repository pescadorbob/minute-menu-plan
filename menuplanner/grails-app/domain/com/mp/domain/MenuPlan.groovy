package com.mp.domain

class MenuPlan {

    String name
    List<Week> weeks = []

    static hasMany = [weeks: Week]
    static belongsTo = [owner: Party]

    static constraints = {
    }

    static mapping = {
        weeks casade: 'all-delete-orphan'
    }

    String toString() {
        return name
    }
}

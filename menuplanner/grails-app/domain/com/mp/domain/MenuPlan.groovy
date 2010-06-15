package com.mp.domain

class MenuPlan {

    String name
    List<Week> weeks = []

    static hasMany = [weeks: Week]
    static belongsTo = [owner: User]

    static constraints = {
    }

    String toString() {
        return name
    }
}

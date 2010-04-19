package com.mp.domain

class MenuPlan {

    String name
    List<Week> weeks = []

    static hasMany = [weeks: Week]

    static constraints = {
    }
}

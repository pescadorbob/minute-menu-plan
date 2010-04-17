package com.mp.domain

class Week {

    String name

    static hasMany = [days: Day]

    static constraints = {
    }
}

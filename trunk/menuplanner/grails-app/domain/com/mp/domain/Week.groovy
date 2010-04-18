package com.mp.domain

class Week {

    String name

    List<Day> days = []
    static hasMany = [days: Day]

    static constraints = {
    }
}

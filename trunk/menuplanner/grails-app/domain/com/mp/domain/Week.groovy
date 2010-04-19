package com.mp.domain

class Week {

    String name
    List<Day> days = []

    static belongsTo = [menuPlan: MenuPlan]
    static hasMany = [days: Day]

    static constraints = {
    }
}

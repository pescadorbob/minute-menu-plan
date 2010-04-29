package com.mp.domain

class Week {

    List<Day> days = []

    static belongsTo = [menuPlan: MenuPlan]
    static hasMany = [days: Day]

    static constraints = {
    }
}

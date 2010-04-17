package com.mp.domain

class Day {

    String name
    List<Meal> meals = []

    static belongsTo = [week: Week]
    static hasMany = [meals: Meal]

    static constraints = {
    }
}

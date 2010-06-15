package com.mp.domain

class QuickFill {

    String name
    List<Meal> mealItems = []

    static hasMany = [mealItems: Meal]

    static constraints = {
        name(unique: true)
    }
}

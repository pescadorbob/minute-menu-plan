package com.mp.domain

class Meal {

    String name
    MealType type

    static belongsTo = [day: Day]
    static hasMany = [items: Item]

    static constraints = {
        type(unique: 'day')
    }
}

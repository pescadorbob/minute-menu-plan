package com.mp.domain

class Meal {

    MealType type
    List<Item> items = []

    static belongsTo = [day: Day]
    static hasMany = [items: Item]

    static constraints = {
        type(unique: 'day')
    }
}

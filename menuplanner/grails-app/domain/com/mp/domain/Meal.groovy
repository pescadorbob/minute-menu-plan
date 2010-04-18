package com.mp.domain

class Meal {

    String name
    MealType type
    List<Item> items = []

    static belongsTo = [day: Day]
    static hasMany = [items: Item]

    static constraints = {
        type(unique: 'day')
    }
}

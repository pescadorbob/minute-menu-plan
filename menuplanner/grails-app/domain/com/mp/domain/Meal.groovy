package com.mp.domain

class Meal {

    MealType type
    List<Item> items = []

    static belongsTo = [Day, QuickFill]
    static hasMany = [items: Item]

    static constraints = {
    }

}

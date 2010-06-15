package com.mp.domain

class Meal {

    MealType type
//    Day day
//    QuickFill quickFill
    List<Item> items = []

    static belongsTo = [Day, QuickFill]
    static hasMany = [items: Item]

    static constraints = {
//        type(unique: 'day')
//        day(nullable: true)
//        quickFill(nullable: true)
    }

}

package com.mp.domain

class WeeklyShoppingList {

    Week week
    List<Item> groceries = []

    static hasMany = [groceries: Item]
    static belongsTo = [shoppingList: ShoppingList]

    static constraints = {
    }
}

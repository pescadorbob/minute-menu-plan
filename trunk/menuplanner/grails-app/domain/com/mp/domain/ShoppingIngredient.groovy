package com.mp.domain

class ShoppingIngredient {

    Item item
    Quantity quantity

    static belongsTo = [weeklyShoppingList: WeeklyShoppingList]

    String toString() {
        return ("${quantity ? quantity : ''} ${item}")
    }

    static constraints = {
        item()
        quantity(nullable: true, blank: true)
    }

    static mapping = {
        cascade: 'all, delete-orphan'
    }
}

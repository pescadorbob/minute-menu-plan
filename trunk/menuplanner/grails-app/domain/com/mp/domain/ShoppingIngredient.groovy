package com.mp.domain

class ShoppingIngredient {

    String name
    Aisle aisle
    Item ingredient
    Quantity quantity

    String toString() {
        return name
    }

    static constraints = {
        aisle(nullable: true, blank: true)
        ingredient(nullable: true)
    }

}

package com.mp.domain

class ShoppingIngredient {

    String name
    Aisle aisle

    String toString() {
        return name
    }

    static constraints = {
        aisle(nullable: true, blank: true)
    }

}

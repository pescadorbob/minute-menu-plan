package com.mp.domain

class RecipeIngredient {

    Integer sequence
    Recipe recipe
    MeasuredProduct ingredient
    Quantity quantity

    String toString() {
        return (sequence + ' ' + ingredient + ' ' + quantity)
    }

    static constraints = {
        sequence(min:1, unique: 'recipe')
    }
}

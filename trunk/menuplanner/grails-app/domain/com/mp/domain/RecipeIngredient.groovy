package com.mp.domain

class RecipeIngredient {
    static searchable = true
  
    Integer sequence
    Recipe recipe
    MeasuredProduct ingredient
    Quantity quantity

    String toString() {
        return (sequence + ' ' + quantity + ' ' + ingredient)
    }

    static constraints = {
        sequence(min:1, unique: 'recipe')
    }
}

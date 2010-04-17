package com.mp.domain

class RecipeIngredient {
    static searchable = true
  
    MeasurableProduct ingredient
    Quantity quantity

    static belongsTo = [recipe: Recipe]

    String toString() {
        return ("${quantity} ${ingredient}")
    }

    static constraints = {
        ingredient(unique: 'recipe')
    }
}

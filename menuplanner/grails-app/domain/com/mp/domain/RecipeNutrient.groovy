package com.mp.domain

class RecipeNutrient {
    static searchable = true

    Recipe recipe
    Nutrient nutrient 
    Quantity quantity

    String toString(){
        return "${quantity} ${nutrient}"
    }
    static constraints = {
        recipe(unique: 'nutrient')
        quantity()
        nutrient(unique: 'recipe')
    }
}

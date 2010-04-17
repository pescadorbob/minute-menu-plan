package com.mp.domain

class RecipeNutrient {

    Nutrient nutrient
    Quantity quantity

    static belongsTo = [recipe: Recipe]

    String toString(){
        return "${quantity} ${nutrient}"
    }
    static constraints = {
        nutrient(unique: 'recipe')
    }
}

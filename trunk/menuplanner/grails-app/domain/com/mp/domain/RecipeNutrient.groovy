package com.mp.domain

class RecipeNutrient {
    Recipe recipe
    Nutrient nutrient 
    BigDecimal quantity

    String toString(){
        return "${quantity}: ${nutrient}"
    }
    static constraints = {
        recipe(unique: 'nutrient')
        quantity()
        nutrient(unique: 'recipe')
    }
}

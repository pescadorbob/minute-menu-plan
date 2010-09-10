package com.mp.domain

class RecipeIngredient {

    Item ingredient
    Quantity quantity
    Aisle aisle
    PreparationMethod preparationMethod

    static belongsTo = [recipe: Recipe]

    String toString() {
        return ("${quantity ? quantity : ''} ${ingredient}${preparationMethod ? ' ('+preparationMethod+')' : ''}")
    }

    static constraints = {
        ingredient(unique: 'recipe')
        quantity(nullable: true, blank: true)
        preparationMethod(nullable: true, blank: true)
        aisle(nullable: true, blank: true)
    }

    static mapping = {
        cascade: 'all, delete-orphan'
    }
}

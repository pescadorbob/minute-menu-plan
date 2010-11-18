package com.mp.domain

import org.apache.commons.lang.StringUtils

class RecipeIngredient {

    Item ingredient
    Quantity quantity
    Aisle aisle
    PreparationMethod preparationMethod

    static belongsTo = [recipe: Recipe]

    String toString() {
        return ("${quantity ? quantity : ''} ${StringUtils.capitaliseAllWords(ingredient.toString())}${preparationMethod ? ' ('+preparationMethod+')' : ''}")
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

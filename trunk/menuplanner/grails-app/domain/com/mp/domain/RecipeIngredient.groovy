package com.mp.domain

import org.apache.commons.lang.StringUtils
import com.mp.domain.ndb.NutritionLink
import com.mp.domain.ndb.NutritionLink
import com.mp.domain.ndb.NutritionLink

class RecipeIngredient {

    Item ingredient
    Quantity quantity
    Aisle aisle
    PreparationMethod preparationMethod
    NutritionLink foodMapping

    static belongsTo = [recipe: Recipe]

    String toString() {
        return ("${quantity ? quantity : ''} ${StringUtils.capitaliseAllWords(ingredient.toString())}${preparationMethod ? ' ('+preparationMethod+')' : ''}")
    }

    static constraints = {
        quantity(nullable: true, blank: true)
        preparationMethod(nullable: true, blank: true)
        aisle(nullable: true, blank: true)
        foodMapping(nullable: true)
    }

    static mapping = {
        cascade: 'all, delete-orphan'
    }
}

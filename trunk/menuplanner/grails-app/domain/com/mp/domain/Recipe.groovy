package com.mp.domain

class Recipe {

    String name

    static hasMany = [ingredients: RecipeIngredient, directions: RecipeDirection]

    String toString() {
        return name
    }

    static constraints = {
        name(unique: true)
    }

    static mapping = {
        ingredients sort:'sequence'
        directions sort:'sequence'
    }
}

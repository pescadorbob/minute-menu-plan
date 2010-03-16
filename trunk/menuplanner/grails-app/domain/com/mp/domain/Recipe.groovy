package com.mp.domain

class Recipe {

    String name
    byte[] image
    static hasMany = [ingredients: RecipeIngredient, directions: RecipeDirection]

    String toString() {
        return name
    }

    static constraints = {
        name(unique: true)
        image(maxSize: 6 * 1024 * 1024)
    }
}

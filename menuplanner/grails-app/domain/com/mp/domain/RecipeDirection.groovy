package com.mp.domain

class RecipeDirection {

    static searchable = true

    String step

    static belongsTo = [recipe: Recipe]

    String toString() {
        return (step)
    }

    static constraints = {
        step(maxSize: 5000)
    }
}

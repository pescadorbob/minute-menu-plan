package com.mp.domain

class RecipeDirection {

    static searchable = true

    Recipe recipe
    String step
    Integer sequence
    Image image

    String toString() {
        return (sequence + '. ' + step)
    }

    static constraints = {
        recipe()
        sequence(min:1)
        step(maxSize: 5000)
        image(nullable: true)       
    }
}

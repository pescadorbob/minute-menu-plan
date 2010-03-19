package com.mp.domain

class RecipeDirection {

    Recipe recipe
    String step
    Integer sequence

    String toString() {
        return (sequence + '. ' + step)
    }

    static constraints = {
        recipe()
        sequence(min:1, unique:'recipe')
        step()
    }
}

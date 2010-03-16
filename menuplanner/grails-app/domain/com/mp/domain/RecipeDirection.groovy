package com.mp.domain

class RecipeDirection {

    //TODO:Add image too
    Recipe recipe
    String step
    Integer sequence

    String toString() {
        return (sequence + '. ' + step)
    }

    static constraints = {
        sequence(unique:'recipe')
    }
}

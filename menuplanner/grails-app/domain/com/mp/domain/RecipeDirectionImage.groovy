package com.mp.domain

class RecipeDirectionImage {

    Long recipeDirectionId
    byte[] image
    static constraints = {
        image(maxSize: 6 * 1024 * 1024)
    }
}

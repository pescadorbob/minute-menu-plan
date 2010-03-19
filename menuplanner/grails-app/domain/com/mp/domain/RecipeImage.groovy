package com.mp.domain

class RecipeImage {

    Long recipeId
    byte[] image

    static constraints = {
        image(maxSize: 6 * 1024 * 1024)
    }
}

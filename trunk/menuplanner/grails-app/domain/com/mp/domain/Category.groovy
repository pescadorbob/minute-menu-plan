package com.mp.domain

class Category {

    String name
    static hasMany = [recipeCategories: RecipeCategory]

    static transients = ['recipes']

    List<Recipe> getRecipes() {
        return recipeCategories?.collect {it.recipe}
    }

    List addToRecipes(Recipe recipe) {
        RecipeCategory.link(recipe, this)
        return recipes
    }

    List removeFromRecipes(Recipe recipe) {
        RecipeCategory.unlink(recipe, this)
        return recipes
    }

    String toString() {
        return name
    }

    static constraints = {
        name(unique: true)
    }
}

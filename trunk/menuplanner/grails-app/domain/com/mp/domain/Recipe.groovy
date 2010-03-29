package com.mp.domain

class Recipe {

    String name
    RecipeDifficulty difficulty
    Boolean shareWithCommunity=false
    Image image
    Integer servings

    Time preparationTime
    Time cookingTime

    static transients = ['categories']

    static hasMany = [ingredients: RecipeIngredient, directions: RecipeDirection, recipeCategories: RecipeCategory, nutrients:RecipeNutrient]

    List<Category> getCategories() {
        return ((recipeCategories) ? ((recipeCategories?.collect {it.category}).sort{it.name}) : [])
    }

    def addToCategories(Category category) {
        RecipeCategory.link(this, category)
        return categories
    }

    List<Category> removeFromCategories(Category category) {
        RecipeCategory.unlink(this, category)
        return categories
    }

    String toString() {
        return name
    }

    static constraints = {
        name(unique: true)
        preparationTime()
        cookingTime()
        difficulty()
        servings()
        image(nullable: true)
    }
    static mapping = {
        ingredients sort: 'sequence'
        directions sort: 'sequence'
    }
}

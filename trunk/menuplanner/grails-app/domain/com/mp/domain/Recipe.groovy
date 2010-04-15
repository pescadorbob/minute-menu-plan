package com.mp.domain

class Recipe extends Item {

    static searchable = {
        cookingTime component: [prefix: 'cookingTime_']
        preparationTime component: [prefix: 'preparationTime_', maxDepth:10]
    }

    RecipeDifficulty difficulty
    Boolean shareWithCommunity = false
    Image image
    Integer servings

    Quantity preparationTime
    Quantity cookingTime
    Set<RecipeCategory> recipeCategories = []

    static transients = ['categories']

    static hasMany = [ingredients: RecipeIngredient, directions: RecipeDirection, recipeCategories: RecipeCategory, nutrients: RecipeNutrient, items: Item]

//    List<Category> getCategories() {
    def getCategories() {
        return ((recipeCategories) ? ((recipeCategories?.collect {it.category}).sort {it.name}) : [])
    }

    def getTotalTime() {
        Quantity sum = Quantity.add(cookingTime, preparationTime)
        return sum
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
        name(nullable: false)
        preparationTime()
        cookingTime()
        difficulty()
        servings()
        image(nullable: true)
    }
    static mapping = {
        tablePerHierarchy false
        ingredients sort: 'sequence'
        directions sort: 'sequence'
    }
}
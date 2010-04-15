package com.mp.domain

import org.apache.lucene.document.NumberTools

class Recipe extends Item {

    static searchable = true

    RecipeDifficulty difficulty
    Boolean shareWithCommunity = false
    Image image
    Integer servings

    Quantity preparationTime
    Quantity cookingTime
    Set<RecipeCategory> recipeCategories = []

    String cookingTimeValue
    String prepTimeValue
    String totalTimeValue
    String categoriesString

    String getCookingTimeValue(){
        Long time  = (cookingTime.value)?.toLong() 
        return NumberTools.longToString(time)
    }

    String getCategoriesString(){
        return (categories? categories*.name.join(", ") : '')
    }

    String getPrepTimeValue(){
        Long time  = (preparationTime.value)?.toLong()
        return NumberTools.longToString(time)
    }

    String getTotalTimeValue(){
        Long time  = (totalTime.value)?.toLong()
        return NumberTools.longToString(time)
    }

    static transients = ['categories', 'cookingTimeValue', 'totalTimeValue', 'prepTimeValue', 'categoriesString']
    static hasMany = [ingredients: RecipeIngredient, directions: RecipeDirection, recipeCategories: RecipeCategory, nutrients: RecipeNutrient, items: Item]

    def getCategories() {
        return ((recipeCategories) ? ((recipeCategories?.collect {it.category}).sort {it.name}) : [])
    }

    def getTotalTime() {
        Quantity sum = Quantity.addTime(cookingTime, preparationTime)
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
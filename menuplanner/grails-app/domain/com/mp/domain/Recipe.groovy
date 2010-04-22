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
    Set<RecipeCategory> recipeCategories = [] as Set
    List<String> directions = []
    List<RecipeIngredient> ingredients = []

    String cookingTimeValue
    String prepTimeValue
    String totalTimeValue
    String categoriesString

    static transients = ['categories', 'cookingTimeValue', 'totalTimeValue', 'prepTimeValue', 'categoriesString']
    static hasMany = [ingredients: RecipeIngredient, directions: String, recipeCategories: RecipeCategory, nutrients: RecipeNutrient, items: Item]

    String getCategoriesString() {
        return (categories ? categories*.name.join(", ") : '')
    }

    String getCookingTimeValue() {
        if (cookingTime) {
            Long time = (cookingTime.value)?.toLong()
            return NumberTools.longToString(time)
        } else {
            return null
        }
    }

    String getPrepTimeValue() {
        if (preparationTime) {
            Long time = (preparationTime?.value)?.toLong()
            return NumberTools.longToString(time)
        } else {
            return null
        }
    }

    String getTotalTimeValue() {
        if (totalTime) {
            Long time = (totalTime?.value)?.toLong()
            return NumberTools.longToString(time)
        } else {
            return null
        }
    }

    def getCategories() {
        return ((recipeCategories) ? ((recipeCategories?.collect {it.category}).sort {it.name}) : [])
    }

    def getTotalTime() {
        if(cookingTime && preparationTime){
        Quantity sum = Quantity.addTime(cookingTime, preparationTime)
        return sum
        } else if(cookingTime){
            return cookingTime
        } else {
            return preparationTime
        }
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
        name(nullabe:false, blank:false)
        preparationTime(nullable: true, blank: true)
        cookingTime(nullable: true, blank: true)
        difficulty(nullable: true, blank: true)
        servings(nullable: true, blank: true)
        image(nullable: true, blank: true)
    }
    static mapping = {
        tablePerHierarchy false
    }
}
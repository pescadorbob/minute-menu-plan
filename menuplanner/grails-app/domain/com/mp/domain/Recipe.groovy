package com.mp.domain

import org.apache.lucene.document.NumberTools
import static com.mp.MenuConstants.*

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
    String caloriesString

    public void validateTimings(){
        if(!preparationTime){
             preparationTime = new Quantity(value: 1, unit: Unit.findByName(TIME_UNIT_MINUTES), savedUnit: Unit.findByName(TIME_UNIT_MINUTES)).s()
        }
        if(!cookingTime){
             cookingTime = new Quantity(value: 0, unit: Unit.findByName(TIME_UNIT_MINUTES), savedUnit: Unit.findByName(TIME_UNIT_MINUTES)).s()
        }
    }

    def beforeInsert = {
         validateTimings()
    }

    def beforeUpdate = {
        validateTimings()
    }

    static transients = ['categories', 'cookingTimeValue', 'totalTimeValue', 'prepTimeValue', 'caloriesString', 'categoriesString']
    static hasMany = [ingredients: RecipeIngredient, directions: String, recipeCategories: RecipeCategory, nutrients: RecipeNutrient, items: Item]

    String getCategoriesString() {
        return (categories ? categories*.name.join(", ") : '')
    }

    String getCaloriesString() {
       RecipeNutrient calories = nutrients?.find{it.nutrient.name == NUTRIENT_CALORIES}
        return (calories?.quantity?.value) ? NumberTools.longToString(calories?.quantity?.value?.toLong()) : null
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
            Float time = totalTime?.value?.toFloat()
            return time.toString()
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
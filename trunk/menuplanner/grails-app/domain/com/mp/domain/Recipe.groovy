package com.mp.domain

import org.apache.lucene.document.NumberTools
import static com.mp.MenuConstants.*
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.grails.comments.*
import org.grails.rateable.*


class Recipe extends Item implements Commentable, Rateable {

    static searchable = {
        ingredients component: true
    }
    static config = ConfigurationHolder.config

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
    String favouriteForUsersString
    Date dateCreated


    String getImageDir() {
        return (config.imagesRootDir + config.recipesRootDir + this?.id + '/')
    }

    void deleteImage() {
        Image image = this?.image
        this.image = null
        image?.delete(flush: true)
    }

    String getFavouriteForUsersString() {
        List<User> users = favouriteForUsers
        String s = (users ? users.collect {it.id.toString()}.join(", ") : '')
        return s
    }

    List<User> getFavouriteForUsers() {
        List<User> users = []
        if (!config.bootstrapMode) {
            users = User.createCriteria().list {
                favourites {
                    eq('id', this.id)
                }
            }
        }
        return users
    }

    def getContributor() {
        return User.createCriteria().get {
            contributions {
                eq('id', this.id)
            }
        }
    }

    public void validateTimings() {
        if (!preparationTime) {
            preparationTime = new Quantity(value: (config.bootstrapMode ? 10 : 1), unit: Unit.findByName(TIME_UNIT_MINUTES), savedUnit: Unit.findByName(TIME_UNIT_MINUTES)).s()
        }
        if (!cookingTime) {
            cookingTime = new Quantity(value: (config.bootstrapMode ? 10 : 0), unit: Unit.findByName(TIME_UNIT_MINUTES), savedUnit: Unit.findByName(TIME_UNIT_MINUTES)).s()
        }
    }

    def beforeInsert = {
        validateTimings()
        if (!servings) {servings = 1}
    }

    def beforeUpdate = {
        validateTimings()
        if (!servings) {servings = 1}
    }

    static transients = ['categories', 'favouriteForUsers', 'favouriteForUsersString', 'cookingTimeValue', 'totalTimeValue', 'prepTimeValue', 'caloriesString', 'categoriesString', 'contributor', 'imageDir']
    static hasMany = [ingredients: RecipeIngredient, directions: String, recipeCategories: RecipeCategory, nutrients: RecipeNutrient, items: Item]

    String getCategoriesString() {
        return (categories ? categories*.name.join(", ") : '')
    }

    String getCaloriesString() {
        RecipeNutrient calories = nutrients?.find {it.nutrient.name == NUTRIENT_CALORIES}
        Quantity cal = calories?.quantity
        return (cal?.value) ? NumberTools.longToString(cal?.value?.toLong()) : NumberTools.longToString(0L)
    }

    String getCookingTimeValue() {
        Quantity cTime = cookingTime
        Long time = (cTime?.value) ? (cTime?.value)?.toLong() : 0L
        return NumberTools.longToString(time)
    }

    String getPrepTimeValue() {
        Quantity pTime = preparationTime
        Long time = (pTime?.value) ? (pTime?.value)?.toLong() : 0L
        return NumberTools.longToString(time)
    }

    String getTotalTimeValue() {
        Quantity tTime = totalTime
        Long time = (tTime?.value) ? (tTime?.value)?.toLong() : 0L
        return NumberTools.longToString(time)
    }

    def getCategories() {
        return ((recipeCategories) ? ((recipeCategories?.collect {it.category}).sort {it.name}) : [])
    }

    def getTotalTime() {
        Quantity cTime = cookingTime
        Quantity pTime = preparationTime
        if (cTime && pTime) {
            Quantity sum = Quantity.addTime(cTime, pTime)
            return sum
        } else {
            return (cTime ? cTime : pTime)
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
        name(nullabe: false, blank: false)
        preparationTime(nullable: true, blank: true)
        cookingTime(nullable: true, blank: true)
        servings(nullable: true, blank: true)
        difficulty(nullable: true, blank: true)
        image(nullable: true, blank: true)
    }

    static mapping = {
        tablePerHierarchy false
    }

    boolean equals(final Object o) {
        if (this.is(o)) return true;
        if (o == null) return false;
        if (!getClass().isAssignableFrom(o.getClass())) return false;
        final Recipe other = Recipe.class.cast(o);
        return id == null ? false : id.equals(other.id);
    }

}
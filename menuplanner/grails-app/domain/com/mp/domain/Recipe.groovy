package com.mp.domain

import org.apache.lucene.document.NumberTools
import static com.mp.MenuConstants.*
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.grails.comments.*
import org.grails.rateable.*
import com.mp.domain.party.Party


class Recipe extends Item implements Commentable, Rateable {

    static searchable = true

    RecipeDifficulty difficulty
    Image image
    Integer servings

    Quantity preparationTime
    Quantity cookingTime
    List<String> directions = []
    List<RecipeIngredient> ingredients = []
    Set<Item> items = []
    Set<SubCategory> subCategories = []

    String description
    String cookingTimeValue
    String prepTimeValue
    String totalTimeValue
    String subCategoriesString
    String caloriesString
    String favouriteForUsersString
    Date dateCreated

    String getImageDir() {
        return (ConfigurationHolder.config.recipesRootDir + this?.id + '/')
    }

    void deleteImage() {
        Image image = this?.image
        this.image = null
        image?.delete(flush: true)
    }

    String getIngredientsString() {
        return (ingredients ? ingredients.collect {it.ingredient.name.toString()}.join(",") : '')
    }

    String getAislesString() {
        String aisleString = ''
        if (ingredients) {
            List<String> aisleList = []
            ingredients.each {RecipeIngredient ingredient ->
                if (ingredient?.aisle) {
                    aisleList.add(ingredient?.aisle?.toString())
                }
            }
            aisleString = aisleList.join(',')
        }
        return aisleString
        }

    String getPreparationMethodString() {
        String preparationString = ''
        if (ingredients) {
            List<String> pList = []
            ingredients.each {RecipeIngredient ingredient ->
                if (ingredient?.preparationMethod) {
                    pList.add(ingredient?.preparationMethod?.toString())
                }
            }
            preparationString = pList.join(',')
        }
        return preparationString
    }

    String getFavouriteForUsersString() {
        List<Party> users = favouriteForUsers
        String s = (users ? users.collect {it.id.toString()}.join(",") : '')
        return s
    }

    List<Party> getFavouriteForUsers() {
        List<Party> users = []
        if (!ConfigurationHolder.config.bootstrapMode) {
            users = Party.createCriteria().list {
                favourites {
                    eq('id', this.id)
                }
            }
        }
        return users
    }

    String getContributorsString() {
        String searchString = ''
        if (!ConfigurationHolder.config.bootstrapMode) {
            Party p = contributor
            searchString = (p) ? NumberTools.longToString(p?.id) : NumberTools.longToString(0L)
        }
        return searchString
    }

    def getContributor() {
        return Party.createCriteria().get {
            contributions {
                eq('id', this.id)
            }
        }
    }

    public void validateTimings() {
        if (!preparationTime) {
            preparationTime = new Quantity(value: (ConfigurationHolder.config.bootstrapMode ? 10 : 1),
                    unit: Unit.findByName(TIME_UNIT_MINUTES), savedUnit: Unit.findByName(TIME_UNIT_MINUTES)).s()
        }
        if (!cookingTime) {
            cookingTime = new Quantity(value: (ConfigurationHolder.config.bootstrapMode ? 10 : 0), unit: Unit.findByName(TIME_UNIT_MINUTES),
                    savedUnit: Unit.findByName(TIME_UNIT_MINUTES)).s()
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

    static transients = ['preparationMethodString', 'aislesString', 'serveWithString', 'ingredientsString', 'contributorsString', 'favouriteForUsers', 'favouriteForUsersString', 'cookingTimeValue', 'totalTimeValue', 'prepTimeValue', 'caloriesString', 'subCategoriesString', 'contributor', 'imageDir']
    static hasMany = [ingredients: RecipeIngredient, directions: String, nutrients: RecipeNutrient, items: Item, subCategories: SubCategory]

    String getSubCategoriesString() {
        return (subCategories ? subCategories.collect {it.name}.join(", ") : '')
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

    String getServeWithString() {
        return (items ? items.collect {it.name.toString()}.join(",") : '')
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

    String toString() {
        return name
    }

    static constraints = {
        name(nullabe: false, blank: false)
        preparationTime(nullable: true, blank: true)
        cookingTime(nullable: true, blank: true)
        servings(nullable: true, blank: true)
        difficulty(nullable: true, blank: true)
        description(nullable: true, blank: true, maxSize: 5000)
        image(nullable: true, blank: true)
    }

    static mapping = {
        tablePerHierarchy false
        directions cascade: 'all-delete-orphan'
        ingredients cascade: 'all-delete-orphan'
        sort 'name'
    }

    boolean equals(final Object o) {
        if (this.is(o)) return true;
        if (o == null) return false;
        if (!getClass().isAssignableFrom(o.getClass())) return false;
        final Recipe other = Recipe.class.cast(o);
        return id == null ? false : id.equals(other.id);
    }

}
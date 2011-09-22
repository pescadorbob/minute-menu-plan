package com.mp.domain

import com.mp.domain.ndb.ItemNutritionLink

class Item {
    static searchable = true

    Float density = 1.0f
    String name
    Aisle suggestedAisle
    Boolean shareWithCommunity = false
    Boolean isAlcoholic = false
    static hasMany = [links:ItemNutritionLink,recipeIngredients:RecipeIngredient]

    String toString() {
        return name
    }

    static constraints = {
        suggestedAisle(nullable: true)
    }

    static mapping = {
        tablePerHierarchy false
        sort 'name'
        density(sqlType: 'numeric(15, 10)')
    }

    boolean equals(final Object o) {
        if (this.is(o)) return true;
        if (o == null) return false;
        if (!getClass().isAssignableFrom(o.getClass())) return false;
        final Item other = Item.class.cast(o);
        return id == null ? false : id.equals(other.id);
    }


}

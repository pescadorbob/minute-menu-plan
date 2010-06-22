package com.mp.domain

class WeeklyShoppingList {

    Integer weekIndex
    List<ShoppingIngredient> products = []
    List<ShoppingIngredient> groceries = []

    static hasMany = [products: ShoppingIngredient, groceries: ShoppingIngredient]
    static belongsTo = [shoppingList: ShoppingList]

    static constraints = {
    }
}

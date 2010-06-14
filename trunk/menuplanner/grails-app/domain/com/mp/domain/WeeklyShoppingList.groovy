package com.mp.domain

class WeeklyShoppingList {

    Integer weekIndex
    List<ShoppingIngredient> products = []
    List<Item> groceries = []

    static hasMany = [products: ShoppingIngredient, groceries: Item]
    static belongsTo = [shoppingList: ShoppingList]

    static constraints = {
        products(nullable:true, blank:true)
        groceries(nullable:true, blank:true)
    }
}

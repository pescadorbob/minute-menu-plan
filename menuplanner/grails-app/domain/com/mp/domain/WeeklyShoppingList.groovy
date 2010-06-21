package com.mp.domain

class WeeklyShoppingList {

    Integer weekIndex
    List<String> products = []
    List<String> groceries = []

    static hasMany = [products: String, groceries: String]
    static belongsTo = [shoppingList: ShoppingList]

    static constraints = {
        products(nullable: true, blank: true)
        groceries(nullable: true, blank: true)
    }
}

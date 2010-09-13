package com.mp.domain

class ShoppingList {

    String name
    Integer servings
    Party party
    MenuPlan menuPlan

    List<WeeklyShoppingList> weeklyShoppingLists = []

    static hasMany = [weeklyShoppingLists: WeeklyShoppingList]
    static belongsTo = [party: Party]

    static mapping = {
        weeklyShoppingLists casade: 'all-delete-orphan'
    }

    static constraints = {
        menuPlan(nullable: true)
    }
}

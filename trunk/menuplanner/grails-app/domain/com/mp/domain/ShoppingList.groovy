package com.mp.domain

class ShoppingList {

    String name
    Integer servings
    Party party
    MenuPlan menuPlan
    Boolean isWeeklyShoppingList = false

    List<WeeklyShoppingList> weeklyShoppingLists = []

    static hasMany = [weeklyShoppingLists: WeeklyShoppingList]
    static belongsTo = [party: Party]

    static mapping = {
        weeklyShoppingLists casade: 'all-delete-orphan', fetch: 'join'
    }

    static constraints = {
        menuPlan(nullable: true)
    }
}

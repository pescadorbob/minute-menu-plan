package com.mp.domain

import com.mp.domain.party.Party

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
        weeklyShoppingLists cascade: 'all-delete-orphan', fetch: 'join'
        sort 'name'
    }

    static constraints = {
        menuPlan(nullable: true)
    }
}

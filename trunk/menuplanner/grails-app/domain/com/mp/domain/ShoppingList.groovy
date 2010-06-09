package com.mp.domain

class ShoppingList {

    Integer servings
    User user 
    MenuPlan menuPlan

    List<WeeklyShoppingList> weeklyShoppingLists = []

    static hasMany = [weeklyShoppingLists: WeeklyShoppingList]
    

    static constraints = {
    }
}

package com.mp.domain

class ShoppingList {

    String name
    Integer servings
    User user 
    MenuPlan menuPlan

    List<WeeklyShoppingList> weeklyShoppingLists = []

    static hasMany = [weeklyShoppingLists: WeeklyShoppingList]
    
    static constraints = {
    }
}

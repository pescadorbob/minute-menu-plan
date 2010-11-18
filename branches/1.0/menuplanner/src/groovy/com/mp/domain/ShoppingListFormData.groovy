package com.mp.domain

class ShoppingListFormData {
    String name
    String menuPlan
    String servings

    public static ShoppingListFormData getDefaultShoppingListFormData() {
        ShoppingListFormData listFormData = new ShoppingListFormData()
        listFormData.name = "Test Shopping List"
        listFormData.menuPlan = "superAdmin's MenuPlan-1"
        listFormData.servings = "4"
        return listFormData
    }
}

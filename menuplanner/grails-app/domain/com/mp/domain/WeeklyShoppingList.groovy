package com.mp.domain

class WeeklyShoppingList {

    Integer weekIndex
    List<ShoppingIngredient> products = []
    List<ShoppingIngredient> groceries = []

    static transients = ['productsByAisle', 'groceriesByAisle']
    static hasMany = [products: ShoppingIngredient, groceries: ShoppingIngredient]
    static belongsTo = [shoppingList: ShoppingList]

    List<Aisle> getAisles(){
        List<Aisle> aisles = []
        products?.each{
            if(it.aisle){
                aisles.add(it.aisle)
            }
        }
        groceries?.each{
            if(it.aisle){
                aisles.add(it.aisle)
            }
        }
        return (aisles.unique{it?.id} as List)
    }
    List<ShoppingIngredient> getProductsByAisle(Aisle aisle = null){
        List<ShoppingIngredient> ingredients = products.findAll{it.aisle == aisle}
        return (ingredients ? ingredients : [])
    }

    List<ShoppingIngredient> getGroceriesByAisle(Aisle aisle = null){
        List<ShoppingIngredient> ingredients = groceries.findAll{it.aisle == aisle}
        return (ingredients ? ingredients : [])
    }

    static constraints = {
    }
}

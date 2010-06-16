package com.mp.domain

class ShoppingListService {

    boolean transactional = true

    Map<String, Quantity> getProductListForWeek(MenuPlan menuPlan, String weekIndex) {
        Map<String, Quantity> productListForWeek = [:]
        menuPlan?.weeks[weekIndex?.toInteger()]?.days?.each {Day day ->
            List<Item> items = day?.breakfast + day?.lunch + day?.dinner as List
            items.each {Item item ->
                String prodName
                Quantity quantity
                if (item?.instanceOf(Recipe)) {
                    item?.ingredients?.each {RecipeIngredient recipeIngredient ->
                        prodName = recipeIngredient?.ingredient
                        quantity = recipeIngredient?.quantity
                        productListForWeek[prodName] = (productListForWeek[prodName] && (quantity)) ? (Quantity.add(productListForWeek[prodName], quantity)) : quantity
                    }
                }
            }
        }
        return productListForWeek
    }

    List<String> getGroceryListForWeek(MenuPlan menuPlan, String weekIndex) {
        List<String> groceryListForWeek = []
        menuPlan?.weeks[weekIndex?.toInteger()]?.days?.each {Day day ->
            List<Item> items = day?.breakfast + day?.lunch + day?.dinner as List
            items.each {Item item ->
                String prodName
                if (!(item?.instanceOf(Recipe))) {
                    prodName = item?.name
                    groceryListForWeek.add(prodName)
                }
            }
        }
        return (groceryListForWeek as Set) as List
    }

    Map<String, Quantity> getProductListForWeekFromShoppingList(WeeklyShoppingList weeklyShoppingList) {
           Map<String, Quantity> productListForWeek = [:]
           weeklyShoppingList.products.each {ShoppingIngredient shoppingIngredient ->
               String prodName
               Quantity quantity
               prodName = shoppingIngredient?.item?.name
               quantity = shoppingIngredient?.quantity
               productListForWeek[prodName] = quantity
           }

           return productListForWeek
       }

       List<String> getGroceryListForWeekFromShoppingList(WeeklyShoppingList weeklyShoppingList) {
           List<String> groceryListForWeek = []
           weeklyShoppingList.groceries.each {ShoppingIngredient shoppingIngredient ->
               String prodName
               Quantity quantity
               prodName = shoppingIngredient?.item?.name
               groceryListForWeek.add(prodName)
           }
           return (groceryListForWeek as Set) as List
       }

}

class PrintShoppingListCO {
    String name
    String menuPlanId
    String weeks
    String servings

    static constraints = {
        name(blank: false, nullable: false)
        weeks(nullable: false, blank: false)
        menuPlanId(nullable: false, blank: false)
        servings(blank: false, matches: /[0-9\s]*/)
    }
}
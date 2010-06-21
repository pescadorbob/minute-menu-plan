package com.mp.domain

class ShoppingListService {

    boolean transactional = true

    List<String> getProductListForWeekFromMenuPlan(MenuPlan menuPlan, String weekIndex) {
        List<String> productListForWeek = []

        menuPlan?.weeks[weekIndex?.toInteger()]?.days?.each {Day day ->
            List<Item> items = day?.breakfast + day?.lunch + day?.dinner as List
            items.each {Item item ->
                String prodName
                String quantity
                if (item?.instanceOf(Recipe)) {
                    item?.ingredients?.each {RecipeIngredient recipeIngredient ->
                        prodName = recipeIngredient?.ingredient
                        quantity = recipeIngredient?.quantity?.toString()
//                        productListForWeek.add((productListForWeek[prodName] && (quantity)) ? (Quantity.add(productListForWeek[prodName], quantity)).toString() : quantity.toString())
                        productListForWeek.add(quantity + '  ' + prodName)
                    }
                }
            }
        }
        return productListForWeek
    }

    List<String> getGroceryListForWeekFromMenuPlan(MenuPlan menuPlan, String weekIndex) {
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

    List<String> getProductListForWeekFromShoppingList(WeeklyShoppingList weeklyShoppingList) {
        List<String> productListForWeek = []
        weeklyShoppingList.products.each {String name ->
            productListForWeek.add(name)
        }
        return productListForWeek
    }

    List<String> getGroceryListForWeekFromShoppingList(WeeklyShoppingList weeklyShoppingList) {
        List<String> groceryListForWeek = []
        weeklyShoppingList.groceries.each {String name ->
            groceryListForWeek.add(name)
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
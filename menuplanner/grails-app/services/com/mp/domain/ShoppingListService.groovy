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
                        productListForWeek[prodName] = (productListForWeek[prodName] && (quantity)) ? productListForWeek[prodName] + quantity : quantity
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
                Quantity quantity
                if (!(item?.instanceOf(Recipe))) {
                    prodName = item?.name
                    groceryListForWeek.add(prodName)
                }
            }
        }
        return groceryListForWeek.unique() as List
    }
}

class PrintShoppingListCO {
    String name
    String menuPlanId
    String weeks
    String servings

    static constraints = {
        name(blank: false, matches: /[a-zA-Z0-9\s\&]*/)
        weeks(nullable: false, blank: false)
        menuPlanId(nullable: false, blank: false)
        servings(blank: false, matches: /[0-9\s]*/)
    }
}
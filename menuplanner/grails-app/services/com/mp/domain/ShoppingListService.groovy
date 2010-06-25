package com.mp.domain

class ShoppingListService {

    boolean transactional = true

    ShoppingList createShoppingList(PrintShoppingListCO shoppingListCO) {
        MenuPlan menuPlan = MenuPlan.get(shoppingListCO.menuPlanId?.toLong())
        ShoppingList shoppingList = new ShoppingList()
        shoppingList.name = shoppingListCO.name
        shoppingList.menuPlan = menuPlan
        shoppingList.servings = shoppingListCO.servings.toInteger()
        shoppingList.user = User.currentUser

        shoppingListCO.weeks.each{String weekIndex ->
            WeeklyShoppingList weeklyShoppingList = createWeeklyShoppingList(shoppingList, menuPlan, weekIndex)
            shoppingList.addToWeeklyShoppingLists(weeklyShoppingList)
        }
        return shoppingList
    }

    WeeklyShoppingList createWeeklyShoppingList(ShoppingList shoppingList, MenuPlan menuPlan, String index){
        WeeklyShoppingList weeklyShoppingList = new WeeklyShoppingList()
        weeklyShoppingList.weekIndex = index.toInteger()
        weeklyShoppingList.products = getProductListForWeekFromMenuPlan(menuPlan, index)
        weeklyShoppingList.groceries = getGroceryListForWeekFromMenuPlan(menuPlan, index)
        return weeklyShoppingList
    }

    List<ShoppingIngredient> getProductListForWeekFromMenuPlan(MenuPlan menuPlan, String weekIndex) {
        List<ShoppingIngredient> productListForWeek = []

        menuPlan?.weeks[weekIndex?.toInteger()]?.days?.each {Day day ->
            List<Item> items = (day?.breakfast + day?.lunch + day?.dinner) as List
            items.each {Item item ->
                String prodName
                String quantity
                String name
                if (item?.instanceOf(Recipe.class)) {
                    item?.ingredients?.each {RecipeIngredient recipeIngredient ->
                        prodName = recipeIngredient?.ingredient
                        quantity = recipeIngredient?.quantity?.toString()
                        name = quantity + '  ' + prodName
                        ShoppingIngredient shoppingIngredient = new ShoppingIngredient()
                        shoppingIngredient.name = name
                        shoppingIngredient.aisle = recipeIngredient?.aisle
                        productListForWeek.add(shoppingIngredient)
                    }
                }
            }
        }
        return productListForWeek
    }

    List<ShoppingIngredient> getGroceryListForWeekFromMenuPlan(MenuPlan menuPlan, String weekIndex) {
        List<ShoppingIngredient> groceryListForWeek = []
        menuPlan?.weeks[weekIndex?.toInteger()]?.days?.each {Day day ->
            List<Item> items = (day?.breakfast + day?.lunch + day?.dinner) as List
            items.each {Item item ->
                if (!(item?.instanceOf(Recipe.class))) {
                    ShoppingIngredient shoppingIngredient = new ShoppingIngredient()
                    shoppingIngredient.name = item?.name
                    shoppingIngredient.aisle = item?.suggestedAisle
                    groceryListForWeek.add(shoppingIngredient)
                }
            }
        }
        return (groceryListForWeek ? (groceryListForWeek?.unique {it.name} as List) : [])
    }

//    List<ShoppingIngredient> getProductListForWeekFromShoppingList(WeeklyShoppingList weeklyShoppingList) {
//        List<ShoppingIngredient> productListForWeek = []
//        weeklyShoppingList.products.each {ShoppingIngredient ingredient ->
//            productListForWeek.add(ingredient)
//        }
//        return productListForWeek
//    }
//
//    List<String> getGroceryListForWeekFromShoppingList(WeeklyShoppingList weeklyShoppingList) {
//        List<String> groceryListForWeek = []
//        weeklyShoppingList.groceries.each {String name ->
//            groceryListForWeek.add(name)
//        }
//        return (groceryListForWeek as Set) as List
//    }
}


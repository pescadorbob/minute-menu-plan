package com.mp.domain

import org.apache.commons.math.fraction.Fraction

class ShoppingListService {

    boolean transactional = true

    ShoppingList createShoppingList(PrintShoppingListCO shoppingListCO) {
        MenuPlan menuPlan = MenuPlan.get(shoppingListCO.menuPlanId?.toLong())
        ShoppingList shoppingList = new ShoppingList()
        shoppingList.name = shoppingListCO.name
        shoppingList.menuPlan = menuPlan
        shoppingList.servings = shoppingListCO.servings.toInteger()
        shoppingList.user = User.currentUser
        shoppingListCO.weeks.each {String weekIndex ->
            WeeklyShoppingList weeklyShoppingList = createWeeklyShoppingList(shoppingList, menuPlan, weekIndex)
            shoppingList.addToWeeklyShoppingLists(weeklyShoppingList)
        }
        return shoppingList
    }

    ShoppingList modifyShoppingList(PrintShoppingListCO shoppingListCO, ShoppingList shoppingListOld) {
        MenuPlan menuPlan = MenuPlan.get(shoppingListCO.menuPlanId?.toLong())
        ShoppingList shoppingList = new ShoppingList()
        shoppingList.name = shoppingListCO.name
        shoppingList.menuPlan = menuPlan
        shoppingList.servings = shoppingListCO.servings.toInteger()
        shoppingList.user = User.currentUser
        shoppingListCO.weeks.each {String weekIndex ->
            if (weekIndex in shoppingListOld?.weeklyShoppingLists?.weekIndex*.toString()) {
                shoppingList.addToWeeklyShoppingLists(shoppingListOld?.weeklyShoppingLists?.find {it?.weekIndex == weekIndex.toInteger()})
            } else {
                WeeklyShoppingList weeklyShoppingList = createWeeklyShoppingList(shoppingList, menuPlan, weekIndex)
                shoppingList.addToWeeklyShoppingLists(weeklyShoppingList)
            }
        }
        return shoppingList
    }

    WeeklyShoppingList createWeeklyShoppingList(ShoppingList shoppingList, MenuPlan menuPlan, String index) {
        WeeklyShoppingList weeklyShoppingList = new WeeklyShoppingList()
        weeklyShoppingList.weekIndex = index.toInteger()
        weeklyShoppingList.products = getProductListForWeekFromMenuPlan(menuPlan, index)
        weeklyShoppingList.groceries = getGroceryListForWeekFromMenuPlan(menuPlan, index)
        return weeklyShoppingList
    }

    List<ShoppingIngredient> getProductListForWeekFromMenuPlan(MenuPlan menuPlan, String weekIndex) {
        List<ShoppingIngredient> productListForWeek = []
        List<RecipeIngredient> weeklyRecipeIngredients = []
        Week week = menuPlan?.weeks[weekIndex?.toInteger()]

        week?.days?.each {Day day ->
            day.meals.each {Meal meal ->
                meal.items.each {Item item ->
                    if (item?.instanceOf(Recipe.class)) {
                        weeklyRecipeIngredients += getRecipeIngredientsList(item)
                    }
                }
            }
        }
        Aisle otherAisle = new Aisle(name: 'Others')
        Map<Aisle, List<RecipeIngredient>> ingredientsGroupByAisles = weeklyRecipeIngredients.groupBy {return (it.aisle ? it.aisle : otherAisle)}
        Set<Aisle> aisles = ingredientsGroupByAisles.keySet()

        aisles.each {Aisle aisle ->
            List<RecipeIngredient> ingredientsByAisle = ingredientsGroupByAisles[aisle]
            Map ingredientsGroupByName = ingredientsByAisle.groupBy {it.ingredient}
            Set differentIngredients = ingredientsGroupByName.keySet()

            differentIngredients.each {Item differentIngredient ->
                List similarIngredients = ingredientsGroupByName[differentIngredient]
                Quantity total = null
                similarIngredients.each {RecipeIngredient similarIngredient ->
                    total = (total == null) ? similarIngredient.quantity : Quantity.add(total, similarIngredient.quantity)
                }
                ShoppingIngredient shoppingIngredient = new ShoppingIngredient(name: "${(total)? total.toBiggestUnitString() + ' ': ''}" + differentIngredient.name, aisle: (aisle?.id) ? aisle : null)
                productListForWeek.add(shoppingIngredient)
            }
        }

        return productListForWeek
    }

    List<RecipeIngredient> getRecipeIngredientsList(def recipe) {
        List<RecipeIngredient> ingredients = []
        if (recipe?.instanceOf(Recipe.class)) {
            recipe.ingredients.each {RecipeIngredient recipeIngredient ->
                if (recipeIngredient.ingredient?.instanceOf(Recipe.class)) {
                    List<RecipeIngredient> subRecipeIngredients = getRecipeIngredientsList(recipeIngredient.ingredient as Recipe)
                    subRecipeIngredients.each {RecipeIngredient subRecipeIngredient ->
                        if (subRecipeIngredient.quantity && recipeIngredient.quantity) {
                            RecipeIngredient newSubRecipeIngredient = new RecipeIngredient()
                            newSubRecipeIngredient.with {
                                ingredient = subRecipeIngredient.ingredient
                                quantity = Quantity.multiply(subRecipeIngredient.quantity, recipeIngredient.quantity)
                                aisle = subRecipeIngredient.aisle
                                preparationMethod = subRecipeIngredient.preparationMethod
                            }
                            if (newSubRecipeIngredient.quantity) {ingredients.add(newSubRecipeIngredient)}
                        } else {
                            if (subRecipeIngredient.quantity) {ingredients.add(subRecipeIngredient)}
                        }
                    }
                } else {
                    if (recipeIngredient.quantity) {ingredients.add(recipeIngredient)}
                }
            }
        }
        return ingredients
    }

    List<ShoppingIngredient> getGroceryListForWeekFromMenuPlan(MenuPlan menuPlan, String weekIndex) {
        List<ShoppingIngredient> groceryListForWeek = []
        List<Product> weeklyGroceryItems = []
        Week week = menuPlan?.weeks[weekIndex?.toInteger()]
        week?.days?.each {Day day ->
            day.meals.each {Meal meal ->
                meal.items.each {Item item ->
                    weeklyGroceryItems += getProductsList(item)
                }
            }
        }

        weeklyGroceryItems = weeklyGroceryItems.unique()

        weeklyGroceryItems.each {def product ->
            ShoppingIngredient shoppingIngredient = new ShoppingIngredient(name: product.name, aisle: product.suggestedAisle)
            groceryListForWeek.add(shoppingIngredient)
        }


        return groceryListForWeek
    }

    List<Product> getProductsList(def recipe) {
        List<Product> products = []
        if (recipe?.instanceOf(Recipe.class)) {
            recipe.ingredients.each {RecipeIngredient recipeIngredient ->
                if (recipeIngredient.ingredient?.instanceOf(Recipe.class)) {
                    List<Product> subProducts = getProductsList(recipeIngredient.ingredient as Recipe)
                    products += subProducts
                } else {
                    if (!recipeIngredient.quantity) {
                        products.add(recipeIngredient.ingredient as Product)
                    }
                }
            }
        } else {
            products.add(recipe)
        }
        return products
    }

}


package com.mp.domain

class ShoppingListService {

    boolean transactional = true

    ShoppingList createShoppingList(PrintShoppingListCO shoppingListCO, Boolean isWeeklyList) {
        MenuPlan menuPlan = MenuPlan.get(shoppingListCO.menuPlanId?.toLong())
        Boolean isScaled = shoppingListCO.areServingsRequired
        ShoppingList shoppingList = new ShoppingList()
        shoppingList.name = shoppingListCO?.name
        shoppingList.isWeeklyShoppingList = isWeeklyList
        shoppingList.menuPlan = menuPlan
        shoppingList.servings = (isScaled && shoppingListCO?.servings) ? shoppingListCO?.servings?.toInteger() : 1
        shoppingList.party = LoginCredential.currentUser?.party
        if (isWeeklyList) {
            shoppingListCO.weeks.each {String weekIndex ->
                WeeklyShoppingList weeklyShoppingList = createWeeklyShoppingList(shoppingList, menuPlan, weekIndex, isScaled)
                shoppingList.addToWeeklyShoppingLists(weeklyShoppingList)
            }
        } else {
            WeeklyShoppingList weeklyShoppingList = createCompleteShoppingList(shoppingList, menuPlan, isScaled)
            shoppingList.addToWeeklyShoppingLists(weeklyShoppingList)
        }
        return shoppingList
    }

    ShoppingList modifyShoppingList(PrintShoppingListCO shoppingListCO, ShoppingList shoppingListOld, Boolean isWeeklyList) {
        MenuPlan menuPlan = MenuPlan.get(shoppingListCO.menuPlanId?.toLong())
        Boolean isScaled = shoppingListCO.areServingsRequired
        ShoppingList shoppingList = new ShoppingList()
        shoppingList.name = shoppingListCO.name
        shoppingList.isWeeklyShoppingList = isWeeklyList
        shoppingList.menuPlan = menuPlan
        shoppingList.servings = shoppingListCO.servings.toInteger()
        shoppingList.party = LoginCredential.currentUser?.party
        if (isWeeklyList) {
            shoppingListCO.weeks.each {String weekIndex ->
                if ((weekIndex in shoppingListOld?.weeklyShoppingLists?.weekIndex*.toString()) && shoppingList.servings == shoppingListOld.servings) {
                    shoppingList.addToWeeklyShoppingLists(shoppingListOld?.weeklyShoppingLists?.find {it?.weekIndex == weekIndex.toInteger()})
                } else {
                    WeeklyShoppingList weeklyShoppingList = createWeeklyShoppingList(shoppingList, menuPlan, weekIndex, isScaled)
                    shoppingList.addToWeeklyShoppingLists(weeklyShoppingList)
                }
            }
        } else {
            WeeklyShoppingList weeklyShoppingList = createCompleteShoppingList(shoppingList, menuPlan, isScaled)
            shoppingList.addToWeeklyShoppingLists(weeklyShoppingList)
        }
        return shoppingList
    }

    WeeklyShoppingList createWeeklyShoppingList(ShoppingList shoppingList, MenuPlan menuPlan, String index, Boolean isScaled) {
        WeeklyShoppingList weeklyShoppingList = new WeeklyShoppingList()
        weeklyShoppingList.weekIndex = index.toInteger()
        weeklyShoppingList.products = getProductListForWeekFromMenuPlan(menuPlan, index, shoppingList, isScaled)
        weeklyShoppingList.groceries = getGroceryListForWeekFromMenuPlan(menuPlan, index)
        weeklyShoppingList.groceries = weeklyShoppingList.groceries.findAll {grocery ->
            !weeklyShoppingList.products.any {it.name.endsWith(grocery.name)}
        } as List
        return weeklyShoppingList
    }

    WeeklyShoppingList createCompleteShoppingList(ShoppingList shoppingList, MenuPlan menuPlan, Boolean isScaled) {
        WeeklyShoppingList weeklyShoppingList = new WeeklyShoppingList()
        weeklyShoppingList.weekIndex = 0
        weeklyShoppingList.products = getCompleteProductListFromMenuPlan(menuPlan, shoppingList, isScaled)
        weeklyShoppingList.groceries = getCompleteGroceryListFromMenuPlan(menuPlan)
        weeklyShoppingList.groceries = weeklyShoppingList.groceries.findAll {grocery ->
            !weeklyShoppingList.products.any {it.name.endsWith(grocery.name)}
        } as List
        return weeklyShoppingList
    }

    List<ShoppingIngredient> getProductListForWeekFromMenuPlan(MenuPlan menuPlan, String weekIndex, ShoppingList shoppingList, Boolean isScaled) {
        List<ShoppingIngredient> productListForWeek = []
        List<RecipeIngredient> weeklyRecipeIngredients = []
        Week week = menuPlan?.weeks[weekIndex?.toInteger()]
        week?.days?.each {Day day ->
            day.meals.each {Meal meal ->
                meal.items.each {Item item ->
                    if (item?.instanceOf(Recipe.class)) {
                        weeklyRecipeIngredients += getRecipeIngredientsList(item, shoppingList, isScaled)
                    }
                }
            }
        }
        productListForWeek = getWeeklyProductsGroupByAisle(weeklyRecipeIngredients)
        return productListForWeek
    }

    List<ShoppingIngredient> getCompleteProductListFromMenuPlan(MenuPlan menuPlan, ShoppingList shoppingList, Boolean isScaled) {
        List<ShoppingIngredient> allProductList = []
        List<RecipeIngredient> weeklyRecipeIngredients = []

        [0, 1, 2, 3].each {Integer index ->
            Week week = menuPlan?.weeks[index]
            week?.days?.each {Day day ->
                day?.meals?.each {Meal meal ->
                    meal?.items?.each {Item item ->
                        if (item?.instanceOf(Recipe.class)) {
                            weeklyRecipeIngredients += getRecipeIngredientsList(item, shoppingList, isScaled)
                        }
                    }
                }
            }
        }
        allProductList = getWeeklyProductsGroupByAisle(weeklyRecipeIngredients)
        return allProductList
    }

    List<RecipeIngredient> getRecipeIngredientsList(def recipe, ShoppingList shoppingList, Boolean isScaled) {
        List<RecipeIngredient> ingredients = []

        if (recipe?.instanceOf(Recipe.class)) {
            recipe.ingredients.each {RecipeIngredient recipeIngredient ->

//                if (recipeIngredient.ingredient?.instanceOf(Recipe.class)) {
//                    List<RecipeIngredient> subRecipeIngredients = getRecipeIngredientsList(recipeIngredient.ingredient as Recipe, shoppingList)
//                    subRecipeIngredients.each {RecipeIngredient subRecipeIngredient ->
//                        if (subRecipeIngredient.quantity && recipeIngredient.quantity) {
//                            RecipeIngredient newSubRecipeIngredient = new RecipeIngredient()
//                            newSubRecipeIngredient.with {
//                                ingredient = subRecipeIngredient.ingredient
//                                quantity = Quantity.multiply(subRecipeIngredient.quantity, recipeIngredient.quantity)
//                                aisle = subRecipeIngredient.aisle
//                                preparationMethod = subRecipeIngredient.preparationMethod
//                            }
//                            if (newSubRecipeIngredient.quantity) {ingredients.add(newSubRecipeIngredient)}
//                        } else {
//                            if (subRecipeIngredient.quantity) {
//                                RecipeIngredient recipeIngredientNew = cloneRecipeIngredient(subRecipeIngredient)
//                                recipeIngredientNew.quantity.value = ((recipeIngredient.quantity.value * shoppingList.servings) / recipeIngredient.recipe.servings).toFloat()
//                                ingredients.add(recipeIngredientNew)
//                            }
//                        }
//                    }

//                    RecipeIngredient recipeIngredientNew = cloneRecipeIngredient(recipeIngredient)
//                    recipeIngredientNew.quantity.value = ((recipeIngredient.quantity.value * shoppingList.servings) / recipeIngredient.recipe.servings).toFloat()
//                    ingredients.add(recipeIngredientNew)
//                } else {
                if (recipeIngredient.quantity) {
                    RecipeIngredient recipeIngredientNew = cloneRecipeIngredient(recipeIngredient)
                    if (isScaled) {
                        Float value = (recipeIngredient?.quantity?.value) ? recipeIngredient.quantity.value : 1.0f
                        Integer servings = recipeIngredient.recipe.servings
                        recipeIngredientNew.quantity.value = ((value * shoppingList.servings) / servings).toFloat()
                        if (!recipeIngredientNew.quantity.savedUnit || !recipeIngredientNew.quantity.savedUnit.isConvertible) {
                            recipeIngredientNew.quantity.value = Math.ceil(recipeIngredientNew.quantity.value)
                        }
                    }
                    ingredients.add(recipeIngredientNew)
                }
//                }
            }
        }
        return ingredients
    }

    List<ShoppingIngredient> getWeeklyProductsGroupByAisle(List<RecipeIngredient> weeklyRecipeIngredients) {
        List<ShoppingIngredient> productListForWeek = []

        Aisle otherAisle = new Aisle(name: 'Others')
        Map<Aisle, List<RecipeIngredient>> ingredientsGroupByAisles = weeklyRecipeIngredients.groupBy {return (it.aisle ? it.aisle : otherAisle)}
        Set<Aisle> aisles = ingredientsGroupByAisles.keySet()

        aisles.each {Aisle aisle ->
            List<RecipeIngredient> ingredientsByAisle = ingredientsGroupByAisles[aisle]
            Map ingredientsGroupByName = ingredientsByAisle.groupBy {it.ingredient}
            Set differentIngredients = ingredientsGroupByName.keySet()
            differentIngredients.each {Item differentIngredient ->
                List<RecipeIngredient> similarIngredients = ingredientsGroupByName[differentIngredient]


                Map similarIngredientsWithSameUnits = similarIngredients.groupBy {
                    if (!it.quantity.unit) {
                        return 1
                    } else if (!it.quantity.unit.isWeightUnit) {
                        return 2
                    } else {
                        return ((it.quantity.unit.isConvertible) ? 3 : it.quantity.unit)
                    }
                }
                List<Quantity> ingredientsAggregatedWithSimilarUnits = []
                similarIngredientsWithSameUnits.values()?.each {def sameUnitQuantities ->
                    Quantity totalQuantity = sameUnitQuantities[0].quantity
                    sameUnitQuantities.tail().each {
                        totalQuantity = Quantity.add(totalQuantity, it.quantity)
                    }
                    ingredientsAggregatedWithSimilarUnits.add(totalQuantity)
                }
                ingredientsAggregatedWithSimilarUnits?.each {Quantity quantity ->
                    ShoppingIngredient shoppingIngredient = new ShoppingIngredient(name: "${(quantity) ? quantity.toBiggestUnitString(differentIngredient.density) + ' ' : ''}" + differentIngredient.name, aisle: (aisle?.id) ? aisle : null)
                    productListForWeek.add(shoppingIngredient)
                }
            }
        }
        return productListForWeek
    }

    RecipeIngredient cloneRecipeIngredient(RecipeIngredient recipeIngredient) {
        RecipeIngredient recipeIngredientNew = new RecipeIngredient()
        Quantity quantity = new Quantity()
        quantity.value = recipeIngredient.quantity.value
        quantity.unit = recipeIngredient.quantity.unit
        quantity.savedUnit = recipeIngredient.quantity.savedUnit

        recipeIngredientNew.ingredient = recipeIngredient.ingredient
        recipeIngredientNew.quantity = quantity
        recipeIngredientNew.aisle = recipeIngredient.aisle
        recipeIngredientNew.preparationMethod = recipeIngredient.preparationMethod
        return recipeIngredientNew
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

    List<ShoppingIngredient> getCompleteGroceryListFromMenuPlan(MenuPlan menuPlan) {
        List<ShoppingIngredient> completeGroceryList = []
        List<Product> allGroceryItems = []
        [0, 1, 2, 3].each {Integer index ->
            Week week = menuPlan?.weeks[index]
            week?.days?.each {Day day ->
                day?.meals?.each {Meal meal ->
                    meal?.items?.each {Item item ->
                        allGroceryItems += getProductsList(item)
                    }
                }
            }
        }
        allGroceryItems = allGroceryItems.unique() as List
        allGroceryItems.each {def product ->
            ShoppingIngredient shoppingIngredient = new ShoppingIngredient(name: product.name, aisle: product.suggestedAisle)
            completeGroceryList.add(shoppingIngredient)
        }
        return completeGroceryList
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
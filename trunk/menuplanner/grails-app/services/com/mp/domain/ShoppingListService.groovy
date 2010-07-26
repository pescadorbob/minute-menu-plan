package com.mp.domain

class ShoppingListService {

    boolean transactional = true

    ShoppingList createShoppingList(PrintShoppingListCO shoppingListCO) {
        MenuPlan menuPlan = MenuPlan.get(shoppingListCO.menuPlanId?.toLong())
        ShoppingList shoppingList = new ShoppingList()
        shoppingList.name = shoppingListCO.name
        shoppingList.menuPlan = menuPlan
        shoppingList.servings = shoppingListCO.servings.toInteger()
        shoppingList.party = LoginCredential.currentUser?.party
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
        shoppingList.party = LoginCredential.currentUser?.party
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
        weeklyShoppingList.products = getProductListForWeekFromMenuPlan(menuPlan, index, shoppingList)
        weeklyShoppingList.groceries = getGroceryListForWeekFromMenuPlan(menuPlan, index)
        return weeklyShoppingList
    }

    List<ShoppingIngredient> getProductListForWeekFromMenuPlan(MenuPlan menuPlan, String weekIndex, ShoppingList shoppingList) {
        List<ShoppingIngredient> productListForWeek = []
        List<RecipeIngredient> weeklyRecipeIngredients = []
        Week week = menuPlan?.weeks[weekIndex?.toInteger()]

        week?.days?.each {Day day ->
            day.meals.each {Meal meal ->
                meal.items.each {Item item ->
                    if (item?.instanceOf(Recipe.class)) {
                        weeklyRecipeIngredients += getRecipeIngredientsList(item, shoppingList)
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
                ShoppingIngredient shoppingIngredient = new ShoppingIngredient(name: "${(total) ? total.toBiggestUnitString() + ' ' : ''}" + differentIngredient.name, aisle: (aisle?.id) ? aisle : null)
                productListForWeek.add(shoppingIngredient)
            }
        }

        return productListForWeek
    }

    List<RecipeIngredient> getRecipeIngredientsList(def recipe, ShoppingList shoppingList) {
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
                    Float value = (recipeIngredient?.quantity?.value) ? recipeIngredient.quantity.value : 1.0f
                    Integer servings = recipeIngredient.recipe.servings
                    recipeIngredientNew.quantity.value = ((value * shoppingList.servings) / servings).toFloat()
                    if(!recipeIngredientNew.quantity.savedUnit){
                        recipeIngredientNew.quantity.value = Math.ceil(recipeIngredientNew.quantity.value)
                    }
                    ingredients.add(recipeIngredientNew)
                }
//                }
            }
        }
        return ingredients
    }

    RecipeIngredient cloneRecipeIngredient(RecipeIngredient recipeIngredient) {
        RecipeIngredient recipeIngredientNew = new RecipeIngredient()
        Quantity quantity = new Quantity()
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


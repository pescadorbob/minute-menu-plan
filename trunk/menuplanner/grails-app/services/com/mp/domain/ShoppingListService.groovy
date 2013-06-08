package com.mp.domain

import com.mp.tools.UserTools
import com.mp.domain.pricing.Price
import com.mp.domain.ndb.ItemNutritionLink
import com.mp.domain.ndb.NDBWeight
import com.mp.tools.UnitUtil
import org.apache.commons.math.util.MathUtils
import com.mp.domain.pricing.ItemPrice
import com.mp.domain.pricing.PriceType

class ShoppingListService {

  boolean transactional = true

  ShoppingList createShoppingList(PrintShoppingListCO shoppingListCO, Boolean isWeeklyList,errors) {
    MenuPlan menuPlan = MenuPlan.get(shoppingListCO.menuPlanId?.toLong())
    Boolean isScaled = shoppingListCO.areServingsRequired
    ShoppingList shoppingList = new ShoppingList()
    shoppingList.name = shoppingListCO?.name
    shoppingList.isWeeklyShoppingList = isWeeklyList
    shoppingList.menuPlan = menuPlan
    shoppingList.servings = (isScaled && shoppingListCO?.servings) ? shoppingListCO?.servings?.toInteger() : 1
    shoppingList.party = UserTools.currentUser?.party
    if (isWeeklyList) {
      shoppingListCO.weeks.each {String weekIndex ->
        WeeklyShoppingList weeklyShoppingList = createWeeklyShoppingList(shoppingList, menuPlan, weekIndex, isScaled)
        shoppingList.addToWeeklyShoppingLists(weeklyShoppingList)
      }
    } else {
      WeeklyShoppingList weeklyShoppingList = createCompleteShoppingList(shoppingList, menuPlan, isScaled,errors)
      shoppingList.addToWeeklyShoppingLists(weeklyShoppingList)
    }
    return shoppingList
  }

  ShoppingList modifyShoppingList(PrintShoppingListCO shoppingListCO, ShoppingList shoppingListOld, Boolean isWeeklyList,errors) {
    MenuPlan menuPlan = MenuPlan.get(shoppingListCO.menuPlanId?.toLong())
    Boolean isScaled = shoppingListCO.areServingsRequired
    ShoppingList shoppingList = new ShoppingList()
    shoppingList.name = shoppingListCO.name
    shoppingList.isWeeklyShoppingList = isWeeklyList
    shoppingList.menuPlan = menuPlan
    shoppingList.servings = shoppingListCO.servings.toInteger()
    shoppingList.party = UserTools.currentUser?.party
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
      WeeklyShoppingList weeklyShoppingList = createCompleteShoppingList(shoppingList, menuPlan, isScaled,errors)
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

  WeeklyShoppingList createCompleteShoppingList(ShoppingList shoppingList, MenuPlan menuPlan, Boolean isScaled,errors) {
    WeeklyShoppingList weeklyShoppingList = new WeeklyShoppingList()
    weeklyShoppingList.weekIndex = 0
    weeklyShoppingList.products = getCompleteProductListFromMenuPlan(menuPlan, shoppingList, isScaled,errors)
    weeklyShoppingList.groceries = getCompleteGroceryListFromMenuPlan(menuPlan)
    weeklyShoppingList.groceries = weeklyShoppingList.groceries.findAll {grocery ->
      !weeklyShoppingList.products.any {it.name.endsWith(grocery.name)}
    } as List
    return weeklyShoppingList
  }

  List<ShoppingIngredient> getProductListForWeekFromMenuPlan(MenuPlan menuPlan, String weekIndex, ShoppingList shoppingList, Boolean isScaled,errors) {
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
    productListForWeek = getWeeklyProductsGroupByAisle(weeklyRecipeIngredients,errors)
    return productListForWeek
  }

  List<ShoppingIngredient> getCompleteProductListFromMenuPlan(MenuPlan menuPlan, ShoppingList shoppingList, Boolean isScaled,errors) {
    List<ShoppingIngredient> allProductList = []
    List<RecipeIngredient> weeklyRecipeIngredients = []

    [0, 1, 2, 3].each {Integer index ->
      Week week = menuPlan?.weeks[index]
      week?.days?.each {Day day ->
        day?.meals?.each {Meal meal ->
          meal?.items?.each {Item item ->
            if (item?.instanceOf(Recipe.class)) {
              def list = getRecipeIngredientsList(item, shoppingList, isScaled)
              weeklyRecipeIngredients += list
            }
          }
        }
      }
    }
    allProductList = getWeeklyProductsGroupByAisle(weeklyRecipeIngredients,errors)
    return allProductList
  }

  List<RecipeIngredient> getRecipeIngredientsList(def recipe, ShoppingList shoppingList, Boolean isScaled) {
    List<RecipeIngredient> ingredients = []

    if (recipe?.instanceOf(Recipe.class)) {
      recipe.ingredients.each {RecipeIngredient recipeIngredient ->

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

  List<ShoppingIngredient> getWeeklyProductsGroupByAisle(List<RecipeIngredient> weeklyRecipeIngredients,errors) {
    List<ShoppingIngredient> productListForWeek = []
    List<StandardConversion> standardConversions = StandardConversion.list([cache: true])

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
            return 'unitless'
//          } else if (!it.quantity.unit.isWeightUnit) {
//            return 'weightless'
          } else {
            return ((it.quantity.unit.isConvertible) ? 'convertible' : it.quantity.unit)
          }
        }
        List<Quantity> ingredientsAggregatedWithSimilarUnits = []
        similarIngredientsWithSameUnits.values()?.each {def sameUnitQuantities ->
            RecipeIngredient initialRecipeIngredient =  sameUnitQuantities[0]
          Quantity totalQuantity = initialRecipeIngredient.quantity
          sameUnitQuantities.tail().each {
            def addedQuantity = Quantity.add(totalQuantity, it.quantity, standardConversions)
            if(addedQuantity){
                totalQuantity = addedQuantity
            } else {
                // if these quantities can't be added, there was an error.
                errors.add(new Error("Couldn't add ${it.quantity} ${initialRecipeIngredient.ingredient} to Total:${totalQuantity} ${initialRecipeIngredient.ingredient} "))
            }
          }
          ingredientsAggregatedWithSimilarUnits.add(totalQuantity)
        }
        ingredientsAggregatedWithSimilarUnits?.each {Quantity quantity ->
          // the grocery quantity is known only if the ingredient has been matched to a food item.
          Quantity groceryQuantity = calculateGroceryQuantity(quantity, differentIngredient)
          groceryQuantity.s()
          Price predictedPrice = calculatePredictedPrice(groceryQuantity, differentIngredient)
          def name = "${groceryQuantity} ${differentIngredient.name} "
          if (predictedPrice?.price) {
              Price avePrice = getAvePrice(differentIngredient)
            name += " \$${MathUtils.round(predictedPrice.price, 2)} + ${avePrice}"
          }
          ShoppingIngredient shoppingIngredient = new ShoppingIngredient(
                  quantity: groceryQuantity,
                  ingredient: differentIngredient,
                  name: name,
                  aisle: (aisle?.id) ? aisle : null,
                  predictedPrice: predictedPrice)
          productListForWeek.add(shoppingIngredient)
        }
      }
    }
    return productListForWeek
  }

  Quantity calculateGroceryQuantity(Quantity fromQ, Item fromProduct) {
    Unit toUnit
    if (fromProduct?.mainShoppingListUnit) {
      toUnit = Unit.findByName(fromProduct.mainShoppingListUnit)
    } else {
      toUnit = Unit.findByName('Pound')
    }
    Quantity converted = UnitUtil.convert(fromQ, null,
            fromProduct, toUnit)
    Quantity normalized = UnitUtil.normalizeQuantity(converted)
    normalized
  }

  Price calculatePredictedPrice(Quantity quantity, Item item) {
    // now, look at the average price of this item to predict the price

      Price avePrice = getAvePrice(item)
    def p = null
    if (avePrice) {
        Unit preferredGroceryUnit = Unit.findByName(item.mainShoppingListUnit)
        BigDecimal priceDollarPerMilliliter = avePrice.price / avePrice.quantity.value
        Price price = new Price(
                price: priceDollarPerMilliliter*quantity.value, 
                quantity: quantity)
        price.s()
        p = price
    }
    p
  }

    private Price getAvePrice(Item item) {
        def c = ItemPrice.createCriteria()
        def avePrices = c {
            eq('type', PriceType.AVE)
            priceOf {
                eq('id', item.id)
            }
        }
        Price avePrice
        if (avePrices.size() > 0)
            avePrice = avePrices.first().price
        return avePrice
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

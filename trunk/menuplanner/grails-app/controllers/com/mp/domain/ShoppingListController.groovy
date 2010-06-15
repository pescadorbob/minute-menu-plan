package com.mp.domain

class ShoppingListController {

    def shoppingListService

    def index = { }

    WeeklyShoppingList createWeeklyShoppingList(List<String> groceries, ShoppingList shoppingList, MenuPlan menuPlan, Integer index) {
        WeeklyShoppingList weeklyShoppingList = new WeeklyShoppingList()
        weeklyShoppingList.weekIndex = index
        shoppingList.addToWeeklyShoppingLists(weeklyShoppingList)
        weeklyShoppingList.s()
        Week week = menuPlan?.weeks?.get(index)
        week.days.each {Day day ->
            List<Item> items = day?.breakfast + day?.lunch + day?.dinner
            items.each {Item item ->
                if (item?.instanceOf(Recipe)) {
                    item?.ingredients?.each {RecipeIngredient ingredient ->
                        if (ingredient?.ingredient?.id in weeklyShoppingList?.products*.item?.id) {
                            def shoppingIngredient = weeklyShoppingList?.products?.find {it?.item?.id == ingredient?.ingredient?.id}
                            Quantity quantity = Quantity.add(shoppingIngredient?.quantity, ingredient?.quantity)
                            quantity.s()
                            shoppingIngredient?.quantity = quantity
                            shoppingIngredient.s()
                        } else {
                            ShoppingIngredient shoppingIngredient = new ShoppingIngredient()
                            shoppingIngredient.item = ingredient.ingredient
                            shoppingIngredient.quantity = ingredient.quantity
                            shoppingIngredient.weeklyShoppingList = weeklyShoppingList
                            weeklyShoppingList.addToProducts(shoppingIngredient)
                            shoppingIngredient.s()
                        }
                    }
                } else {
                    weeklyShoppingList.addToGroceries(item)
                }
            }
        }
        groceries?.each {String name ->
            Item item = Item.findByName(name)
            if (!item) {
                item = new Product(name: name).s()
            }
            weeklyShoppingList.groceries.add(item)
        }
        return weeklyShoppingList
    }

    def create = {
        List<String> weekList = params.weekList?.tokenize('[, ]')
        MenuPlan menuPlan = MenuPlan.get(params?.menuPlanId?.toLong())
        ShoppingList shoppingList = new ShoppingList()
        shoppingList.menuPlan = menuPlan
        shoppingList.name = params?.shoppingListName
        shoppingList.servings = params?.servings?.toInteger()
        shoppingList.user = User.currentUser
        shoppingList.s()
        ['0', '1', '2', '3'].each {String index ->
            List<String> groceries = params?.list('groceries' + index)
            if (index in weekList) {
                createWeeklyShoppingList(groceries, shoppingList, menuPlan, index?.toInteger())
            }
        }
        redirect(controller: 'shoppingList', action: 'show', id: shoppingList?.id)
    }

    def show = {
        ShoppingList shoppingList = ShoppingList.get(params.id)

        render(view: 'show', model: [shoppingList: shoppingList])
    }

    def printShoppingList = {
        User user = User.currentUser
        List<MenuPlan> menuPlans = MenuPlan.findAllByOwner(user)
        render(view: 'printShoppingList', model: [menuPlans: menuPlans, servings: user.mouthsToFeed])
    }

    def detailShoppingList = {PrintShoppingListCO pslCO ->
        if (pslCO.validate()) {
            Integer servings = params?.servings?.toInteger()
            MenuPlan menuPlan = MenuPlan.get(params?.menuPlanId)
            List<Map<String, Quantity>> productListForWeeks = []
            List<List<String>> groceryListForWeeks = []
            params?.weeks?.each {String weekIndex ->
                Map<String, Quantity> productListForWeek = [:]
                List<String> groceryListForWeek = []
                try{
                    productListForWeek = shoppingListService.getProductListForWeek(menuPlan, weekIndex)
                    groceryListForWeek = shoppingListService.getGroceryListForWeek(menuPlan, weekIndex)
                } catch (e){
                    e.printStackTrace()
                }
                productListForWeeks.add(productListForWeek)
                groceryListForWeeks.add(groceryListForWeek)
            }
            render(view: 'detailShoppingList', model: [menuPlan: menuPlan, servings: servings, shoppingListName: params?.name, weeks: params?.list('weeks'), productListForWeeks: productListForWeeks, groceryListForWeeks:groceryListForWeeks])
        } else {
            pslCO.errors.allErrors.each {
                println it
            }
            User user = User.currentUser
            List<MenuPlan> menuPlans = MenuPlan.findAllByOwner(user)
            render(view: 'printShoppingList', model: [pslCO: pslCO, menuPlans: menuPlans, servings: user.mouthsToFeed])
        }
    }

    def cancelDetailShoppingList = {
        redirect(controller: 'shoppingList', action: 'printShoppingList')
    }

}

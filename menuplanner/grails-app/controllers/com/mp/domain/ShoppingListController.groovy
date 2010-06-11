package com.mp.domain

import com.mp.UtilController

class ShoppingListController {

    def shoppingListService

    def index = { }

    def create = {
        List<String> weekList = params.weekList?.tokenize('[, ]')
        MenuPlan menuPlan = MenuPlan.get(params?.menuPlanId?.toLong())

        ShoppingList shoppingList = new ShoppingList()
        shoppingList.menuPlan = menuPlan
        shoppingList.name = params?.shoppingListName
        shoppingList.servings = params?.servings
        shoppingList.user = User.currentUser

        WeeklyShoppingList weeklyShoppingList
        if ('0' in weekList) {
            weeklyShoppingList = new WeeklyShoppingList(shoppingList: shoppingList, week: menuPlan.weeks[0])
            params?.groceries0?.each {String name ->
                Item item = Item.findByName(name)
                if (!item) {
                    item = new Product(name: name).s()
                }
                weeklyShoppingList.groceries.add(item)
            }
            shoppingList.weeklyShoppingLists.add(weeklyShoppingList)
        }
        if ('1' in weekList) {
            weeklyShoppingList = new WeeklyShoppingList(shoppingList: shoppingList, week: menuPlan.weeks[1])
            params?.groceries1?.each {String name ->
                Item item = Item.findByName(name)
                if (!item) {
                    item = new Product(name: name).s()
                }
                weeklyShoppingList.groceries.add(item)
            }
            shoppingList.weeklyShoppingLists.add(weeklyShoppingList)
        }
        if ('2' in weekList) {
            weeklyShoppingList = new WeeklyShoppingList(shoppingList: shoppingList, week: menuPlan.weeks[2])
            params?.groceries2?.each {String name ->
                Item item = Item.findByName(name)
                if (!item) {
                    item = new Product(name: name).s()
                }
                weeklyShoppingList.groceries.add(item)
            }
            shoppingList.weeklyShoppingLists.add(weeklyShoppingList)
        }
        if ('3' in weekList) {
            weeklyShoppingList = new WeeklyShoppingList(shoppingList: shoppingList, week: menuPlan.weeks[3])
            params?.groceries3?.each {String name ->
                Item item = Item.findByName(name)
                if (!item) {
                    item = new Product(name: name).s()
                }
                weeklyShoppingList.groceries.add(item)
            }
            shoppingList.weeklyShoppingLists.add(weeklyShoppingList)
        }
        shoppingList.s()
        render(view: 'detailShoppingList', model: [menuPlan: menuPlan, weeks: weekList])
//        redirect(controller: 'shoppingList', action: 'show', id: shoppingList?.id)
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
            def productListPerWeek = []
            params?.weeks?.each {String weekIndex ->
                Map<String, Quantity> productListForWeek = [:]
                menuPlan?.weeks[weekIndex?.toInteger()]?.days?.each {Day day ->
                    List<Item> items = day?.breakfast + day?.lunch + day?.dinner
                    items.each {Item item ->
                        String prodName
                        Quantity quantity
                        if (item?.instanceOf(Recipe)) {
                            item.ingredients.each {RecipeIngredient recipeIngredient ->
                                prodName = recipeIngredient?.ingredient
                                quantity = recipeIngredient?.quantity
                                productListForWeek[prodName] = productListForWeek[prodName] ? productListForWeek[prodName] + quantity : quantity
                            }
                        } else {
                            prodName = item?.name
                            quantity = null
                            productListForWeek[prodName] = quantity
                        }
                    }
                }
                productListPerWeek.add(productListForWeek)
            }
            render(view: 'detailShoppingList', model: [menuPlan: menuPlan, servings: params?.servings, shoppingListName: params?.name, weeks: params?.list('weeks'), productListPerWeek: productListPerWeek])
        } else {
            pslCO.errors.allErrors.each {
                println it
            }
            User user = User.currentUser
            List<MenuPlan> menuPlans = MenuPlan.findAllByOwner(user)
            render(view: 'printShoppingList', model: [pslCO: pslCO, menuPlans: menuPlans, servings: user.mouthsToFeed])
        }


    }
}

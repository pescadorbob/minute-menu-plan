package com.mp.domain

class ShoppingListController {

    def index = { }

    def create = {
        List<String> weekList = params.weekList?.tokenize('[, ]')
        println "*************************" + weekList 
        MenuPlan menuPlan = MenuPlan.get(params?.menuPlanId?.toLong())

        ShoppingList shoppingList = new ShoppingList()
        shoppingList.menuPlan = menuPlan
        shoppingList.name = params?.shoppingListName
        shoppingList.servings = params?.servings
        shoppingList.user = User.currentUser

        WeeklyShoppingList weeklyShoppingList
        if ('0' in weekList) {
            println "****  0, ${menuPlan.weeks[0]?.id}"
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
            println "****  1, ${menuPlan.weeks[1]?.id}"
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
            println "****  2, ${menuPlan.weeks[2]?.id}"
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
            println "****  3, ${menuPlan.weeks[3]?.id}"
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
    }

    def printShoppingList = {
        User user = User.currentUser
        List<MenuPlan> menuPlans = MenuPlan.findAllByOwner(user)
        render(view: 'printShoppingList', model: [menuPlans: menuPlans, servings: user.mouthsToFeed])
    }
    def detailShoppingList = {
        MenuPlan menuPlan = MenuPlan.get(params?.menuPlanId)
        render(view: 'detailShoppingList', model: [menuPlan: menuPlan, servings: params?.servings, shoppingListName: params?.name, weeks: params?.list('weeks')])
    }
}

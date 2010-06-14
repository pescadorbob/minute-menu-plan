package com.mp.domain

class ShoppingListController {

    def shoppingListService

    def index = { }

    WeeklyShoppingList createWeeklyShoppingList(List<String> groceries, ShoppingList shoppingList, Week week) {
        WeeklyShoppingList weeklyShoppingList = new WeeklyShoppingList(shoppingList: shoppingList, week: week)
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
        shoppingList.servings = params?.servings
        shoppingList.user = User.currentUser

        WeeklyShoppingList weeklyShoppingList
        ['0','1','2','3'].each{String index->
            if (index in weekList) {
                shoppingList.weeklyShoppingLists.add(createWeeklyShoppingList(params?.list('groceries' + index), shoppingList, menuPlan.weeks[index.toInteger()]))
            }
        }
/*
        if ('0' in weekList) {
            shoppingList.weeklyShoppingLists.add(createWeeklyShoppingList(params?.list('groceries0'), shoppingList, menuPlan.weeks[0]))
        }
        if ('1' in weekList) {
            shoppingList.weeklyShoppingLists.add(createWeeklyShoppingList(params?.list('groceries1'), shoppingList, menuPlan.weeks[1]))
        }
        if ('2' in weekList) {
            shoppingList.weeklyShoppingLists.add(createWeeklyShoppingList(params?.list('groceries2'), shoppingList, menuPlan.weeks[2]))
        }
        if ('3' in weekList) {
            shoppingList.weeklyShoppingLists.add(createWeeklyShoppingList(params?.list('groceries3'), shoppingList, menuPlan.weeks[3]))
        }
*/
        shoppingList.s()
        redirect(controller: 'shoppingList', action: 'show', id: shoppingList?.id)
    }

    def show = {
        ShoppingList shoppingList = ShoppingList.get(params.id)
        MenuPlan menuPlan = shoppingList.menuPlan
        def productListPerWeek = []
        List<String> weeks = []
        shoppingList?.weeklyShoppingLists?.each {WeeklyShoppingList weeklyShoppingList ->
            String weekIndex
            Week week = weeklyShoppingList.week
            weekIndex = menuPlan?.weeks*.id?.indexOf(week?.id)?.toString()
            weeks.add(weekIndex)
            Map<String, Quantity> productListForWeek = shoppingListService.getProductListForWeek(menuPlan, weekIndex)
            productListPerWeek.add(productListForWeek)
        }
        render(view: 'show', model: [shoppingList: shoppingList, productListPerWeek: productListPerWeek, weeks: weeks])
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
            params?.weeks?.each {String weekIndex ->
                Map<String, Quantity> productListForWeek = shoppingListService.getProductListForWeek(menuPlan, weekIndex)
                productListForWeeks.add(productListForWeek)
            }
            render(view: 'detailShoppingList', model: [menuPlan: menuPlan, servings: params?.servings, shoppingListName: params?.name, weeks: params?.list('weeks'), productListPerWeek: productListForWeeks])
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

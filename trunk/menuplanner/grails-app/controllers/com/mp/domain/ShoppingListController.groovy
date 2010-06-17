package com.mp.domain

class ShoppingListController {

    def shoppingListService

    def index = { }

    WeeklyShoppingList createWeeklyShoppingListFromMenuPlan(List<String> groceries, ShoppingList shoppingList, MenuPlan menuPlan, Integer index) {
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
                }
            }
        }
        groceries?.each {String name ->
            Item item = Item.findByName(name)
            if (!item && name.trim()) {
                item = new Product(name: name).s()
            }
            if (!(item?.id in weeklyShoppingList?.groceries*.id)) {
                weeklyShoppingList.groceries.add(item)
            }
        }
        return weeklyShoppingList
    }

    def save = {
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
                createWeeklyShoppingListFromMenuPlan(groceries, shoppingList, menuPlan, index?.toInteger())
            }
        }
        redirect(controller: 'shoppingList', action: 'show', id: shoppingList?.id)
    }

    def show = {
        ShoppingList shoppingList = ShoppingList.get(params.id)
        render(view: 'show', model: [shoppingList: shoppingList])
    }

    def printShoppingList = {
        MenuPlan menuPlan = MenuPlan.get(params?.id?.toLong())
        User user = User.currentUser
        ShoppingList shoppingList = ShoppingList?.findByMenuPlanAndUser(menuPlan,user)
        if(!shoppingList){
            PrintShoppingListCO pslCO = new PrintShoppingListCO()
            pslCO.name = menuPlan?.name + '-Shopping List'
            pslCO.menuPlanId = params?.id
            pslCO.weeks = '[0,1,2,3]'
            pslCO.servings = user.mouthsToFeed.toString()
            List<MenuPlan> menuPlans = MenuPlan.findAllByOwner(user)
            render(view: 'printShoppingList', model: [pslCO: pslCO, menuPlans: menuPlans, servings: user.mouthsToFeed])
        } else{
            redirect(controller:'shoppingList', action:'show', id:shoppingList?.id)
        }
    }

    def create = {PrintShoppingListCO pslCO ->
        if (pslCO.validate()) {
            Integer servings = params?.servings?.toInteger()
            MenuPlan menuPlan = MenuPlan.get(params?.menuPlanId)
            List<Map<String, Quantity>> productListForWeeks = []
            List<List<String>> groceryListForWeeks = []
            params?.weeks?.each {String weekIndex ->
                Map<String, Quantity> productListForWeek = [:]
                List<String> groceryListForWeek = []
                try {
                    productListForWeek = shoppingListService.getProductListForWeekFromMenuPlan(menuPlan, weekIndex)
                    groceryListForWeek = shoppingListService.getGroceryListForWeekFromMenuPlan(menuPlan, weekIndex)
                } catch (e) {
                    e.printStackTrace()
                }
                productListForWeeks.add(productListForWeek)
                groceryListForWeeks.add(groceryListForWeek)
            }
            render(view: 'create', model: [menuPlan: menuPlan, servings: servings, shoppingListName: params?.name, weeks: params?.list('weeks'), productListForWeeks: productListForWeeks, groceryListForWeeks: groceryListForWeeks])
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
        redirect(controller: 'shoppingList', action: 'printShoppingList', id: params?.menuPlanId)
    }

    def edit = {
        ShoppingList shoppingList = ShoppingList.get(params?.shoppingListId?.toLong())
        User user = User.currentUser
        PrintShoppingListCO pslCO = new PrintShoppingListCO()
        pslCO.name = shoppingList?.name
        pslCO.menuPlanId = shoppingList?.menuPlan?.id
        pslCO.servings = shoppingList?.servings
        pslCO.weeks = shoppingList?.weeklyShoppingLists*.weekIndex.toString()
        List<MenuPlan> menuPlans = MenuPlan.findAllByOwner(user)

        render(view: 'printShoppingList', model: [pslCO: pslCO, menuPlans: menuPlans, shoppingListId: shoppingList?.id, servings: user.mouthsToFeed])
    }

    def modifyShoppingList = {PrintShoppingListCO pslCO ->
        if (pslCO.validate()) {
            ShoppingList shoppingList = ShoppingList.get(params?.shoppingListId?.toLong())
            String shoppingListId = shoppingList?.id?.toString()
            List<String> weekIndexInShoppingList = []
            List<String> weeks = params?.list('weeks')
            shoppingList?.weeklyShoppingLists?.each {WeeklyShoppingList weeklyShoppingList ->
                weekIndexInShoppingList.add(weeklyShoppingList?.weekIndex?.toString())
            }
            List<Map<String, Quantity>> productListForWeeks = []
            List<List<String>> groceryListForWeeks = []
            weeks.each {String weekIndex ->
                Map<String, Quantity> productListForWeek = [:]
                List<String> groceryListForWeek = []
                if (weekIndex in weekIndexInShoppingList) {
                    WeeklyShoppingList weeklyShoppingList = shoppingList?.weeklyShoppingLists?.find {it?.weekIndex == weekIndex?.toInteger()}
                    try {
                        productListForWeek = shoppingListService.getProductListForWeekFromShoppingList(weeklyShoppingList)
                        groceryListForWeek = shoppingListService.getGroceryListForWeekFromShoppingList(weeklyShoppingList)
                    } catch (e) {
                        e.printStackTrace()
                    }
                    productListForWeeks.add(productListForWeek)
                    groceryListForWeeks.add(groceryListForWeek)

                } else {
                    MenuPlan menuPlan = shoppingList?.menuPlan
                    try {
                        productListForWeek = shoppingListService.getProductListForWeekFromMenuPlan(menuPlan, weekIndex)
                        groceryListForWeek = shoppingListService.getGroceryListForWeekFromMenuPlan(menuPlan, weekIndex)
                    } catch (e) {
                        e.printStackTrace()
                    }
                    productListForWeeks.add(productListForWeek)
                    groceryListForWeeks.add(groceryListForWeek)
                }
            }
            render(view: 'create', model: [menuPlan: shoppingList.menuPlan, shoppingListId: shoppingListId,
                    servings: shoppingList.servings, shoppingListName: shoppingList?.name,
                    weeks: weeks, productListForWeeks: productListForWeeks, groceryListForWeeks: groceryListForWeeks])
        }
        else {
            pslCO.errors.allErrors.each {
                println it
            }
            User user = User.currentUser
            List<MenuPlan> menuPlans = MenuPlan.findAllByOwner(user)
            render(view: 'printShoppingList', model: [pslCO: pslCO, menuPlans: menuPlans, servings: user.mouthsToFeed])
        }
    }

    def update = {
        ShoppingList shoppingList = ShoppingList.get(params?.shoppingListId?.toLong())
        List<String> weekList = params.weekList?.tokenize('[, ]')

        List<WeeklyShoppingList> weeklyShoppingLists = shoppingList.weeklyShoppingLists
        shoppingList.weeklyShoppingLists = []
        weeklyShoppingLists.each {WeeklyShoppingList weeklyShoppingList ->
            try {
                weeklyShoppingList.delete(flush: true)
            } catch (Exception e) {
                e.printStackTrace()
            }
        }
        ['0', '1', '2', '3'].each {String index ->
            List<String> groceries = params?.list('groceries' + index)
            if (index in weekList) {
                createWeeklyShoppingListFromMenuPlan(groceries, shoppingList, shoppingList?.menuPlan, index?.toInteger())
            }
        }
        render(view: 'show', model: [shoppingList: shoppingList])
    }


    WeeklyShoppingList updateWeeklyShoppingList(List<String> groceries, WeeklyShoppingList weeklyShoppingList) {
        groceries?.each {String name ->
            Item item = Item.findByName(name)
            if (!item && name.trim()) {
                item = new Product(name: name).s()
            }
            if (!(item?.id in weeklyShoppingList?.groceries*.id)) {
                weeklyShoppingList.groceries.add(item)
            }
        }
        return weeklyShoppingList
    }
}

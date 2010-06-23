package com.mp.domain

class ShoppingListController {

    def shoppingListService
    def asynchronousMailService

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

    void createWeeklyShoppingList(List<ShoppingIngredient> groceries, List<ShoppingIngredient> products, ShoppingList shoppingList, Integer index) {
        WeeklyShoppingList weeklyShoppingList = new WeeklyShoppingList()
        weeklyShoppingList.weekIndex = index
        shoppingList.addToWeeklyShoppingLists(weeklyShoppingList)
        weeklyShoppingList.products = products
        weeklyShoppingList.groceries = groceries
        weeklyShoppingList.s()
    }

    def save = {
        List<String> weekList = params.weekList?.tokenize("[], ")
        MenuPlan menuPlan = MenuPlan.get(params?.menuPlanId?.toLong())
        ShoppingList shoppingList = new ShoppingList()
        shoppingList.menuPlan = menuPlan
        shoppingList.name = params?.shoppingListName
        shoppingList.servings = params?.servings?.toInteger()
        shoppingList.user = User.currentUser
        shoppingList.s()
        weekList.each {String index ->
            WeeklyShoppingList weeklyShoppingList = new WeeklyShoppingList(shoppingList: shoppingList, weekIndex: index.toInteger())

            List<ShoppingIngredient> products = []
            List<ShoppingIngredient> groceries = []

            Map groceriesByWeek = params.findAll {it.key.contains("week${index}.groceries")}
            groceriesByWeek.each {key, value ->
                String aisleId = key.tokenize(".")?.last()
                Aisle aisle
                if (aisleId) {
                    aisle = Aisle.get(aisleId?.toLong())
                }

                List<String> groceryNames = [value].flatten()
                groceryNames?.each {
                    ShoppingIngredient shoppingIngredient = new ShoppingIngredient()
                    shoppingIngredient.name = it
                    shoppingIngredient.aisle = aisle
                    groceries.add(shoppingIngredient.s())
                }
            }

            Map productsByWeek = params.findAll {it.key.contains("week${index}.products")}
            productsByWeek.each {key, value ->
                String aisleId = key.tokenize(".")?.last()
                Aisle aisle
                if (aisleId) {
                    aisle = Aisle.get(aisleId?.toLong())
                }
                List<String> productNames = [value].flatten()
                productNames?.each {
                    ShoppingIngredient shoppingIngredient = new ShoppingIngredient()
                    shoppingIngredient.name = it
                    shoppingIngredient.aisle = aisle
                    products.add(shoppingIngredient.s())
                }
            }
            weeklyShoppingList.products = products
            weeklyShoppingList.groceries = groceries

            shoppingList.addToWeeklyShoppingLists(weeklyShoppingList)
            weeklyShoppingList.s()
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
        ShoppingList shoppingList = ShoppingList?.findByMenuPlanAndUser(menuPlan, user)
        if (!shoppingList) {
            PrintShoppingListCO pslCO = new PrintShoppingListCO()
            pslCO.name = menuPlan?.name + '-Shopping List'
            pslCO.menuPlanId = params?.id
            pslCO.weeks = ['0', '1', '2', '3']
            pslCO.servings = user.mouthsToFeed.toString()
            List<MenuPlan> menuPlans = user?.menuPlans as List
            render(view: 'printShoppingList', model: [pslCO: pslCO, menuPlans: menuPlans, servings: user.mouthsToFeed])
        } else {
            redirect(controller: 'shoppingList', action: 'show', id: shoppingList?.id)
        }
    }

    def create = {PrintShoppingListCO pslCO ->
        if (pslCO.validate()) {
            ShoppingList shoppingList = shoppingListService.createShoppingList(pslCO)
            render(view: 'create', model: [shoppingList: shoppingList])
        } else {
            pslCO.errors.allErrors.each {
                println it
            }
            User user = User.currentUser
            List<MenuPlan> menuPlans = user?.menuPlans as List
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
        pslCO.weeks = shoppingList?.weeklyShoppingLists*.weekIndex*.toString()
        List<MenuPlan> menuPlans = user?.menuPlans as List
        render(view: 'printShoppingList', model: [pslCO: pslCO, menuPlans: menuPlans, shoppingListId: shoppingList?.id, servings: user.mouthsToFeed, shoppingList: shoppingList])
    }

    def modifyShoppingList = {PrintShoppingListCO pslCO ->
        if (pslCO.validate()) {
            ShoppingList shoppingListOld = ShoppingList.get(params?.shoppingListId?.toLong())
            ShoppingList shoppingListNew = shoppingListService.createShoppingList(pslCO)

            shoppingListNew.weeklyShoppingLists.each {WeeklyShoppingList wsl ->
                WeeklyShoppingList weeklyShoppingList = shoppingListOld.weeklyShoppingLists.find {it.weekIndex == wsl.weekIndex}
                if (weeklyShoppingList) {
                    wsl = weeklyShoppingList
                }
            }
            render(view: 'create', model: [shoppingList: shoppingListNew, shoppingListId: shoppingListOld?.id])
        }
        else {
            pslCO.errors.allErrors.each {
                println it
            }
            User user = User.currentUser
            List<MenuPlan> menuPlans = user?.menuPlans as List
            render(view: 'printShoppingList', model: [pslCO: pslCO, menuPlans: menuPlans, servings: user.mouthsToFeed])
        }
    }

    def printerFriendlyShoppingList = {
        ShoppingList shoppingList = ShoppingList.get(params.id)
        render(view: 'printerFriendlyShoppingList', model: [shoppingList: shoppingList])
    }

    def emailShoppingList = {
        ShoppingList shoppingList = ShoppingList.get(params.id)
        asynchronousMailService.sendAsynchronousMail {
            to User.currentUser?.email
            subject "Your Shopping List : ${shoppingList.name}"
            html g.render(template: '/shoppingList/emailShoppingList', model: [shoppingList: shoppingList])
        }
        render "Email sent to ${User.currentUser?.email}"
    }

    def update = {
        List<String> weekList = params.weekList?.tokenize("[], ")
        ShoppingList shoppingList = ShoppingList.get(params?.shoppingListId?.toLong())
        MenuPlan menuPlan = shoppingList?.menuPlan

        List<WeeklyShoppingList> weeklyShoppingLists = shoppingList.weeklyShoppingLists
        shoppingList.weeklyShoppingLists = []
        weeklyShoppingLists.each {WeeklyShoppingList weeklyShoppingList ->
            try {
                weeklyShoppingList.delete(flush: true)
            } catch (Exception e) {
                e.printStackTrace()
            }
        }

        weekList.each {String index ->
            WeeklyShoppingList weeklyShoppingList = new WeeklyShoppingList(shoppingList: shoppingList, weekIndex: index.toInteger())

            List<ShoppingIngredient> products = []
            List<ShoppingIngredient> groceries = []

            Map groceriesByWeek = params.findAll {it.key.contains("week${index}.groceries")}
            groceriesByWeek.each {key, value ->
                String aisleId = key.tokenize(".")?.last()
                Aisle aisle
                if (aisleId) {
                    aisle = Aisle.get(aisleId?.toLong())
                }

                List<String> groceryNames = [value].flatten()
                groceryNames?.each {
                    ShoppingIngredient shoppingIngredient = new ShoppingIngredient()
                    shoppingIngredient.name = it
                    shoppingIngredient.aisle = aisle
                    groceries.add(shoppingIngredient.s())
                }
            }

            Map productsByWeek = params.findAll {it.key.contains("week${index}.products")}
            productsByWeek.each {key, value ->
                String aisleId = key.tokenize(".")?.last()
                Aisle aisle
                if (aisleId) {
                    aisle = Aisle.get(aisleId?.toLong())
                }
                List<String> productNames = [value].flatten()
                productNames?.each {
                    ShoppingIngredient shoppingIngredient = new ShoppingIngredient()
                    shoppingIngredient.name = it
                    shoppingIngredient.aisle = aisle
                    products.add(shoppingIngredient.s())
                }
            }
            weeklyShoppingList.products = products
            weeklyShoppingList.groceries = groceries

            shoppingList.addToWeeklyShoppingLists(weeklyShoppingList)
            weeklyShoppingList.s()
        }
        redirect(controller: 'shoppingList', action: 'show', id: shoppingList?.id)
    }
    
}

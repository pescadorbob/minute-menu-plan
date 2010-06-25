package com.mp.domain

class ShoppingListController {

    def shoppingListService
    def asynchronousMailService

    def index = { }

    def generateShoppingList = {
        MenuPlan menuPlan = MenuPlan.get(params?.id?.toLong())
        User user = User.currentUser
        PrintShoppingListCO pslCO = new PrintShoppingListCO()
        pslCO.name = menuPlan?.name + '-Shopping List'
        pslCO.menuPlanId = params?.id
        pslCO.weeks = ['0', '1', '2', '3']
        pslCO.servings = user.mouthsToFeed.toString()
        List<MenuPlan> menuPlans = user?.menuPlans as List
        render(view: 'generateShoppingList', model: [pslCO: pslCO, menuPlans: menuPlans, servings: user.mouthsToFeed])
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
            render(view: 'generateShoppingList', model: [pslCO: pslCO, menuPlans: menuPlans, servings: user.mouthsToFeed])
        }
    }
    def show = {
        ShoppingList shoppingList = ShoppingList.get(params.id)
        render(view: 'show', model: [shoppingList: shoppingList])
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



    def cancelDetailShoppingList = {
        redirect(controller: 'shoppingList', action: 'generateShoppingList', id: params?.menuPlanId)
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
        render(view: 'generateShoppingList', model: [pslCO: pslCO, menuPlans: menuPlans, shoppingListId: shoppingList?.id, servings: user.mouthsToFeed, shoppingList: shoppingList])
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
            render(view: 'generateShoppingList', model: [pslCO: pslCO, menuPlans: menuPlans, servings: user.mouthsToFeed])
        }
    }

    def update = {
        List<String> weekList = params.weekList?.tokenize("[], ")
        ShoppingList shoppingList = ShoppingList.get(params?.shoppingListId?.toLong())

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


    def printerFriendlyShoppingList = {
        ShoppingList shoppingList = ShoppingList.get(params.id)
        render(view: 'printerFriendlyShoppingList', model: [shoppingList: shoppingList])
    }

    def emailShoppingList = {
        ShoppingList shoppingList = ShoppingList.get(params?.shoppingListId?.toLong())
        String emailAddress = params?.emailId
        asynchronousMailService.sendAsynchronousMail {
            to emailAddress
            subject "Your Shopping List : ${shoppingList.name}"
            html g.render(template: '/shoppingList/emailShoppingList', model: [shoppingList: shoppingList])
        }
        render "Email sent to ${User.currentUser?.email}"
    }

}

class PrintShoppingListCO {
    String name
    String menuPlanId
    List<String> weeks
    String servings

    static constraints = {
        name(blank: false, nullable: false)
        weeks(validator: {obj, val ->
            if (!val) {
                return 'default.blank.message'
            }
        })
        menuPlanId(nullable: false, blank: false)
        servings(blank: false, matches: /[0-9\s]*/)
    }
}
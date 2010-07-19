package com.mp.domain

class ShoppingListController {

    def shoppingListService
    def asynchronousMailService

    def index = { }

    def generateShoppingList = {
        MenuPlan menuPlan = MenuPlan.get(params?.id?.toLong())
        LoginCredential user = LoginCredential.currentUser
        PrintShoppingListCO pslCO = new PrintShoppingListCO()
        pslCO.name = menuPlan?.name + '-Shopping List'
        pslCO.menuPlanId = params?.id
        pslCO.weeks = ['0', '1', '2', '3']
        Integer mouthsToFeed = user?.party?.roles?.find{it.userType==UserType.Subscriber}?.mouthsToFeed
        pslCO.servings = mouthsToFeed?.toString()
        List<MenuPlan> menuPlans = user?.party?.menuPlans as List
        render(view: 'generateShoppingList', model: [pslCO: pslCO, menuPlans: menuPlans, servings: mouthsToFeed])
    }

    def create = {PrintShoppingListCO pslCO ->
        if (pslCO.validate()) {
            ShoppingList shoppingList = shoppingListService.createShoppingList(pslCO)
            render(view: 'create', model: [shoppingList: shoppingList])
        } else {
            pslCO.errors.allErrors.each {
                println it
            }
            LoginCredential user = LoginCredential.currentUser
            List<MenuPlan> menuPlans = user?.party?.menuPlans as List
            Integer mouthsToFeed = user?.party?.roles?.find{it.userType==UserType.Subscriber}?.mouthsToFeed
            render(view: 'generateShoppingList', model: [pslCO: pslCO, menuPlans: menuPlans, servings: mouthsToFeed])
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
        shoppingList.party = LoginCredential.currentUser?.party
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
        LoginCredential user = LoginCredential.currentUser
        PrintShoppingListCO pslCO = new PrintShoppingListCO()
        pslCO.name = shoppingList?.name
        pslCO.menuPlanId = shoppingList?.menuPlan?.id
        pslCO.servings = shoppingList?.servings
        pslCO.weeks = shoppingList?.weeklyShoppingLists*.weekIndex*.toString()
        List<MenuPlan> menuPlans = user?.party?.menuPlans as List
        Integer mouthsToFeed = user?.party?.roles?.find{it.userType==UserType.Subscriber}?.mouthsToFeed
        render(view: 'generateShoppingList', model: [pslCO: pslCO, menuPlans: menuPlans, shoppingListId: shoppingList?.id, servings: mouthsToFeed, shoppingList: shoppingList])
    }

    def modifyShoppingList = {PrintShoppingListCO pslCO ->
        if (pslCO.validate()) {

            ShoppingList shoppingListOld = ShoppingList.get(params?.shoppingListId?.toLong())
            ShoppingList shoppingListNew = shoppingListService.modifyShoppingList(pslCO, shoppingListOld)
            render(view: 'create', model: [shoppingList: shoppingListNew, shoppingListId: shoppingListOld?.id])
        }
        else {
            pslCO.errors.allErrors.each {
                println it
            }
            LoginCredential user = LoginCredential.currentUser
            List<MenuPlan> menuPlans = user?.party?.menuPlans as List
            Integer mouthsToFeed = user?.party?.roles?.find{it.userType==UserType.Subscriber}?.mouthsToFeed
            render(view: 'generateShoppingList', model: [pslCO: pslCO, menuPlans: menuPlans, servings: mouthsToFeed])
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
        render "Email sent to ${emailAddress}"
    }

}

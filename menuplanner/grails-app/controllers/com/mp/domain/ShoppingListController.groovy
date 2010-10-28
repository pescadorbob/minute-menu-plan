package com.mp.domain

import javax.servlet.http.Cookie
import org.codehaus.groovy.grails.commons.ConfigurationHolder

class ShoppingListController {

    def shoppingListService
    def asynchronousMailService
    def userService

    def index = { }

    def generateShoppingList = {
        MenuPlan menuPlan = MenuPlan.get(params?.id?.toLong())
        LoginCredential user = LoginCredential.currentUser
        PrintShoppingListCO pslCO = new PrintShoppingListCO()
        pslCO.name = menuPlan?.name + '-Shopping List'
        pslCO.menuPlanId = params?.id
        Integer mouthsToFeed = user?.party?.subscriber?.mouthsToFeed
        pslCO.servings = mouthsToFeed
        List<MenuPlan> menuPlans = user?.party?.menuPlans as List
        render(view: 'generateShoppingList', model: [pslCO: pslCO, menuPlans: menuPlans, servings: mouthsToFeed])
    }

    def createOriginal = {PrintShoppingListCO pslCO ->
        pslCO.areServingsRequired = false
        pslCO.areWeeksRequired = (params.shoppingList == "GENERATE_WITH_WEEKS")
        if (pslCO.validate()) {
            ShoppingList shoppingList
            Boolean weeklyList
            switch (params?.shoppingList) {
                case "GENERATE_WITH_WEEKS":
                    weeklyList = true
                    shoppingList = shoppingListService.createShoppingList(pslCO, weeklyList)
                    break;
                case "GENERATE_WITHOUT_WEEKS":
                    weeklyList = false
                    shoppingList = shoppingListService.createShoppingList(pslCO, weeklyList)
                    break;
            }
            render(view: 'create', model: [shoppingList: shoppingList, weeklyList: weeklyList])
        } else {
            pslCO.errors.allErrors.each {
                println it
            }
            LoginCredential user = LoginCredential.currentUser
            List<MenuPlan> menuPlans = user?.party?.menuPlans as List
            Integer mouthsToFeed = user?.party?.subscriber?.mouthsToFeed
            render(view: 'generateShoppingList', model: [pslCO: pslCO, menuPlans: menuPlans, servings: mouthsToFeed])
        }
    }

    def createScaled = {PrintShoppingListCO pslCO ->
        pslCO.areServingsRequired = true
        pslCO.areWeeksRequired = (params.shoppingList == "GENERATE_WITH_WEEKS")
        if (pslCO.validate()) {
            Integer servings = pslCO.servings?.toInteger()
            ShoppingList shoppingList
            Boolean weeklyList
            switch (params?.shoppingList) {
                case "GENERATE_WITH_WEEKS":
                    weeklyList = true
                    shoppingList = shoppingListService.createShoppingList(pslCO, weeklyList)
                    break;
                case "GENERATE_WITHOUT_WEEKS":
                    weeklyList = false
                    shoppingList = shoppingListService.createShoppingList(pslCO, weeklyList)
                    break;
            }
            render(view: 'create', model: [shoppingList: shoppingList, weeklyList: weeklyList, servings: servings])
        } else {
            pslCO.errors.allErrors.each {
                println it
            }
            LoginCredential user = LoginCredential.currentUser
            List<MenuPlan> menuPlans = user?.party?.menuPlans as List
            Integer mouthsToFeed = user?.party?.subscriber?.mouthsToFeed
            render(view: 'generateShoppingList', model: [pslCO: pslCO, menuPlans: menuPlans, servings: mouthsToFeed])
        }
    }

    def show = {
        ShoppingList shoppingList = ShoppingList.get(params.id)
        render(view: 'show', model: [shoppingList: shoppingList, party: LoginCredential?.currentUser?.party])
    }

    def save = {
        List<String> weekList = params.weekList?.tokenize("[], ")
        String isWeeklyListString = params?.isWeeklyShoppingList?.toString()
        MenuPlan menuPlan = MenuPlan.get(params?.menuPlanId?.toLong())
        ShoppingList shoppingList = new ShoppingList()
        shoppingList.menuPlan = menuPlan
        shoppingList.name = params?.shoppingListName
        shoppingList.servings = params?.servings?.toInteger()
        shoppingList.isWeeklyShoppingList = (isWeeklyListString.contains('true')) ? true : false
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
        pslCO.isWeeklyShoppingList = shoppingList?.isWeeklyShoppingList
        pslCO.weeks = shoppingList?.weeklyShoppingLists*.weekIndex*.toString()
        List<MenuPlan> menuPlans = user?.party?.menuPlans as List
        Integer mouthsToFeed = user?.party?.subscriber?.mouthsToFeed
        render(view: 'generateShoppingList', model: [pslCO: pslCO, menuPlans: menuPlans, shoppingListId: shoppingList?.id, isWeeklyList: shoppingList?.isWeeklyShoppingList, servings: mouthsToFeed, shoppingList: shoppingList])
    }

    def modifyShoppingListOriginal = {PrintShoppingListCO pslCO ->
        pslCO.areServingsRequired = false
        pslCO.areWeeksRequired = (params.shoppingList == "GENERATE_WITH_WEEKS")
        if (pslCO.validate()) {
            ShoppingList shoppingListNew
            ShoppingList shoppingListOld = ShoppingList.get(params?.shoppingListId?.toLong())
            Boolean isWeeklyList
            switch (params?.shoppingList) {
                case "GENERATE_WITH_WEEKS":
                    isWeeklyList = true
                    shoppingListNew = shoppingListService.modifyShoppingList(pslCO, shoppingListOld, isWeeklyList)
                    break;
                case "GENERATE_WITHOUT_WEEKS":
                    isWeeklyList = false
                    shoppingListNew = shoppingListService.modifyShoppingList(pslCO, shoppingListOld, isWeeklyList)
                    break;
            }
            render(view: 'create', model: [shoppingList: shoppingListNew, shoppingListId: shoppingListOld?.id, isWeeklyList: isWeeklyList])
        }
        else {
            pslCO.errors.allErrors.each {
                println it
            }
            LoginCredential user = LoginCredential.currentUser
            List<MenuPlan> menuPlans = user?.party?.menuPlans as List
            Integer mouthsToFeed = user?.party?.subscriber?.mouthsToFeed
            render(view: 'generateShoppingList', model: [pslCO: pslCO, menuPlans: menuPlans, servings: mouthsToFeed])
        }
    }

    def modifyShoppingListScaled = {PrintShoppingListCO pslCO ->
        pslCO.areServingsRequired = true
        pslCO.areWeeksRequired = (params.shoppingList == "GENERATE_WITH_WEEKS")
        if (pslCO.validate()) {
            ShoppingList shoppingListNew
            ShoppingList shoppingListOld = ShoppingList.get(params?.shoppingListId?.toLong())
            Boolean isWeeklyList
            switch (params?.shoppingList) {
                case "GENERATE_WITH_WEEKS":
                    isWeeklyList = true
                    shoppingListNew = shoppingListService.modifyShoppingList(pslCO, shoppingListOld, isWeeklyList)
                    break;
                case "GENERATE_WITHOUT_WEEKS":
                    isWeeklyList = false
                    shoppingListNew = shoppingListService.modifyShoppingList(pslCO, shoppingListOld, isWeeklyList)
                    break;
            }
            shoppingListNew.servings = params?.servings?.toInteger()
            render(view: 'create', model: [shoppingList: shoppingListNew, shoppingListId: shoppingListOld?.id])
        }
        else {
            pslCO.errors.allErrors.each {
                println it
            }
            LoginCredential user = LoginCredential.currentUser
            List<MenuPlan> menuPlans = user?.party?.menuPlans as List
            Integer mouthsToFeed = user?.party?.subscriber?.mouthsToFeed
            render(view: 'generateShoppingList', model: [pslCO: pslCO, menuPlans: menuPlans, servings: mouthsToFeed])
        }
    }

    def update = {
        List<String> weekList = params.weekList?.tokenize("[], ")
        String isWeeklyListString = params?.isWeeklyShoppingList?.toString()
        ShoppingList shoppingList = ShoppingList.get(params?.shoppingListId?.toLong())
        shoppingList.servings = (params?.servings) ? params?.servings?.toInteger() : shoppingList?.servings
        shoppingList.isWeeklyShoppingList = (isWeeklyListString.contains('true')) ? true : false
        List<WeeklyShoppingList> weeklyShoppingLists = shoppingList.weeklyShoppingLists
        shoppingList.weeklyShoppingLists = []
        weeklyShoppingLists.each {WeeklyShoppingList weeklyShoppingList ->
            weeklyShoppingList.delete(flush: true)
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

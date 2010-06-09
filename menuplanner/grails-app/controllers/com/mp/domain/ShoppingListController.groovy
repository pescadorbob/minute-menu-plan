package com.mp.domain

class ShoppingListController {

    def index = { }

    def create = {

        MenuPlan menuPlan = MenuPlan.get(1)
        render(view: 'detailShoppingList', model: [menuPlan: menuPlan, weeks: params?.list('weeks')])
    }

    def printShoppingList = {
        User user = User.currentUser
        List<MenuPlan> menuPlans = MenuPlan.findAllByOwner(user)
        render(view: 'printShoppingList', model: [menuPlans: menuPlans])
    }
    def detailShoppingList = {
        MenuPlan menuPlan = MenuPlan.get(params?.menuPlanId)
        render(view: 'detailShoppingList', model: [menuPlan: menuPlan, weeks: params?.list('weeks')])
    }
}

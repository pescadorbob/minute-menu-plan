package com.mp.domain

class QuickFillController {

    def index = {
        redirect(action: 'quickFillAdmin')
    }

    def quickFillAdmin = {
        List<Category> categoryList = Category.list()
        QuickFill quickFill = params.addNew ? new QuickFill(mealItems: [new Meal(type: MealType.BREAKFAST), new Meal(type: MealType.LUNCH), new Meal(type: MealType.DINNER)]) : QuickFill.get(1)
        if (params.id) {
            quickFill = QuickFill.get(params.long("id"))
        }
        [categoryList: categoryList, itemTotal: Recipe.count(), quickFill: quickFill]
    }

    def saveAndUpdateQuickFill = {
        QuickFill quickFill = (params.id) ? QuickFill.get(params.long("id")) : new QuickFill()
        if (params.id) {
            def mealItems = quickFill.mealItems
            quickFill.mealItems = []
            mealItems*.delete()
        }
        Meal meal1 = new Meal(type: MealType.BREAKFAST, items: Item.getAll([params.meal.Breakfast].flatten()))
        Meal meal2 = new Meal(type: MealType.LUNCH, items: Item.getAll([params.meal.Lunch].flatten()))
        Meal meal3 = new Meal(type: MealType.DINNER, items: Item.getAll([params.meal.Dinner].flatten()))
        quickFill.name = params.quickFillName
        quickFill.addToMealItems(meal1)
        quickFill.addToMealItems(meal2)
        quickFill.addToMealItems(meal3)
        quickFill.s()
        flash.message = (params.id) ? "Record updated to database." : "Record saved to database."
        redirect(action: "quickFillAdmin")
    }
}

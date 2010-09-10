package com.mp.domain

import grails.converters.deep.JSON

class QuickFillController {

    def index = {
        redirect(action: 'quickFillAdmin')
    }

    def quickFillAdmin = {
        params.max = Math.min(params.max ? params.int('max') : 4, 100)

        QuickFill quickFill = new QuickFill(mealItems: [new Meal(type: MealType.DINNER), new Meal(type: MealType.DINNER), new Meal(type: MealType.DINNER), new Meal(type: MealType.DINNER),new Meal(type: MealType.BREAKFAST), new Meal(type: MealType.LUNCH)])
        if (params.id) {
            quickFill = QuickFill.get(params.long("id"))
        }
        List<Recipe> recipeList = Recipe.list(params)
        [categories: Category.list(), itemList: recipeList, itemTotal: Recipe.count(), quickFill: quickFill, quickFillList: QuickFill.list(max: params.max, offset: params?.offset), quickFillTotal: QuickFill.count()]
    }

    def saveAndUpdateQuickFill = {
        if(!params?.quickFillName){
            flash.message = "Please specify the name for the Quick Fill"
            redirect(action: "quickFillAdmin")
            return
        }
        QuickFill quickFill = (params.id) ? QuickFill.get(params.long("id")) : new QuickFill()
        if (params.id) {
            def mealItems = quickFill.mealItems
            quickFill.mealItems = []
            mealItems*.delete()
        }
        Meal meal1 = new Meal(type: MealType.DINNER, items: Item.getAll([params?.meal?.Dinner1]?.flatten()))
        Meal meal2 = new Meal(type: MealType.DINNER, items: Item.getAll([params?.meal?.Dinner2]?.flatten()))
        Meal meal3 = new Meal(type: MealType.DINNER, items: Item.getAll([params?.meal?.Dinner3]?.flatten()))
        Meal meal4 = new Meal(type: MealType.DINNER, items: Item.getAll([params?.meal?.Dinner4]?.flatten()))
        Meal meal5 = new Meal(type: MealType.BREAKFAST, items: Item.getAll([params?.meal?.Breakfast]?.flatten()))
        Meal meal6 = new Meal(type: MealType.LUNCH, items: Item.getAll([params?.meal?.Lunch]?.flatten()))
        quickFill.name = params.quickFillName
        quickFill.addToMealItems(meal1)
        quickFill.addToMealItems(meal2)
        quickFill.addToMealItems(meal3)
        quickFill.addToMealItems(meal4)
        quickFill.addToMealItems(meal5)
        quickFill.addToMealItems(meal6)
//        try{
            quickFill.s()
//        }catch(ex){
//
//        }
        flash.message = (params.id) ? "Record updated to database." : "Record saved to database."
        redirect(action: "quickFillAdmin")
    }

    def getQuickFill={
        QuickFill quickFill = QuickFill.get(params.long('id'))
        String jsonQuickFill="{'mealItems':["
        def mealCount=quickFill?.mealItems?.size()
        quickFill?.mealItems?.eachWithIndex{Meal meal,Integer j->
            jsonQuickFill+=" {'items':["
            def itemCount=meal?.items?.size()
            meal?.items?.eachWithIndex {Item item,Integer i->
                jsonQuickFill+="{'id':${item.id},'name':'${item.name}'}${((itemCount-1)==i)?'':','}"

            }
            jsonQuickFill+="]}${((mealCount-1)==j)?'':','}"
        }
        jsonQuickFill+="]}"
//        String quickFillJson=(quickFill as JSON).toString()
        render (text:""" "($jsonQuickFill)" """,contentType:'text/json') 
    }
}

package com.mp.domain

class MenuPlanController {

    def index = { }

    def show = {
        Integer listSize = Recipe.count()
        params.max = Math.min(params.max ? params.int('max') : 4, 150)
        List<Category> categoryList = Category.list()
        List<Recipe> recipeList = Recipe.list(params)

        MenuPlan menuPlan = MenuPlan.get(params.long("id"))
        render(view: 'show', model: [menuPlan: menuPlan, categoryList: categoryList, recipeList: recipeList, recipeTotal: Recipe.count()])
    }

    def create = {
        params.max = Math.min(params.max ? params.int('max') : 4, 150)
        MenuPlan menuPlan = new MenuPlan()
        4.times {
            Week week = new Week()
            7.times {
                Day day = new Day()
                week.addToDays(day)
            }
            menuPlan.addToWeeks(week)
        }
        List<Category> categoryList = Category.list()
        List<Recipe> recipeList = Recipe.list(params)
        render(view: 'create', model: [menuPlan: menuPlan, categoryList: categoryList, recipeList: recipeList, recipeTotal: Recipe.count()])
    }

    def edit = {
        Integer listSize = Recipe.count()
        params.max = Math.min(params.max ? params.int('max') : 4, 150)
        List<Category> categoryList = Category.list()
        List<Recipe> recipeList = Recipe.list(params)

        MenuPlan menuPlan = MenuPlan.get(params.long("id"))
        render(view: 'edit', model: [menuPlan: menuPlan, categoryList: categoryList, recipeList: recipeList, recipeTotal: Recipe.count()])
    }

    def saveAndUpdate = {
        MenuPlan menuPlan
        if (params.id) {
            menuPlan = MenuPlan.get(params.id)
            List<Week> weeks = menuPlan.weeks
            menuPlan.weeks = []
            weeks*.delete(flush: true)
        } else {
            menuPlan = new MenuPlan()
            menuPlan.name = System.currentTimeMillis()
        }
        (0..3).each {Integer weekIndex ->
            Week week = new Week()
            (0..6).each {Integer dayIndex ->
                Day day = new Day()
                MealType.values().each {MealType mealType ->
                    Meal meal = new Meal(type: mealType)
                    params.list("mealItems.${mealType}.week${weekIndex}.day${dayIndex}")?.each {
                        Item item = Item.get(it)
                        meal.addToItems(item)
                    }
                    day.addToMeals(meal)
                }
                week.addToDays(day)
            }
            menuPlan.addToWeeks(week)
        }
        menuPlan.s()
        redirect(action: 'show', id: menuPlan.id)
    }

    def search = {
        List<Recipe> results = []
        if (params?.q?.size() > 0) {

        }
        String query = params.query ?: params.list("q")?.join(" ")

        Integer total
        if (query && (query != 'null')) {
            def search = Recipe.search([reload: true, max: 4, offset: params.offset ?: 0]) {
                must(queryString(query))
            }
            results = search?.results
            total = search?.total
        } else {
            params.max = 4
            results = Recipe.list(params)
            total = Recipe.count()
        }

        render(template: '/menuPlan/searchResultMenuPlan', model: [recipeList: results, recipeTotal: total, query: query])
    }

}

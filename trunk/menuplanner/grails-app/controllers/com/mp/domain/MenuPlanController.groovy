package com.mp.domain

class MenuPlanController {

    def index = { }

    def show = {
        params.max = Math.min(params.max ? params.int('max') : 4, 150)
        List<Category> categoryList = Category.list()
        List<Recipe> recipeList = Recipe.list(params)

        MenuPlan menuPlan = MenuPlan.get(params.long("id"))
        render(view: 'show', model: [menuPlan: menuPlan, categoryList: categoryList, itemList: recipeList, itemTotal: Recipe.count()])
    }

    def create = {
        params.max = Math.min(params.max ? params.int('max') : 4, 150)
        MenuPlan menuPlan
        if (params.menuPlanId) {
            menuPlan = MenuPlan.get(params.long("menuPlanId"))
        } else {
            menuPlan = new MenuPlan()
            4.times {
                Week week = new Week()
                7.times {
                    Day day = new Day()
                    week.addToDays(day)
                }
                menuPlan.addToWeeks(week)
            }
        }
        List<Category> categoryList = Category.list()
        List<Recipe> recipeList = Recipe.list(params)
        render(view: 'create', model: [menuPlan: menuPlan, categoryList: categoryList, itemList: recipeList, itemTotal: Recipe.count()])
    }

    def edit = {
        params.max = Math.min(params.max ? params.int('max') : 4, 150)
        List<Category> categoryList = Category.list()
        List<Recipe> recipeList = Recipe.list(params)

        MenuPlan menuPlan = MenuPlan.get(params.long("id"))
        render(view: 'edit', model: [menuPlan: menuPlan, categoryList: categoryList, itemList: recipeList, itemTotal: Recipe.count()])
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
            menuPlan.owner = User.currentUser
        }
        menuPlan.name = params.menuPlan.name
        (0..3).each {Integer weekIndex ->
            Week week = new Week()
            (0..6).each {Integer dayIndex ->
                Day day = new Day()
                MealType.values().each {MealType mealType ->
                    Meal meal = new Meal(type: mealType)
                    String itemKey
                    if (mealType in [MealType.BREAKFAST, MealType.LUNCH]) {
                        itemKey = "mealItems.${mealType}.week0.day${dayIndex}"
                    } else {
                        itemKey = "mealItems.${mealType}.week${weekIndex}.day${dayIndex}"
                    }
                    params.list(itemKey)?.each {
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
        menuPlan.owner.addToMenuPlans(menuPlan)
        menuPlan.owner.s()
        redirect(action: 'show', id: menuPlan.id)
    }

    def search = {
        String searchDomainName = (params.searchByDomainName != 'null') ? ('com.mp.domain.' + params.searchByDomainName) : ('com.mp.domain.Recipe')
        List<String> allQueries = []
        params.query = (params.query == 'null') ? '' : params.query
        params?.list("query")?.eachWithIndex {String myQ, Integer index ->
            allQueries.push(myQ)
            if (!(myQ.contains(':'))) {
                allQueries[index] = '*' + myQ + '*'
            }
        }
        List<Recipe> results = []
        String query = allQueries?.join(" ")?.tokenize(", ")?.join(" ")
        if(query.startsWith('[')){
            query = query.substring(1, query.length()-1)
        }
        Integer total

        if (query && (query != 'null')) {
            Class clazz = grailsApplication.getClassForName(searchDomainName)
            def searchList = clazz.search([reload: true, max: 4, offset: params.offset ? params.long('offset') : 0]) {
                must(queryString(query))
            }
            results = searchList?.results
            total = searchList?.total
        } else {
            params.max = 4
            results = Item.list(params)
            total = Item.count()
        }
        render(template: '/menuPlan/searchResultMenuPlan', model: [itemList: results, itemTotal: total, query: params.query])
    }

    def printerFriendlyMenuPlan = {
        MenuPlan menuPlan = MenuPlan.get(params.long("id"))
        render(view: 'printMenuPlan', model: [menuPlan: menuPlan])
    }
}

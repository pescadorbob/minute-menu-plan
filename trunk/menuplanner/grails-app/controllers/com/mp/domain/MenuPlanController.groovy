package com.mp.domain

import static com.mp.MenuConstants.*
import org.apache.lucene.document.NumberTools

class MenuPlanController {

    def recipeService
    def index = { }

    def show = {
        params.max = Math.min(params.max ? params.int('max') : 4, 150)
        List<Recipe> recipeList = Recipe.list(params)
        MenuPlan menuPlan = MenuPlan.get(params.long("id"))
        List<SubCategory> subCategories = (Recipe.list()*.subCategories)?.flatten()?.unique {it.id}?.sort {it.name}
        List<Category> categories = (subCategories*.category)?.flatten()?.unique {it.id}?.sort {it.name}
        render(view: 'show', model: [menuPlan: menuPlan, categories: categories, subCategories: subCategories, itemList: recipeList, itemTotal: Recipe.count(), openInNewWindow: false])
    }

    def create = {
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
        params.max = 4
        List<Recipe> results = []
        results = recipeService.getFilteredRecipeList(4, 0)
        Integer total = recipeService.getFilteredRecipeCount()
        List<SubCategory> subCategories = (Recipe.list()*.subCategories)?.flatten()?.unique {it.id}?.sort {it.name}
        List<Category> categories = (subCategories*.category)?.flatten()?.unique {it.id}?.sort {it.name}
        render(view: 'create', model: [menuPlan: menuPlan, categories: categories, subCategories: subCategories, itemList: results, itemTotal: total, openInNewWindow: true])
    }

    def edit = {
        params.max = 4
        List<Recipe> results = []
        results = recipeService.getFilteredRecipeList(4, 0)
        Integer total = recipeService.getFilteredRecipeCount()
        MenuPlan menuPlan = MenuPlan.get(params.long("id"))
        List<SubCategory> subCategories = (Recipe.list()*.subCategories)?.flatten()?.unique {it.id}?.sort {it.name}
        List<Category> categories = (subCategories*.category)?.flatten()?.unique {it.id}?.sort {it.name}
        render(view: 'edit', model: [menuPlan: menuPlan, categories: categories, subCategories: subCategories, itemList: results, itemTotal: total, openInNewWindow: true])
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
            menuPlan.owner = LoginCredential.currentUser.party
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
        boolean openInNewWindow = (params?.openInNewWindow == 'true') ? true : false
        Long offset = params.offset ? params.long('offset') : 0
        List<Item> results = []
        Party currentUser = LoginCredential.currentUser.party
        Long currentUserId = currentUser?.id
        List<String> allQueries = []
        List<String> subCategoriesString = []
        String subQueryString
        Integer total
        def searchList
        String searchKeyword = ''

        if (!params.query || (params.query == 'null')) {
            params.query = ''
        }
        if ((params.query instanceof String) && params.query.startsWith('[')) {
            params.query = params.query.substring(1, params.query.length() - 1).tokenize(',')
        }
        List queryList = params.list('query').flatten()
        queryList = queryList.findAll {it?.trim()}

        queryList?.eachWithIndex {String myQ, Integer index ->
            if (myQ.contains('subCategoriesString')) {
                String categoryString = myQ.split(":").flatten().get(1)
                if (categoryString.endsWith(']')) {
                    categoryString = categoryString.substring(0, categoryString.length() - 1)
                }
                subCategoriesString.add(categoryString)

            } else {
                allQueries.push(myQ)
            }
        }
        if (subCategoriesString) {
            subQueryString = subCategoriesString.join('" OR "')
            subQueryString = '"' + subQueryString + '"'
            allQueries.push('subCategoriesString:' + subQueryString)
        }

        String keyword = allQueries.find {!(it.contains(':'))}
        allQueries = allQueries.findAll {(it.contains(':'))}
        if (keyword) {
            allQueries.add("*${keyword}*")
        }
        String query = allQueries?.join(" ")?.tokenize(", ")?.join(" ")
        if (query.startsWith('[')) {
            query = query.substring(1, query.length() - 1)
        }
        query += " (shareWithCommunity:true OR contributorsString:${NumberTools.longToString(currentUserId)})"
        if (!currentUser.showAlcoholicContent) {query += "  isAlcoholic:false"}

        if (params.searchByDomainName == 'Item') {
            searchList = Item.search([reload: true, max: 4, offset: offset]) {
                must(queryString(query))
            }
            results = searchList?.results
            total = searchList?.total
            if (!results && keyword) {
                String newQuery = recipeService.fuzzySearchQuery(query, keyword)
                searchList = Item.search([reload: true, max: 4, offset: offset]) {
                    must(queryString(newQuery))
                }
                results = searchList?.results
                total = searchList?.total
            }

        } else {
            searchList = Recipe.search([reload: true, max: 4, offset: offset]) {
                must(queryString(query))
            }
            results = searchList?.results
            total = searchList?.total
            if (!results && keyword) {
                String newQuery = recipeService.fuzzySearchQuery(query, keyword)
                searchList = Recipe.search([reload: true, max: 4, offset: offset]) {
                    must(queryString(newQuery))
                }
                results = searchList?.results
                total = searchList?.total
            }
        }
        render(template: '/menuPlan/searchResultMenuPlan', model: [itemList: results, itemTotal: total, query: queryList, openInNewWindow: openInNewWindow])
    }

    def printerFriendlyMenuPlan = {
        MenuPlan menuPlan = MenuPlan.get(params.long("id"))
        render(view: 'printMenuPlan', model: [menuPlan: menuPlan])
    }
}

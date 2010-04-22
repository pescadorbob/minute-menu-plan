package com.mp.domain

class MenuPlanController {

    def index = { }

    def show = {
        Integer listSize = Recipe.count()
        params.max = Math.min(params.max ? params.int('max') : 4, 150)
        List<Category> categoryList = Category.list()
        List<Recipe> recipeList = Recipe.list(params)

        MenuPlan menuPlan = MenuPlan.get(params.long("id"))
        render(view: 'show', model: [weeks: menuPlan.weeks, categoryList:categoryList, recipeList:recipeList, recipeTotal: Recipe.count()])
    }

    def search = {
        List<Recipe> results = []
        if(params?.q?.size()>0){

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
            params.max=4
            results = Recipe.list(params)
            total = Recipe.count()
        }

        render(template: '/menuPlan/searchResult', model: [recipeList: results, recipeTotal: total, query: query])
    }
    
}

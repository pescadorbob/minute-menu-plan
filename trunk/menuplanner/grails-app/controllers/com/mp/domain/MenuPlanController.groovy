package com.mp.domain

class MenuPlanController {

    def index = { }

    def show = {
        MenuPlan menuPlan = MenuPlan.get(params.long("id"))
        render(view: 'show', model: [weeks: menuPlan.weeks])
    }
}

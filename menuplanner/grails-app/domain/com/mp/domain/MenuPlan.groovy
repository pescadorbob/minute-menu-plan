package com.mp.domain

class MenuPlan {

    String name
    List<Week> weeks = []

    static hasMany = [weeks: Week]
    static belongsTo = [owner: User]

    static constraints = {
    }

    public MenuPlan shallowClone() {
        MenuPlan menuPlan = new MenuPlan()
        menuPlan.name = this.name
        menuPlan.weeks = []
        this.weeks.each {Week week ->
            menuPlan.weeks += week.shallowClone(menuPlan)
        }
        return menuPlan
    }
}

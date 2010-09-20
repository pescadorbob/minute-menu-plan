package com.mp.domain
class PrintShoppingListCO {
    String name
    String menuPlanId
    List<String> weeks
    String servings
    Boolean isWeeklyShoppingList = false
    Boolean areServingsRequired
    Boolean areWeeksRequired

    static constraints = {
        name(blank: false, nullable: false)
        weeks(validator: {val, obj ->
            if ((obj.areWeeksRequired) && (!val)) {
                return 'printShoppingListCO.weeks.nullable.error.weeks'
            }
        })
        menuPlanId(nullable: false, blank: false)
        servings(matches: /[0-9\s]*/, validator: {val, obj ->
            if ((obj.areServingsRequired) && (!val)) {
                return 'printShoppingListCO.servings.blank.servings'
            }
            if ((obj.areServingsRequired) && (!val instanceof Float)) {
                return 'printShoppingListCO.servings.matches.error.servings'
            }
        })
    }
}

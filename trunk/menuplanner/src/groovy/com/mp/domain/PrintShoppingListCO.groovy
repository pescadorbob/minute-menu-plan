package com.mp.domain
class PrintShoppingListCO {
    String name
    String menuPlanId
    List<String> weeks
    String servings

    static constraints = {
        name(blank: false, nullable: false)
        weeks(validator: {obj, val ->
            if (!val) {
                return 'default.blank.message'
            }
        })
        menuPlanId(nullable: false, blank: false)
        servings(blank: false, matches: /[0-9\s]*/)
    }
}
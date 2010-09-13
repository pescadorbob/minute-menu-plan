package com.mp.domain

class Week {

    List<Day> days = []

    static belongsTo = [menuPlan: MenuPlan]
    static hasMany = [days: Day]

    static mapping = {
        days casade: 'all-delete-orphan'
    }

    static constraints = {
    }

    boolean equals(o) {
        if (this.is(o)) return true;
        if (!o || getClass() != o.class) return false;
        Week week = (Week) o;
        if (!id.equals(week.id)) return false;
        return true;
    }

    List<Item> getRecipes(){
        List<Item> items = []
        days?.each{Day day->
            day.meals?.each{Meal meal ->
                meal.items?.each{Item item ->
                 if(item.instanceOf(Recipe.class)){
                     items.add(item)
                 }
                }
            }
        }
        items = items?.unique{it.id}
        return items
    }

}

package com.mp.domain

class Meal {

    MealType type
    List<Item> items = []

    static belongsTo = [day: Day]
    static hasMany = [items: Item]

    static constraints = {
        type(unique: 'day')
    }

    public Meal shallowClone(Day day){
        Meal meal = new Meal(day: day, type: this.type)
        meal.items=[]
        this.items.each{Item item->
            meal.items+=item.shallowClone()
        }
        return meal
    }

}

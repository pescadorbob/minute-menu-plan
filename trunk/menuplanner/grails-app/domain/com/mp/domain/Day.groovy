package com.mp.domain

class Day {

    List<Meal> meals = []

    static belongsTo = [week: Week]
    static hasMany = [meals: Meal]

    static transients = ['mealByType', 'breakfast', 'lunch', 'dinner', 'supper']


    List<Item> getMealByType(MealType mealType) {
        List<Item> items = []
        Meal meal
        if (meals) {
            meal = meals.find {it.type == mealType}
            if (meal) {
                items = meal.items
            }
        }
        return items
    }

    List<Item> getBreakfast() {
        return getMealByType(MealType.BREAKFAST)
    }

    List<Item> getLunch() {
        return getMealByType(MealType.LUNCH)
    }

    List<Item> getDinner() {
        return getMealByType(MealType.DINNER)
    }

    List<Item> getSupper() {
        return getMealByType(MealType.SUPPER)
    }

    static constraints = {
    }

    public Day shallowClone(Week week){
        Day day = new Day(week: week)
        day.meals =[]
        this.meals.each{Meal meal->
            day.meals +=meal.shallowClone(day)
        }
        return day
    }
}
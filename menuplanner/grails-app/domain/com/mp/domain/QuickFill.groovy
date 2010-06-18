package com.mp.domain

class QuickFill {

    String name
    List<Meal> mealItems = []

    static hasMany = [mealItems: Meal]

    static transients = ['week1', 'week2', 'week3', 'week4', 'breakfast', 'lunch']

    Meal getWeek1(){
        return getDinnerForWeek(1)
    }

    Meal getWeek2(){
        return getDinnerForWeek(2)
    }

    Meal getWeek3(){
        return getDinnerForWeek(3)
    }

    Meal getWeek4(){
        return getDinnerForWeek(4)
    }

    Meal getDinnerForWeek(Integer week){
        List<Meal> dinnerMeals = mealItems.findAll{it.type == MealType.DINNER}
        return dinnerMeals[(week-1)]
    }

    Meal getBreakfast(){
        return mealItems.find{it.type == MealType.BREAKFAST}
    }

    Meal getLunch(){
        return mealItems.find{it.type == MealType.LUNCH}
    }

    static constraints = {
        name(unique: true)
    }
}
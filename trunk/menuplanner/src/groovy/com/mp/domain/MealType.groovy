package com.mp.domain

public enum MealType {

    BREAKFAST('Breakfast'),
    LUNCH('Lunch'),
    DINNER('Dinner'),
    SUPPER('Supper')

    String name

    MealType(String name){
        this.name = name
    }

    String toString(){
        return name
    }

}
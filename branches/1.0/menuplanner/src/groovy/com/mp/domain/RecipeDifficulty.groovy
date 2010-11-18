package com.mp.domain

public enum RecipeDifficulty {

    EASY("Easy"),
    MEDIUM("Medium"), 
    HARD("Hard")

    String name

    RecipeDifficulty(String name){
        this.name = name
    }

    public static List<RecipeDifficulty> list(){
        return [EASY, MEDIUM, HARD] 
    }

    String toString(){
        return name
    }

}
package com.mp.domain

class Person extends Party{

    static mapping = {
        tablePerHierarchy false
    }

    static constraints = {
    }

    String toString(){
        return name
    }
}

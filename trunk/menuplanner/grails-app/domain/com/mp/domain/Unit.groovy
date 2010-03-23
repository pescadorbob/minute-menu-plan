package com.mp.domain

class Unit {

    String name
    String symbol
    String definition

    String toString() {
        name
    }

    static constraints = {
        name(unique: true, nullable:false)
    }
    static mapping = {
        tablePerHierarchy false
    }
}
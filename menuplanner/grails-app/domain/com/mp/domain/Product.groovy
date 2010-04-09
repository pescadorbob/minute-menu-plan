package com.mp.domain

class Product extends Item{

    Boolean isVisible = false

    String toString() {
        return name
    }

    static constraints = {
        name(unique: true, nullable:false)
    }

    static mapping = {
        tablePerHierarchy false
    }
}

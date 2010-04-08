package com.mp.domain

class Product {

    String name
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

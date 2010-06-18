package com.mp.domain

class Product extends Item{
    static searchable = true

    Boolean isVisible = false
    Aisle suggestedAisle

    String toString() {
        return name
    }

    static constraints = {
        name(unique: true, nullable:false)
        suggestedAisle(nullable: true)
    }

    static mapping = {
        tablePerHierarchy false
    }
}

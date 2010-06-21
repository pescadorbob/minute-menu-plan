package com.mp.domain

class Item {
    static searchable = true

    String name
    Aisle suggestedAisle

    String toString() {
        return name
    }

    static constraints = {
        suggestedAisle(nullable: true)
    }

    static mapping = {
        tablePerHierarchy false
        sort 'name'
    }
}

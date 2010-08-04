package com.mp.domain

class SubCategory {

    String name
    Category category

    static belongsTo = [category : Category]

    static constraints = {
    }

    String toString() {
        return name
    }
}

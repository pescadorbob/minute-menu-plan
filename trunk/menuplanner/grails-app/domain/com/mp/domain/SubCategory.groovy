package com.mp.domain

class SubCategory {

    String name
    Category category

    static belongsTo = [category : Category]

    static constraints = {
        name (unique: 'category')
    }

    String toString() {
        return name
    }

    static mapping = {
        sort 'name'
    }

}

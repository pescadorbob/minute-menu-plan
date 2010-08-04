package com.mp.domain

class Category {

    String name
    Set<SubCategory> subCategories = []

    static hasMany = [subCategories: SubCategory]

    String toString() {
        return name
    }

    static constraints = {
    }
}

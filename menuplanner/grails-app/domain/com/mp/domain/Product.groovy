package com.mp.domain

class Product {

    String name

    String toString() {
        return name
    }

    static constraints = {
        name(unique: true)
    }
}

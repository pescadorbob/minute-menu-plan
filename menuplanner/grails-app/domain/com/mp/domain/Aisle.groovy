package com.mp.domain

class Aisle {

    String name

    String toString() {
        return name
    }

    static constraints = {
        name(unique: true)
    }
}

package com.mp.domain

class Nutrient {

    String name
    Unit preferredUnit

    static constraints = {
    }

    String toString() {
        return name
    }
}

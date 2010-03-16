package com.mp.domain

class Metric {

    String name
    String symbol
    String definition

    String toString() {
        name
    }

    static constraints = {
        name(unique: true)
    }
}

package com.mp.domain

class MeasuredProduct extends Product{

    static hasMany = [possibleUnits: Unit]
    Unit preferredUnit

    String toString() {
        return name
    }

    static constraints = {
        name()
        preferredUnit()
    }

    static mapping = {
        tablePerHierarchy false
    }
}

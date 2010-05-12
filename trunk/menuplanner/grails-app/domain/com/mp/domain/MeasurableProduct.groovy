package com.mp.domain

class MeasurableProduct extends Product{
    static searchable = true
              
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

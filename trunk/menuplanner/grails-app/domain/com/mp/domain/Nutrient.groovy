package com.mp.domain

class Nutrient {

    String name
    Unit preferredUnit
    
    static hasMany = [possibleUnits: Unit]
    static constraints = {
    }

    String toString() {
        return name
    }
}

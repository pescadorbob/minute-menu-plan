package com.mp.domain

class Unit extends Metric{

    static hasMany = [systemOfUnits:SystemOfUnit]
    static belongsTo = SystemOfUnit

    static constraints = {
    }

    static mapping = {
        tablePerHierarchy false
    }
}

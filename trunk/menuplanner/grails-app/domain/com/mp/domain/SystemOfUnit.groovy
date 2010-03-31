package com.mp.domain

class SystemOfUnit {

    String systemName
    String standardizationBody
    static hasMany = [units: Unit]

    static constraints = {
        units(nullable: true)
    }
}

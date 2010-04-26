package com.mp.domain
import static com.mp.MenuConstants.*

class Unit extends Metric{

    static hasMany = [systemOfUnits:SystemOfUnit]
    static belongsTo = SystemOfUnit

    static constraints = {
    }

    static mapping = {
        tablePerHierarchy false
    }

    Boolean belongsToMetricSystem(){
        return (systemOfUnits? (systemOfUnits.any{it.systemName == SYSTEM_OF_UNIT_METRIC}) : false)
    }

    Boolean belongsToUsaSystem(){
        return (systemOfUnits? (systemOfUnits.any{it.systemName == SYSTEM_OF_UNIT_USA}) : false)
    }
}
package com.mp.domain

class Metric {

    String name
    String symbol
    String definition
    MetricType metricType
    Boolean isWeightUnit = false
    
    String toString() {
        return "${name}"
    }

    static constraints = {
        name(unique: true, nullable:false)
    }
    static mapping = {
        tablePerHierarchy false
    }
}

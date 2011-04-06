package com.mp.domain

class Metric {

    String name
    String symbol
    String small
    String definition
    MetricType metricType

    String toString() {
        return "${name}"
    }

    static constraints = {
        name(unique: true, nullable:false)
        small(nullable:true)
    }
    static mapping = {
        tablePerHierarchy false
    }

}

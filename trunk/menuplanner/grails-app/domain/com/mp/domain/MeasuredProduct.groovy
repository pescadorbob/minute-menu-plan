package com.mp.domain

class MeasuredProduct extends Product{

    static hasMany = [possibleMetrics: Metric]
    Metric preferredMetric

    String toString() {
        return name
    }

    static constraints = {
        name()
        preferredMetric()
    }

    static mapping = {
        tablePerHierarchy false
    }
}

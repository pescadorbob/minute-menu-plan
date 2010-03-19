package com.mp.domain

class MeasuredProduct {

    static hasMany = [possibleMetrics: Metric]
    Product product
    Metric preferredMetric

    String toString() {
        return product
    }

    static constraints = {
        product()
        preferredMetric()
    }

    static mapping = {
        tablePerHierarchy false
    }
}

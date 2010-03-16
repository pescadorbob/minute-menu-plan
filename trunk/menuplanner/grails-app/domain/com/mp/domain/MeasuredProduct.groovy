package com.mp.domain

class MeasuredProduct extends Product {

    static hasMany = [possibleMetrics: Metric]
    Metric preferredMetric
    byte[] image

    static constraints = {
        image(maxSize: 6 * 1024 * 1024, nullable: true)
    }
}

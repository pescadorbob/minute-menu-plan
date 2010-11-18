package com.mp.domain

class SystemOfUnit {

    String systemName
    String standardizationBody
    static hasMany = [units: Unit]

    static constraints = {
        units(nullable: true)
    }

     static mapping = {
        cache true
    }
    
    List<Unit> getTimeUnits() {
        return ((units) ? (units.findAll {it.metricType == MetricType.TIME} as List) : [])
    }

    List<Unit> getMetricUnits() {
        return ((units) ? (units.findAll {it.metricType == MetricType.METRIC} as List) : [])
    }

    List<Unit> getSortedMetricUnits() {
        return ((units) ? (units.findAll {it.metricType == MetricType.METRIC} as List) : [])
    }

    List<Unit> getRecipeUnits() {
        List<Unit> recipeUnits = []
        recipeUnits = ((units) ? (units.findAll {it.metricType != MetricType.TIME} as List) : [])
        return recipeUnits
    }
}

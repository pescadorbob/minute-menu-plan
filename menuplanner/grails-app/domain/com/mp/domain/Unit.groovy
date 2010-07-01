package com.mp.domain
import static com.mp.MenuConstants.*

class Unit extends Metric{

    static hasMany = [systemOfUnits:SystemOfUnit]
    static belongsTo = SystemOfUnit
    User user

    static constraints = {
        user(nullable: true)
    }

    static mapping = {
        tablePerHierarchy false
    }

    public static List<Unit> getSortedMetricUnits(){
        List<Unit> units = StandardConversion.listOrderByConversionFactor()?.findAll{it.sourceUnit.metricType == MetricType.METRIC}*.sourceUnit
        units = units.findAll{((it.user == null) || (it.user == User.currentUser))}
        return units
    }

    Boolean belongsToMetricSystem(){
        return (systemOfUnits? (systemOfUnits.any{it.systemName == SYSTEM_OF_UNIT_METRIC}) : false)
    }

    Boolean belongsToUsaSystem(){
        return (systemOfUnits? (systemOfUnits.any{it.systemName == SYSTEM_OF_UNIT_USA}) : false)
    }

    public static List<Unit> listForCurrentUser(){
        User currentUser = User.currentUser
        List<Unit> units = currentUser ? Unit.findAllByUserOrUserIsNull(currentUser) : []
        return units
    }

}
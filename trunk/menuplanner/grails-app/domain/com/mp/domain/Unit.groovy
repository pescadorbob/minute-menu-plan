package com.mp.domain
import static com.mp.MenuConstants.*

class Unit extends Metric{

    static hasMany = [systemOfUnits:SystemOfUnit]
    static belongsTo = SystemOfUnit
    Party party

    static constraints = {
        party(nullable: true)
    }

    static mapping = {
        tablePerHierarchy false
    }

    public static List<Unit> getSortedMetricUnits(){
        List<Unit> units = StandardConversion.listOrderByConversionFactor()?.findAll{it.sourceUnit.metricType == MetricType.METRIC}*.sourceUnit
        units = units.findAll{((it.party == null) || (it.party == LoginCredential.currentUser?.party))}
        return units
    }

    Boolean belongsToMetricSystem(){
        return (systemOfUnits? (systemOfUnits.any{it.systemName == SYSTEM_OF_UNIT_METRIC}) : false)
    }

    Boolean belongsToUsaSystem(){
        return (systemOfUnits? (systemOfUnits.any{it.systemName == SYSTEM_OF_UNIT_USA}) : false)
    }

    public static List<Unit> listForCurrentUser(){
        LoginCredential currentUser = LoginCredential.currentUser
        List<Unit> units = currentUser ? Unit.findAllByPartyOrPartyIsNull(currentUser.party) : []
        return units
    }

}
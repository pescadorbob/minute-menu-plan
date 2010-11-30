package com.mp.domain
import static com.mp.MenuConstants.*
import com.mp.domain.party.Party
import com.mp.tools.UserTools

class Unit extends Metric{

    static hasMany = [systemOfUnits:SystemOfUnit]
    static belongsTo = SystemOfUnit
    Boolean isWeightUnit = false
    Boolean isConvertible = false
    Party party

    static constraints = {
        party(nullable: true)
    }

    static mapping = {
        tablePerHierarchy false
        systemOfUnits fetch: 'join'
        cache true
    }

    public static List<Unit> getSortedMetricUnits(){
        List<Unit> units = Unit.list().findAll{(it.metricType == MetricType.METRIC) && ((it.party == null) || (it.party == UserTools.currentUser?.party))}
        return units.sort{it.name}
    }

    Boolean belongsToMetricSystem(){
        return (systemOfUnits? (systemOfUnits.any{it.systemName == SYSTEM_OF_UNIT_METRIC}) : false)
    }

    Boolean belongsToUsaSystem(){
        return (systemOfUnits? (systemOfUnits.any{it.systemName == SYSTEM_OF_UNIT_USA}) : false)
    }

    public static List<Unit> listForCurrentUser(){
        LoginCredential currentUser = UserTools.currentUser
        List<Unit> units = currentUser ? Unit.findAllByPartyOrPartyIsNull(currentUser.party) : []
        return units
    }

}

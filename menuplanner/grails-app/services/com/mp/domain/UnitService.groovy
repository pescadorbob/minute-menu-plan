package com.mp.domain

import static com.mp.MenuConstants.*
import com.mp.tools.UserTools

class UnitService {

    boolean transactional = true

    public Unit createNewUnit(String unitName,String unitSymbol,Long unitId,Long systemOfUnit,Float conversionFactor){
        Unit unit = new Unit()
        unit.name=unitName
        unit.symbol=unitSymbol
        unit.metricType=MetricType.METRIC
        unit.addToSystemOfUnits(SystemOfUnit.get(systemOfUnit))
        unit.definition="Subscriber Created Unit"
        unit.party=UserTools.currentUser?.party
        unit.s()
        Unit sourceUnit = Unit.findById(unitId)
        Unit targetUnit = Unit.findByName(UNIT_MILLI_LITRE)
        conversionFactor = conversionFactor * StandardConversion.findBySourceUnit(sourceUnit).conversionFactor
        new StandardConversion(sourceUnit: unit, targetUnit: targetUnit, conversionFactor: conversionFactor).s()
        return unit
    }
}

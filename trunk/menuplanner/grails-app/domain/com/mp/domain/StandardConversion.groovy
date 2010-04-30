package com.mp.domain

import org.apache.commons.math.fraction.Fraction
import java.text.FieldPosition
import org.apache.commons.math.fraction.ProperFractionFormat

class StandardConversion {
    Unit sourceUnit
    Unit targetUnit
    Double conversionFactor

    public static Quantity getMetricQuantity(BigDecimal amount, Unit displayUnit) {
        Quantity result = new Quantity()
        result.value = new Fraction(amount?.toString())?.floatValue() * StandardConversion.findByTargetUnit(displayUnit)?.conversionFactor
        result.savedUnit = StandardConversion.findByTargetUnit(displayUnit)?.sourceUnit
        result.unit = displayUnit
        return result
    }

    public static String getUsaString(BigDecimal metricValue, Unit unit) {
        if(metricValue && unit){
            BigDecimal conversionFactor = StandardConversion.findByTargetUnit(unit)?.conversionFactor
            metricValue = metricValue / conversionFactor
            String result = new Fraction(metricValue).myFormatUsingProperFractionFormat()
            return result
        }
        return null
    }

    static constraints = {
    }
}

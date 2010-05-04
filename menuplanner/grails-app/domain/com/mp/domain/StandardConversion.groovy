package com.mp.domain

import org.apache.commons.math.fraction.Fraction
import java.text.FieldPosition
import org.apache.commons.math.fraction.ProperFractionFormat

class StandardConversion {
    Unit sourceUnit
    Unit targetUnit
    Double conversionFactor

    public static Quantity getMetricQuantity(String amountFraction, Unit displayUnit) {
        BigDecimal amount = new Fraction(amountFraction).floatValue()
        Quantity result = new Quantity()
        if(displayUnit && amount){
            result.savedUnit = StandardConversion.findByTargetUnit(displayUnit)?.sourceUnit
            result.unit = displayUnit
            result.value = amount * StandardConversion.findByTargetUnit(displayUnit)?.conversionFactor
        }else if(amount){
            result.value = amount
        }
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

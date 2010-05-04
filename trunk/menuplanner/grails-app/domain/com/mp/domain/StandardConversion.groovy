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
        if (displayUnit && amount) {
            if (displayUnit.belongsToMetricSystem()) {
                result.savedUnit = displayUnit
                result.unit = displayUnit
                result.value = amount
            } else {
                result.savedUnit = StandardConversion.findByTargetUnit(displayUnit)?.sourceUnit
                result.unit = displayUnit
                result.value = amount * StandardConversion.findByTargetUnit(displayUnit)?.conversionFactor
            }
        } else if (amount) {
            result.value = amount
        }
        return result
    }

    //TODO: Send an object of quantity
    public static String getUsaString(BigDecimal metricValue, Unit unit) {
        if (metricValue && unit) {
            String result
            if (unit.belongsToUsaSystem()) {
                result = new Fraction(metricValue).myFormatUsingProperFractionFormat()
            } else {
                BigDecimal conversionFactor = StandardConversion.findByTargetUnit(unit)?.conversionFactor
                metricValue = metricValue / conversionFactor
                result = new Fraction(metricValue).myFormatUsingProperFractionFormat()
            }
            return result
        }
        return null
    }

    static constraints = {
    }
}

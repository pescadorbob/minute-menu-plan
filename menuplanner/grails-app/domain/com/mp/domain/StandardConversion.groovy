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
                result.savedUnit = StandardConversion.findBySourceUnit(displayUnit)?.targetUnit
                result.unit = displayUnit
                result.value = amount * StandardConversion.findBySourceUnit(displayUnit)?.conversionFactor
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
            if (unit.belongsToMetricSystem()) {
                result = new Fraction(metricValue).myFormatUsingProperFractionFormat()
            } else {
                BigDecimal conversionFactor = StandardConversion.findBySourceUnit(unit)?.conversionFactor
                if (conversionFactor) {
                    metricValue = metricValue / conversionFactor
                    result = new Fraction(metricValue).myFormatUsingProperFractionFormat()
                } else {
                    result = ''
                }
            }
            return result
        }
        return null
    }

    static constraints = {
    }
}

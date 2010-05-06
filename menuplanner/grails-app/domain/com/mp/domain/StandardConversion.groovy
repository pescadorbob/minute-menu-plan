package com.mp.domain

import org.apache.commons.math.fraction.Fraction
import java.text.FieldPosition
import org.apache.commons.math.fraction.ProperFractionFormat
import static com.mp.MenuConstants.*

class StandardConversion {
    Unit sourceUnit
    Unit targetUnit
    Double conversionFactor

    public static Quantity getUsaQuantity(Quantity sourceQuantity) {
        Quantity result = new Quantity()
        return result
    }

    public static Quantity getMetricQuantity(String amountFraction, Unit displayUnit) {
        BigDecimal amount = new Fraction(amountFraction).floatValue()
        Quantity result = new Quantity()
        if (displayUnit && amount) {
            StandardConversion standardConversion = StandardConversion.findBySourceUnit(displayUnit)
            if (standardConversion) {
                result.savedUnit = standardConversion?.targetUnit
                result.unit = displayUnit
                result.value = amount * standardConversion?.conversionFactor
            } else {
                result.savedUnit = displayUnit
                result.unit = displayUnit
                result.value = amount
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
                StandardConversion.withNewSession {
                    BigDecimal conversionFactor = StandardConversion.findBySourceUnit(unit)?.conversionFactor
                    if (conversionFactor) {
                        metricValue = metricValue / conversionFactor
                        result = new Fraction(metricValue).myFormatUsingProperFractionFormat()
                    } else {
                        result = ''
                    }
                }
            }
            return result
        }
        return null
    }

    static constraints = {
    }
}

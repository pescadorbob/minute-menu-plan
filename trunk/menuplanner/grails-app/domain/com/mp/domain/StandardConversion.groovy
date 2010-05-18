package com.mp.domain

import org.apache.commons.math.fraction.Fraction
import java.text.FieldPosition
import org.apache.commons.math.fraction.ProperFractionFormat
import static com.mp.MenuConstants.*

class StandardConversion {
    Unit sourceUnit
    Unit targetUnit
    Double conversionFactor

    public static Quantity getMetricQuantity(String amountFraction, Unit displayUnit) {
        Quantity result = new Quantity()
        Float amount
        if (amountFraction != null) {
            amount = new Fraction(amountFraction)?.floatValue()
        }
        if (displayUnit && (amount != null)) {
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
        } else if (amount != null) {
            result.value = amount
        }
        return result
    }

    public static String getUsaQuantityString(Quantity sourceQuantity) {
        Float metricValue = sourceQuantity?.value
        Unit unit = sourceQuantity?.unit
        String result
        if ((metricValue != null) && unit) {
            StandardConversion standardConversion
            StandardConversion.withNewSession {
                if (unit) {
                    standardConversion = StandardConversion.findBySourceUnit(unit)
                }
                if (standardConversion) {
                    Float conversionFactor = StandardConversion.findBySourceUnit(unit)?.conversionFactor
                    metricValue = metricValue / conversionFactor
                    result = new Fraction(metricValue).myFormatUsingProperFractionFormat()
                } else {
                    result = new Fraction(metricValue).myFormatUsingProperFractionFormat()
                }
            }
        } else if ((metricValue != null)) {
            result = new Fraction(metricValue).myFormatUsingProperFractionFormat()
        }
        return result
    }

    public static getTargetUnit(Unit unit){
        Unit targetUnit = new Unit()
        if(unit){
            targetUnit = StandardConversion.findBySourceUnit(unit)?.targetUnit
            if(!targetUnit){
                targetUnit = unit
            }
        }
        return targetUnit
    }

    static constraints = {
    }
}

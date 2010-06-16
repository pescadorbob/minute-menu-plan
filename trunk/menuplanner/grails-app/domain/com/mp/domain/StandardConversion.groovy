package com.mp.domain

import org.apache.commons.math.fraction.Fraction
import java.text.FieldPosition
import org.apache.commons.math.fraction.ProperFractionFormat
import static com.mp.MenuConstants.*

class StandardConversion {
    Unit sourceUnit
    Unit targetUnit
    Double conversionFactor

    public static Quantity getQuantityToSave(String amountFraction, Unit displayUnit) { 
        Quantity result
        Float amount
        if (amountFraction) {
          result = new Quantity()
            if (amountFraction.contains('/')) {
                amount = new Fraction(amountFraction)?.floatValue()
            } else {
                amount = amountFraction.toFloat()
            }
            result.value = amount
        }
        if (displayUnit && (amount != null)) { 
            StandardConversion standardConversion = StandardConversion.findBySourceUnit(displayUnit)
            result.value = amount * standardConversion?.conversionFactor
            result.savedUnit = standardConversion?.targetUnit
            result.unit = displayUnit
        }
        return result
    }

    public static String getQuantityValueString(Quantity sourceQuantity) {
        String result = ''
        Float floatValue = sourceQuantity?.value
        Unit unit = sourceQuantity?.unit
        if (!unit) {
            result = floatValue ? new Fraction(floatValue).myFormatUsingProperFractionFormat() : ''
        } else {
            if (floatValue != null) {
                StandardConversion standardConversion
                StandardConversion.withNewSession {
                    standardConversion = StandardConversion.findBySourceUnit(unit)
                    Float conversionFactor = standardConversion?.conversionFactor
                    floatValue = floatValue / conversionFactor
                    if (unit.belongsToUsaSystem()) {
                        result = new Fraction(floatValue).myFormatUsingProperFractionFormat()
                    } else {
                        result = floatValue ? floatValue?.toString() : ''
                    }
                }
            }
        }
        return result
    }

  
    static constraints = {
    }
}
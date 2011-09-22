package com.mp.domain

import org.apache.commons.math.fraction.Fraction
import java.text.FieldPosition
import org.apache.commons.math.fraction.ProperFractionFormat
import static com.mp.MenuConstants.*

class StandardConversion {
  static transients = {'getQuantityToSave'
    'getQuantityValueString'}
    Unit sourceUnit
    Unit targetUnit
    Float conversionFactor

  // every unit has a conversion to target mL
    public static Quantity getQuantityToSave(String amountFraction, Unit displayUnit, Float density = 1.0f, List<StandardConversion> standardConversions = []) {
        Quantity result
        Float amount
        if (amountFraction) {
            result = new Quantity()
            if (amountFraction.contains('/')) {
                amount = new Fraction(amountFraction)?.floatValue()
            } else {
                amount = amountFraction?.toFloat()
            }
            result.value = amount
        }
        if (displayUnit && (amount != null)) {
            StandardConversion standardConversion = (standardConversions) ? standardConversions.find{it.sourceUnit.id == displayUnit.id} : StandardConversion.findBySourceUnit(displayUnit)
            Float conversionFactor = standardConversion ? standardConversion.conversionFactor : 1.0f
            if (displayUnit.isWeightUnit) {
                result.value = ((amount * conversionFactor) / density)
            } else {
                result.value = amount * conversionFactor
            }
            result.savedUnit = standardConversion ? standardConversion?.targetUnit : displayUnit
            result.unit = displayUnit
        }
        return result
    }

    public static String getQuantityValueString(Quantity sourceQuantity, Float density = 1.0f, List<StandardConversion> standardConversions = []) {
        String result = ''
        Float floatValue = sourceQuantity?.value
        Unit unit = sourceQuantity?.unit
        if (!unit) {
            result = floatValue ? new Fraction(floatValue).myFormatUsingProperFractionFormat() : ''
        } else {
            if (floatValue != null) {
                StandardConversion standardConversion
                    standardConversion =  (standardConversions) ? standardConversions.find{it.sourceUnit.id == unit.id} : StandardConversion.findBySourceUnit(unit)
                    Float conversionFactor = (standardConversion) ? standardConversion.conversionFactor : 1.0f
                    if (unit.isWeightUnit) {
                        floatValue = (floatValue  * density) / conversionFactor
                    }
                    else {
                        floatValue = (floatValue / conversionFactor)
                    }
                    if (unit.belongsToUsaSystem()) {
                        result = new Fraction(floatValue).myFormatUsingProperFractionFormat()
                    } else {
                        result = floatValue ? floatValue?.toString() : ''
                    }
            }
        }
        return result
    }

    static mapping = {
        sourceUnit fetch: 'join'
        targetUnit fetch: 'join'
        conversionFactor(sqlType: 'numeric(20, 10)', fetch: 'join')
        cache true
    }
}
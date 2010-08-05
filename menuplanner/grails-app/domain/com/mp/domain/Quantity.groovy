package com.mp.domain

import org.apache.commons.math.fraction.Fraction

class Quantity {

    Float value
    Unit unit
    Unit savedUnit

    String toString() {
        String amount
        String result
        amount = StandardConversion.getQuantityValueString(this)
        result = "${amount ? amount : ''}${unit ? (' ' + unit?.symbol) : ''}"
        return result.trim()
    }

    static constraints = {
        value(nullable: true, blank: true)
        unit(nullable: true, blank: true)
        savedUnit(nullable: true, blank: true)
    }

//TODO: Make it generic unit conversion method add(q1, q2).

    static mapping = {
        value(sqlType: 'numeric(25, 10)')
        columns {
            value lazy: false
            unit lazy: false
            savedUnit lazy: false
        }
    }

    public static Quantity addTime(Quantity q1, Quantity q2) {
        Quantity sum = new Quantity()
        Float totalMinutes = 0.0f
        totalMinutes += (q1?.value) ? q1?.value : 0.0f
        totalMinutes += (q2?.value) ? q2?.value : 0.0f
        sum.unit = q1.savedUnit
        sum.value = totalMinutes
        return sum
    }

    //TODO; Implement this

    public static Quantity multiply(Quantity quantity1, Quantity quantity2) {
        return add(quantity1, quantity2)
    }

    public static Quantity add(Quantity quantity1, Quantity quantity2) {
        if (!quantity1) { return quantity2 }
        if (!quantity2) { return quantity1 }
        String usVal1 = (quantity1.value) ? (StandardConversion.getQuantityValueString(quantity1)) : ''
        Unit displayUnit1 = quantity1.unit
        String usVal2 = (quantity2.value) ? (StandardConversion.getQuantityValueString(quantity2)) : ''
        Unit displayUnit2 = quantity2.unit
        Quantity resultantQuantity
        Quantity q1 = StandardConversion.getQuantityToSave(usVal1, displayUnit1)
        Quantity q2 = StandardConversion.getQuantityToSave(usVal2, displayUnit2)

        if (q1 != null && (q1?.savedUnit == q2?.savedUnit)) {
            resultantQuantity = new Quantity()
            Unit displayUnit = q1?.unit
            if (displayUnit) {
                if (StandardConversion.findBySourceUnit(q1?.unit)?.conversionFactor > StandardConversion.findBySourceUnit(q2?.unit)?.conversionFactor) {
                    displayUnit = q2?.unit
                }
            }
            if (q1.value && q2.value) {
                resultantQuantity?.value = q1?.value + q2.value
            } else {
                resultantQuantity?.value = (q1.value) ? q1.value : q2.value
            }
            resultantQuantity?.savedUnit = q1?.savedUnit
            resultantQuantity?.unit = displayUnit
        }
        return resultantQuantity
    }

    public String toBiggestUnitString(Float density) {
        if (!unit || !unit.isConvertible) {return this.toString()}
        String finalString = ''
        if (!value) {return finalString}
        Float valueWithDensity = ((unit?.isWeightUnit) ? (value * density) : value).toFloat()
        StandardConversion standardConversion = StandardConversion.createCriteria().get {
            eq('targetUnit', savedUnit)
            le('conversionFactor', valueWithDensity)
            sourceUnit {
                eq('isWeightUnit', unit.isWeightUnit)
                eq('isConvertible', true)
            }
            order("conversionFactor", "desc")
            maxResults(1)
        }
        if (standardConversion) {
            Fraction f3 = new Fraction((valueWithDensity / standardConversion.conversionFactor.toFloat()))
            finalString = (f3.myFormatUsingProperFractionFormat() + " " + standardConversion.sourceUnit?.symbol)
        } else {
            finalString = toString()
        }
        return finalString
    }
}

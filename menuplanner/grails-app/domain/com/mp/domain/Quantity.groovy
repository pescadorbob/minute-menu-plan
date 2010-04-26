package com.mp.domain

import static com.mp.MenuConstants.*
import java.text.FieldPosition
import org.apache.commons.math.fraction.ProperFractionFormat
import org.apache.commons.math.fraction.Fraction

class Quantity {

    String value
    Unit unit
    Unit savedUnit

    def beforeInsert = {
        if (value?.contains(".") && (value.tokenize(".").last()?.toInteger() == 0)) {
            value = value.tokenize(".").first()
        }
    }

    static transients = ['fractionValue']

    public BigDecimal decimalValue() {
        return value?.toBigDecimal()
    }

    String toString() {
        if (unit?.systemOfUnits*.systemName.contains(SYSTEM_OF_UNIT_METRIC)) {
            return "${value ?: ''} ${unit?.symbol ?: ''}"
        } else {
            return "${fractionValue ?: ''} ${unit?.symbol ?: ''}"
        }
    }

    String getFractionValue() {

        if (value) {
            Fraction fraction = new Fraction(value.toBigDecimal())
            return fraction.myFormatUsingProperFractionFormat()
        } else {
            return ''
        }
    }

    static constraints = {
        value(nullable: true, blank: true)
        unit(nullable: true, blank: true)
        savedUnit(nullable: true, blank: true)
    }

    //TODO: Make it generic unit conversion method add(q1, q2).

    public static Quantity addTime(Quantity q1, Quantity q2) {
        Quantity sum = new Quantity()
        sum.value = '0.00'
        //Buggy code: Quick fix for hibernate expception during bootstrap
        //sum.unit = Unit.findByName(TIME_UNIT_MINUTES)
        sum.unit = q1.unit
        sum.value = sum.value.toBigDecimal() + ((q1.unit.id == sum.unit.id) ? q1.value.toBigDecimal() : (q1.value.toBigDecimal() * 60))
        sum.value = sum.value.toBigDecimal() + ((q2.unit.id == sum.unit.id) ? q2.value.toBigDecimal() : (q2.value.toBigDecimal() * 60))
        return sum
    }
}
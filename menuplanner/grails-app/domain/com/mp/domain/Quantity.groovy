package com.mp.domain

import static com.mp.MenuConstants.*
import java.text.FieldPosition
import org.apache.commons.math.fraction.ProperFractionFormat
import org.apache.commons.math.fraction.Fraction

class Quantity {

    BigDecimal value
    Unit unit
    Unit savedUnit

    static transients = ['fractionValue']

    String toString() {
        if (unit?.belongsToMetricSystem()) {
            return "${value ? value : ''} ${unit ? unit?.symbol : ''}"
        } else {
            String amount = value?.toString()
//            amount= StandardConversion.getUsaString(value, unit)
            return "${amount ? amount : ''} ${unit ? unit?.symbol : ''}"
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
        sum.value = 0.00
        //Buggy code: Quick fix for hibernate expception during bootstrap
        //sum.unit = Unit.findByName(TIME_UNIT_MINUTES)
        sum.unit = q1.unit
        sum.value = sum.value + ((q1.unit.id == sum.unit.id) ? q1.value : (q1.value * 60))
        sum.value = sum.value + ((q2.unit.id == sum.unit.id) ? q2.value : (q2.value * 60))
        return sum
    }
}
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
        if (unit && value) {
            String amount = StandardConversion.getUsaString(value, unit)
            return "${amount ? amount : ''} ${unit ? unit : ''}"
        } else {
            return "${value ? value : ''} ${unit ? unit?.symbol : ''}"
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
        BigDecimal totalMinutes = 0.0
        if(q1?.value){
            totalMinutes += q1?.value
        }
        if(q2?.value){
            totalMinutes += q2?.value
        }
        sum.unit = q1.savedUnit
        sum.value = totalMinutes
        return sum
    }
}
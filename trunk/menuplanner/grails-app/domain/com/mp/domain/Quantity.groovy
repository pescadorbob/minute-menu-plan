package com.mp.domain

import static com.mp.MenuConstants.*
import java.text.FieldPosition
import org.apache.commons.math.fraction.ProperFractionFormat
import org.apache.commons.math.fraction.Fraction

class Quantity {

    Float value
    Unit unit
    Unit savedUnit

    String toString() {
        String amount
        amount = StandardConversion.getUsaQuantityString(this)
        return "${amount ? amount : ''} ${unit ? unit?.symbol : ''}"
    }

    static constraints = {
        value(nullable: true, blank: true)
        unit(nullable: true, blank: true)
        savedUnit(nullable: true, blank: true)
    }

//TODO: Make it generic unit conversion method add(q1, q2).

    public static Quantity addTime(Quantity q1, Quantity q2) {
        Quantity sum = new Quantity()
        Float totalMinutes = 0.0
        if (q1?.value) {
            totalMinutes += q1?.value
        }
        if (q2?.value) {
            totalMinutes += q2?.value
        }
        sum.unit = q1.savedUnit
        sum.value = totalMinutes
        return sum
    }

}
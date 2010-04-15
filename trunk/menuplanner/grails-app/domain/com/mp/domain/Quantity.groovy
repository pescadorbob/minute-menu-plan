package com.mp.domain

import static com.mp.MenuConstants.*

class Quantity {

    BigDecimal value
    Unit unit

    String toString() {
        return "${value} ${unit.symbol}"
    }

    static constraints = {
        value(min: 0.0)
    }

    //TODO: Make it generic unit conversion method add(q1, q2).

    public static Quantity addTime(Quantity q1, Quantity q2) {
        Quantity sum = new Quantity()
        sum.value = 0.00
        sum.unit = Unit.findByName(TIME_UNIT_MINUTES)
        sum.value += ((q1.unit.id == sum.unit.id) ? q1.value : (q1.value * 60))
        sum.value += ((q2.unit.id == sum.unit.id) ? q2.value : (q2.value * 60))
        return sum
    }
}
package com.mp.domain

class Quantity {

    BigDecimal value
    Unit unit

    String toString() {
        return "${value} ${unit.symbol}"
    }

    static constraints = {
        value(min: 0.0)
    }

    //TODO: Implement unit conversion.

    public static Quantity add(Quantity q1, Quantity q2) {
        Quantity sum = new Quantity()
        sum.value = q1.value + q2.value
        sum.unit = q1.unit
        return sum
    }

}

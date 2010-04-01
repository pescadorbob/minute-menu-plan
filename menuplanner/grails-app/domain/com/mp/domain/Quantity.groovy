package com.mp.domain

class Quantity {

    BigDecimal value
    Unit unit

    String toString() {
        return "${value} ${unit}"
    }

    static constraints = {
        value(min: 0.0)
    }
}

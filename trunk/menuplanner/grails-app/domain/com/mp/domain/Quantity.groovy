package com.mp.domain

class Quantity {

    BigDecimal amount
    Unit unit

    String toString() {
        return (amount + ' ' + unit)
    }

    static constraints = {
        amount(min:0.0001)
    }
}

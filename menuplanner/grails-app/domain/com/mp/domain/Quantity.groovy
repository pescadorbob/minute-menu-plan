package com.mp.domain

class Quantity {

    BigDecimal amount
    Metric metric

    String toString() {
        return (amount + ' ' + metric)
    }

    static constraints = {
        amount(min:0.0001)
    }
}

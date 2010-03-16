package com.mp.domain

class Quantity {

    BigDecimal amount
    Metric metric

    String toString() {
        return (amount + ' ' + metric)
    }

    static constraints = {
    }
}

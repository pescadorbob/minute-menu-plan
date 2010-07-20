package com.mp.domain

import com.mp.google.checkout.*

class OrderStatus {

    String orderId
    String transactionId
    FinancialState financialStatus  = FinancialState.REVIEWING
    FulfillmentState fulfillmentStatus = FulfillmentState.NEW

//    def beforeInsert = {
//        financialStatus = (financialStatus) ? financialStatus : FinancialState.REVIEWING
//        fulfillmentStatus = (fulfillmentStatus) ? fulfillmentStatus : FulfillmentState.NEW
//
//    }

    static constraints = {
    }
}

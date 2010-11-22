package com.mp.domain.orders

import com.mp.google.checkout.*
import com.mp.domain.party.Party

class OrderStatus {

    Date dateCreated
    Party party
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

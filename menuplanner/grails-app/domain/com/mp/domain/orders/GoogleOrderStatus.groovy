package com.mp.domain.orders

import com.mp.google.checkout.*
import com.mp.domain.party.Party

class GoogleOrderStatus extends OrderStatus {

    Date dateCreated
    Party party
    String orderId        // google order number
    String transactionId  // google serial number
    FinancialState financialStatus  = FinancialState.REVIEWING
    FulfillmentState fulfillmentStatus = FulfillmentState.NEW

    static constraints = {
    }
}

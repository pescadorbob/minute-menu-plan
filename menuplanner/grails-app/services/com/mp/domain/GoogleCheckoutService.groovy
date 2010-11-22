package com.mp.domain

import org.codehaus.groovy.grails.commons.ConfigurationHolder
import com.mp.google.checkout.*
import com.mp.domain.orders.OrderStatus

class GoogleCheckoutService {

    boolean transactional = true
    static config = ConfigurationHolder.config

    public Boolean updateFulfillmentState(OrderStatus orderStatus, FulfillmentState newState, String transactionId) {
        orderStatus.transactionId = transactionId
        orderStatus.s()
        String queryString = "_type=${newState.type}&google-order-number=${orderStatus.orderId}"
        if(newState == FulfillmentState.WILL_NOT_DELIVER){
            //TODO: Give cancellation Reason here.
            queryString+='&reason=merchant_cancelled_order'
        }
        return changeOrderState(queryString)
    }

    public Boolean updateFinancialState(OrderStatus orderStatus, FinancialState newState, String transactionId) {
        orderStatus.financialStatus = newState
        orderStatus.transactionId = transactionId
        orderStatus.s()
        String queryString = "_type=${newState.type}&google-order-number=${orderStatus.orderId}"
        return changeOrderState(queryString)
    }

    private Boolean changeOrderState(String queryString) {
        Integer responseCode
        String merchantKey = config.googleCheckout.merchantKey
        String merchantId = config.googleCheckout.merchantId
        String requestFormUrl = config.googleCheckout.requestFormUrl

        URL url = new URL(requestFormUrl)
        String authString = "${merchantId}:${merchantKey}".toString().getBytes().encodeBase64().toString()
        URLConnection connection = url.openConnection()
        connection.setRequestMethod("POST")
        connection.setRequestProperty("Authorization", "Basic ${authString}")
        connection.doOutput = true

        Writer writer = new OutputStreamWriter(connection.outputStream)
        writer.write(queryString)
        writer.flush()
        writer.close()
        connection.connect()
        responseCode = connection.responseCode
        return (responseCode == 200)
    }
}

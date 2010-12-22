package com.mp.subscriptions.paypal;

public enum TransactionType {
    SUBSCRIPTION_CANCELLED('subscr_cancel'),
    SUBSCRIPTION_EXPIRED('subscr_eot'),
    SUBSCRIPTION_FAILED('subscr_failed'),
    SUBSCRIPTION_SIGNUP('subscr_signup'),
    SUBSCRIPTION_PAYMENT('subscr_payment')

    String name

    TransactionType(String name){
        this.name = name
    }

    String toString(){
        return name
    }
}
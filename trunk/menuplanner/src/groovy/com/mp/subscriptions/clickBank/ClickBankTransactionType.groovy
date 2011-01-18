package com.mp.subscriptions.clickBank

public enum ClickBankTransactionType {
    SUBSCRIPTION_CANCELLED('CANCEL-REBILL'),
    SUBSCRIPTION_SIGNUP('SALE'),
    SUBSCRIPTION_PAYMENT('BILL')

    String name

    ClickBankTransactionType(String name){
        this.name = name
    }

    String toString(){
        return name
    }
}
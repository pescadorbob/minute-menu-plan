package com.mp.google.checkout

public enum FinancialState {

    REVIEWING('REVIEWING'),
    CHARGEABLE('CHARGEABLE'),
    CHARGING('CHARGING'),
    CHARGED('CHARGED'),
    PAYMENT_DECLINED('PAYMENT_DECLINED'),
    CANCELLED('CANCELLED'),
    CANCELLED_BY_GOOGLE('CANCELLED_BY_GOOGLE')

    String name

    FinancialState(String name){
        this.name = name
    }

    String toString(){
        return name
    }


}
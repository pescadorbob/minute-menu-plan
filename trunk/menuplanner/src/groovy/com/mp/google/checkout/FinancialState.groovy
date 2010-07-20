package com.mp.google.checkout

public enum FinancialState {

    REVIEWING('REVIEWING', 'none'),
    CHARGEABLE('CHARGEABLE', 'charge-and-ship-order'),
//    CHARGING('CHARGING'),
    CHARGED('CHARGED', 'none')
//    PAYMENT_DECLINED('PAYMENT_DECLINED'),
//    CANCELLED('CANCELLED'),
//    CANCELLED_BY_GOOGLE('CANCELLED_BY_GOOGLE')

    String name
    String type

    FinancialState(String name, String type){
        this.name = name
        this.type = type
    }

    String toString(){
        return name
    }


}
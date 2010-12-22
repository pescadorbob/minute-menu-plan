package com.mp.subscriptions

public enum GatewayType {
    PAYPAL('PAYPAL'),
    GOOGLE('GOOGLE'),
    CLICKBANK('CLICKBANK')

    String name

    GatewayType(name){
        this.name = name
    }

    String toString(){
        return name
    }


}
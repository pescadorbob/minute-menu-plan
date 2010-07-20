package com.mp.google.checkout

public enum FulfillmentState {
    NEW('NEW', 'new'),
    PROCESSING('PROCESSING', 'process-order'),
    DELIVERED('DELIVERED', 'deliver-order'),
    WILL_NOT_DELIVER('WILL_NOT_DELIVER', 'cancel-items')

    String name
    String type

    FulfillmentState(String name, String type){
        this.name = name
        this.type = type
    }

    String toString(){
        return name
    }
}
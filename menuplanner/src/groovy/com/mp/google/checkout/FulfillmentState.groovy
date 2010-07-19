package com.mp.google.checkout

public enum FulfillmentState {
    NEW('NEW'),
    PROCESSING('PROCESSING'),
    DELIVERED('DELIVERED'),
    WILL_NOT_DELIVER('WILL_NOT_DELIVER')

    String name

    FulfillmentState(String name){
        this.name = name
    }

    String toString(){
        return name
    }
}
package com.mp.subscriptions

public enum SubscriptionStatus {

    CURRENT('CURRENT'),
    EXPIRED('EXPIRED'),
    RENEWED('RENEWED'),
    CANCELLED('CANCELLED')

    String name

    SubscriptionStatus(String name){
        this.name = name
    }

    String toString(){
        return name
    }

}
package com.mp.subscriptions

public enum SubscriptionStatus {

    CURRENT('CURRENT'),
    PENDING('PENDING'),
    EXPIRED('EXPIRED'),
    RENEWED('RENEWED')

    String name

    SubscriptionStatus(String name){
        this.name = name
    }

    String toString(){
        return name
    }

}
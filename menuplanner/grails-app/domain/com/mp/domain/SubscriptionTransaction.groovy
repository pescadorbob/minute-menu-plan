package com.mp.domain

class SubscriptionTransaction extends AccountTransaction{

    static constraints = {
    }

    static mapping = {
        tablePerHierarchy false
    }

}

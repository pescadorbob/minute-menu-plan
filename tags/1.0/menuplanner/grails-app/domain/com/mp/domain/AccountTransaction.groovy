package com.mp.domain

class AccountTransaction {

    Date date
    Float amount

    static constraints = {
    }

    static mapping = {
        tablePerHierarchy false
    }

}

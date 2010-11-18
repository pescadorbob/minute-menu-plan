package com.mp.domain

class Account {

    String accountNumber
    List<AccountTransaction> transactions = []

    static hasMany = [transactions: AccountTransaction]

    static constraints = {
    }

    static mapping = {
        tablePerHierarchy false
    }

}

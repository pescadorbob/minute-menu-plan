package com.mp.domain.accounting

class Account {

    String accountNumber = UUID.randomUUID().toString()
    String name
    Float balance
    Date lastTransaction
    static hasMany = [transactions: AccountTransaction]

    static constraints = {
        lastTransaction(nullable: true)
    }

    static transients = ['balance']

    Float getBalance(){
        List<AccountTransaction> accountTransactions = transactions
        return (accountTransactions ? accountTransactions.sum {it.amount} : 0.0) as Float
    }

    static mapping = {
        transactions(lazy: false)
        tablePerHierarchy false
    }

}

package com.mp.domain.accounting

class Account {

  String accountNumber = UUID.randomUUID().toString()
  String name
  Float balance   = 0.0f
  Date lastTransaction
  static hasMany = [transactions: AccountTransaction]

  static constraints = {
    lastTransaction(nullable:true)
  }

  static mapping = {
    tablePerHierarchy false
  }

}

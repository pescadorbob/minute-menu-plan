package com.mp.domain.accounting

import com.mp.domain.accounting.AccountTransaction

class Account {

  String accountNumber

  float balance
  Date lastTransaction
  static hasMany = [transactions: AccountTransaction]

  static constraints = {
    lastTransaction(nullable:true)
  }

  static mapping = {
    tablePerHierarchy false
  }

}

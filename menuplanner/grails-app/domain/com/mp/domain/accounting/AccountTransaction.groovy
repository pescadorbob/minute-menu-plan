package com.mp.domain.accounting

class AccountTransaction {

  Date transactionDate
  Float amount
  String description
  AccountTransactionType transactionType
  Account transactionFor
  Boolean isVoid = false
  
  static constraints = {
  }

  static mapping = {
    tablePerHierarchy false
  }

}

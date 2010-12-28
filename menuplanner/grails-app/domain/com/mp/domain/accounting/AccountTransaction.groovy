package com.mp.domain.accounting

class AccountTransaction {

  Date transactionDate
  Float amount
  String description
  AccountTransactionType transactionType
  Account transactionFor
  String uniqueId = UUID.randomUUID().toString()
  Boolean isVoid = false
  
  static constraints = {
      uniqueId(unique: true)
  }

  static mapping = {
    tablePerHierarchy false
  }

}

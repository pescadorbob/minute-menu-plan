package com.mp.domain.accounting
/**
 * Created on Nov 19, 2010
 * Created by Brent Fisher
 *
 */
 
enum AccountTransactionType {
   OPENING_BALANCE('OPENING_BALANCE'),
   SUBSCRIPTION_CHARGE('SUBSCRIPTION_CHARGE'),
   SUBSCRIPTION_PAYMENT('SUBSCRIPTION_PAYMENT'),
   SUBSCRIPTION_PAYMENT_FAILED('SUBSCRIPTION_PAYMENT'),
   SUBSCRIPTION_CANCELLED('SUBSCRIPTION_CANCELLED'),
   SUBSCRIPTION_EXPIRED('SUBSCRIPTION_EXPIRED'),
   AUTOMATED_FUNDING('AUTOMATED_FUNDING'),
   FUNDING('FUNDING'),
   AFFILIATE_PAYMENT('AFFILIATE_PAYMENT'),
   REFUND('REFUND'),
   ADJUSTMENT('ADJUSTMENT')


  public AccountTransactionType(name){
    this.name = name
  }
  final String name

  public String toString(){

  }
}
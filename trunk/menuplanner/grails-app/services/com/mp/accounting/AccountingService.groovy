package com.mp.accounting

import com.mp.domain.accounting.AccountTransaction
import com.mp.domain.accounting.OperationalAccount
import com.mp.domain.accounting.Account
import org.hibernate.Criteria
import com.mp.domain.accounting.AccountTransactionType
import com.mp.domain.accounting.AccountRoleType
import com.mp.domain.accounting.AccountRole
import com.mp.domain.party.Party

/**
 * Created on Nov 28, 2010
 * Created by Brent Fisher
 *
 * This file contains proprietary information of Access-Wealth, LLC.
 * Copying or reproduction without prior written approval is prohibited.
 * (c) Copyright 1998-2010 Access-Wealth, LLC.
 * All rights reserved.
 */

public class AccountingService {
    /*
   OPENING_BALANCE('OPENING_BALANCE'),
    SUBSCRIPTION_PAYMENT('SUBSCRIPTION_PAYMENT'),
    SUBSCRIPTION_CHARGE('SUBSCRIPTION_CHARGE'),
    AUTOMATED_FUNDING('AUTOMATED_FUNDING'),
    FUNDING('FUNDING'),
    AFFILIATE_PAYMENT('AFFILIATE_PAYMENT'),
    REFUND('REFUND'),
    ADJUSTMENT('ADJUSTMENT')
    */

    public void createTxn(opAcctNum, toAcctNum, txDate, txAmt, description, txType) {
        if (log.isDebugEnabled()) log.debug """Op:${opAcctNum}
         acct:${toAcctNum}
         txDate:${txDate}
         txAmt:${txAmt}
         description:${description}"""
        OperationalAccount opAcct = OperationalAccount.findByAccountNumber(opAcctNum)
        Account toAcct = Account.findByAccountNumber(toAcctNum)
        switch (txType) {
            case AccountTransactionType.OPENING_BALANCE:
            case AccountTransactionType.SUBSCRIPTION_PAYMENT:
                break
            case AccountTransactionType.AUTOMATED_FUNDING:
            case AccountTransactionType.FUNDING:
            case AccountTransactionType.SUBSCRIPTION_CANCELLED:
            case AccountTransactionType.SUBSCRIPTION_EXPIRED:
            case AccountTransactionType.SUBSCRIPTION_PAYMENT_FAILED:
            case AccountTransactionType.AFFILIATE_PAYMENT:
            case AccountTransactionType.REFUND:
            case AccountTransactionType.ADJUSTMENT:
                new AccountTransaction(transactionFor: opAcct,
                        transactionDate: txDate, amount: -1 * txAmt, description: description, transactionType: txType).s()
                break
        }
        new AccountTransaction(transactionFor: toAcct,
                transactionDate: txDate, amount: txAmt, description: description, transactionType: txType).s()
    }

    def getCurrentBalance(Account acct) {
        def c = AccountTransaction.createCriteria()
        def balance = c.get {
            transactionFor {
                idEq(acct.id)
            }
            projections {
                sum('amount')
            }
        }
        println "Balance:${balance}"
        balance
    }

    public Account findOrCreateNewAccount(Party party) {
        AccountRole accountRole = AccountRole.findByRoleFor(party)
        if (accountRole) {
            return accountRole.describes
        } else {
            Account account = new Account(name: "General Account:${party.name}").s()
            new AccountRole(roleFor: party, describes: account, type: AccountRoleType.OWNER).s()
            new AccountTransaction(transactionFor: account, transactionDate: new Date(), amount: 0.0, description: "Opening Balance", transactionType: AccountTransactionType.OPENING_BALANCE).s()
            return account
        }
    }

    def getBalance(AccountTransaction txn) {
        def c = AccountTransaction.createCriteria()
        def balance = c.get {
            transactionFor {
                idEq(txn.transactionFor.id)
            }
            and {
                le('id', txn.id)
                le('transactionDate', txn.transactionDate)
            }
            projections {
                sum('amount')
            }
        }
        if (!balance) {
            println "Balance not found!"
            balance = 0.0
        }
        balance
    }
}
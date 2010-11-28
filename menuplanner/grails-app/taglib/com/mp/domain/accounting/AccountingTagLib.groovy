package com.mp.domain.accounting

import com.mp.domain.party.DirectorCoach
import com.mp.domain.party.CoachSubscriber
import org.codehaus.groovy.grails.plugins.web.taglib.FormatTagLib
import java.text.NumberFormat
import javax.swing.text.NumberFormatter
import java.text.DecimalFormat
import com.mp.domain.UserLogin
import org.hibernate.Criteria

public class AccountingTagLib {
  static namespace = "txn"
  def accountingService

  def balance = {attrs, body ->
    def accountTransaction = attrs?.txn
    def account = attrs?.acct
    def balance = 0.0
    if(accountTransaction){
      balance = accountingService.getBalance(accountTransaction)
    } else if (account){
      balance = accountingService.getCurrentBalance(account)
    }
    def format = attrs.format
    DecimalFormat myFormatter = new DecimalFormat(format);
    String output = "unknown"
    try {
      output = myFormatter.format(balance);
      if (balance < 0) {
        output = "<span class=\"neg\">(${output})</span>"
      }
    } catch (Exception e){
      println "Unknown balance:${balance}"
      e.printStackTrace()
    }
    out << output
  }

  def name = {attrs, body ->
    def ar = AccountRole.findByDescribes(attrs?.account)
    out << ar?.roleFor?.name
  }
  def email = {attrs, body ->
    def party = AccountRole.findByDescribes(attrs?.account)?.roleFor
    def login = UserLogin.findByParty(party)

    out << login?.email
  }
  def eachTransaction = {attrs, body ->
    def account = attrs?.account
    def from = attrs.from
    def thru = attrs.thru
    def var = attrs.var ? attrs.var : "t"
    def status = attrs.status ? attrs.status : "i"
    def c = AccountTransaction.createCriteria()
    def trans = c.list {
      if(account) transactionFor {
        idEq(account.id)
      }
      ge('transactionDate',from)
      le('transactionDate',thru+1)
    }?.sort { a, b ->
      def dateCompare = a.transactionDate.compareTo(b.transactionDate)
      if(!dateCompare)
         return a.id - b.id
      else return dateCompare
    }.eachWithIndex {it, i ->
      out << body((var): it, (status): i)
    }
  }
}

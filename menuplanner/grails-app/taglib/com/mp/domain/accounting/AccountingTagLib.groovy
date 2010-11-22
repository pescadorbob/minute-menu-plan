package com.mp.domain.accounting

import com.mp.domain.party.DirectorCoach
import com.mp.domain.party.CoachSubscriber
import org.codehaus.groovy.grails.plugins.web.taglib.FormatTagLib
import java.text.NumberFormat
import javax.swing.text.NumberFormatter
import java.text.DecimalFormat

public class AccountingTagLib  {
  static namespace = "txn"

  def balance = {attrs, body ->
    if (log.isDebugEnabled()) {
      log.debug "Calculating Balance"
    }
    def account = attrs?.account
    def accountTransaction = attrs?.txn
    def balance = 0.0;
    account.transactions.findAll {
      it.transactionDate <= accountTransaction.transactionDate
    }.sort {
      it.transactionDate
    }.each {
      balance += it.amount
    }
    def format = attrs.format
    DecimalFormat myFormatter = new DecimalFormat(format);
    String output = myFormatter.format(balance);
    if(balance<0){
      output = "<em class=\"neg\">${output}</em>"
    }
    out << output
  }
  def eachTransaction = {attrs, body ->
    def account = attrs?.account
    def from = attrs.from
    def thru = attrs.thru
    def var = attrs.var ? attrs.var : "t"
    def status = attrs.status ? attrs.status : "i"
    if (log.isDebugEnabled()) {
      log.debug "getting Account Transactions ${from} - ${thru}"
    }
    account.transactions.findAll{
      it.transactionDate >= from && it.transactionDate <= thru+1
    }?.sort { it.transactionDate }.eachWithIndex { it, i->
      out << body((var):it,(status):i)
    }
  }


}

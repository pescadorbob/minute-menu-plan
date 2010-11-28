<%@ page import="com.mp.domain.accounting.OperationalAccount; com.mp.domain.accounting.Account" %>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <meta name="layout" content="menu"/>
  <g:set var="entityName" value="${message(code: 'account.label', default: 'Account')}"/>
  <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>
<body>
<div id="container">
  <div id="wrapper" class="clearfix">
    <div id="content-wrapper" class="clearfix">
      <div class="headbox">
        <h3>General Ledger</h3>
      </div>
      <div class="nav">
        <span class="menuButton"><g:link class="create" action="create" controller="accountTransaction">Create New Transaction</g:link></span>
      </div>

      <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
      </g:if>
      <div class="dialog">
        <g:form method="post" action="showGeneralLedger">
        <table>
          <tbody>
            <tr class="prop">
              <td valign="top" class="name">
                <label for="from">From</label></br>
                <g:datePicker name="from" precision="day" value="${from}"/>
                </td><td>
                <label for="thru">Thru</label></br>
                <g:datePicker name="thru" precision="day" value="${thru}"/>
            </td><td>
                <label for="accountNum">Account</label>   </br>
                <g:select name="accountNum" value="${accountNum}"
                  from="${Account.list()}"
                  noSelection="['null':'-No Account-']"
                  optionKey="accountNumber" optionValue="name"/>
            </td><td>   </br>
              <g:submitButton name="filter" value="Filter"/>
              </td>
            </tr>
          </tbody>
        </table>
          </g:form>

            <table><tbody>
          <tr>
            <th>Date</th>
            <th>Account</th>
            <th>Memo</th>
            <th>Amount</th>
            <th>Balance</th>
            <th>Action</th>
          </tr>
            <txn:eachTransaction account="${account}" status="i" var="t" from="${from}" thru="${thru}">
              <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                <td><g:link controller="accountTransaction" action="show" id="${t.id}"><g:formatDate format="yyyy-MM-dd"
                        date="${t.transactionDate}"/></g:link></td>
                <td>${t.transactionFor?.name}</td>
                <td>${t.description}</td>
                <td class="currency"><g:formatNumber number="${t.amount}" format="\$###,##0.00" /></td>
                <td class="currency"><txn:balance txn="${t}" format="\$###,##0.00"/></td>
                <td>
                  <g:link action="void" id="${t.id}">VOID</g:link> &nbsp;
                  <g:link action="delete" id="${t.id}">DELETE</g:link> &nbsp;
                  <g:link action="edit" id="${t.id}">EDIT</g:link>
                </td>
              </tr>
            </txn:eachTransaction>
          </tbody>
        </table>
      </div>
    </div>
  </div></div></body>
</html>

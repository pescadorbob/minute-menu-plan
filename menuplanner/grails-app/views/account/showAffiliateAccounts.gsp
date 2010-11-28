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
        <h3>Affiliate Account Balance Activity</h3>
      </div>
      <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
      </g:if>
      <div class="dialog">
        <g:form method="post" action="showAffiliateAccounts">
        <table>
          <tbody>
            <tr class="prop">
              <td valign="top" class="name">
                <label for="minimumBalance">Minimum Balance:</label>
                <g:textField name="minimumBalance" value="${minimumBalance}"/>
                <g:submitButton name="filter" value="Filter"/>
              </td>
            </tr>
          </tbody>
        </table>
          </g:form>
          <g:form method="post" action="markPaid">
            <table><tbody>
          <tr>
            <th>Mark Paid</th>
            <th>Name</th>
            <th>Email Address</th>
            <th>Amount Due</th>
            <th>Pay Pal</th>
          </tr>
          <g:each in="${accounts}" status="i" var="account">
            <% def val = "accounts" %>
            <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
              <td>
              <g:checkBox name="${val}" checked="${false}" value="${account.accountNumber}" />
              </td>
              <td><txn:name account="${account}"/></td>
              <td><txn:email account="${account}"/></td>
              <td class="currency"><txn:balance acct="${account}" format="\$###,##0.00"/></td>
              <td><a href="#" target="_paypal">Pay with pay pal</a></td>
            </tr>
          </g:each>
            <tr><td colspan="5"><label for ="operationalAccountNum">Operational Account:</label>
            <g:select name="operationalAccountNum"
          from="${OperationalAccount.list()}"
          optionKey="accountNumber" optionValue="name"/>
            </td></tr>
           <tr><td colspan="5">
              <label for="comments">Comments:</label><g:textField name="comments" />
             <g:submitButton name="markPaid" value="Mark Paid"/>
            </td></tr>
          </tbody>
        </table>
            </g:form>
      </div>
    </div>
  </div></div></body>
</html>

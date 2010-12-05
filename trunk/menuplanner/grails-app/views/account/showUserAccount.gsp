<%@ page import="com.mp.domain.accounting.Account" %>
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
        <h3>Account Activity</h3>
      </div>
      <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
      </g:if>
      <div class="dialog">
        <table class="data">
          <tbody>
          <g:form method="post" action="showUserAccount">
            <g:hiddenField name="id" value="${party?.id}"/>

            <tr class="prop">
              <td valign="top" class="name">
                <label for="from">From</label>
                <g:datePicker name="from" precision="day" value="${from}"/>
                <label for="thru">Thru</label>
                <g:datePicker name="thru" precision="day" value="${thru}"/>
                <g:submitButton name="filter" value="Filter"/>
              </td>
            </tr>
          </g:form>
          <tr class="prop">

            <td valign="top" style="text-align: left;" class="value">
              <ul>
                <g:render template="/account/accountTransactions" model="[account:account,from:from,thru:thru]"/>
              </ul>
            </td>

          </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div></div></body>
</html>

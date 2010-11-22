
<%@ page import="com.mp.domain.accounting.AccountTransaction" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'accountTransaction.label', default: 'AccountTransaction')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${accountTransaction}">
            <div class="errors">
                <g:renderErrors bean="${accountTransaction}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${accountTransaction?.id}" />
                <g:hiddenField name="version" value="${accountTransaction?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="amount"><g:message code="accountTransaction.amount.label" default="Amount" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: accountTransaction, field: 'amount', 'errors')}">
                                    <g:textField name="amount" value="${fieldValue(bean: accountTransaction, field: 'amount')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="transactionType"><g:message code="accountTransaction.transactionType.label" default="Transaction Type" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: accountTransaction, field: 'transactionType', 'errors')}">
                                    <g:select name="transactionType" from="${com.mp.domain.accounting.AccountTransactionType?.values()}" value="${accountTransaction?.transactionType}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="transactionDate"><g:message code="accountTransaction.transactionDate.label" default="Transaction Date" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: accountTransaction, field: 'transactionDate', 'errors')}">
                                    <g:datePicker name="transactionDate" precision="day" value="${accountTransaction?.transactionDate}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="description"><g:message code="accountTransaction.description.label" default="Description" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: accountTransaction, field: 'description', 'errors')}">
                                    <g:textField name="description" value="${accountTransaction?.description}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="transactionFor"><g:message code="accountTransaction.transactionFor.label" default="Transaction For" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: accountTransaction, field: 'transactionFor', 'errors')}">
                                    <g:select name="transactionFor.id" from="${com.mp.domain.accounting.Account.list()}" optionKey="id" value="${accountTransaction?.transactionFor?.id}"  />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>

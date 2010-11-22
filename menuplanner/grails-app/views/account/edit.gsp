
<%@ page import="com.mp.domain.accounting.Account" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'account.label', default: 'Account')}" />
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
            <g:hasErrors bean="${account}">
            <div class="errors">
                <g:renderErrors bean="${account}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${account?.id}" />
                <g:hiddenField name="version" value="${account?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="lastTransaction"><g:message code="account.lastTransaction.label" default="Last Transaction" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: account, field: 'lastTransaction', 'errors')}">
                                    <g:datePicker name="lastTransaction" precision="day" value="${account?.lastTransaction}" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="transactions"><g:message code="account.transactions.label" default="Transactions" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: account, field: 'transactions', 'errors')}">
                                    
<ul>
<g:each in="${account?.transactions?}" var="t">
    <li><g:link controller="accountTransaction" action="show" id="${t.id}">${t?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="accountTransaction" action="create" params="['account.id': account?.id]">${message(code: 'default.add.label', args: [message(code: 'accountTransaction.label', default: 'AccountTransaction')])}</g:link>

                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="balance"><g:message code="account.balance.label" default="Balance" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: account, field: 'balance', 'errors')}">
                                    <g:textField name="balance" value="${fieldValue(bean: account, field: 'balance')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="accountNumber"><g:message code="account.accountNumber.label" default="Account Number" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: account, field: 'accountNumber', 'errors')}">
                                    <g:textField name="accountNumber" value="${account?.accountNumber}" />
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


<%@ page import="com.mp.domain.accounting.AccountTransaction" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'accountTransaction.label', default: 'AccountTransaction')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'accountTransaction.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="amount" title="${message(code: 'accountTransaction.amount.label', default: 'Amount')}" />
                        
                            <g:sortableColumn property="transactionType" title="${message(code: 'accountTransaction.transactionType.label', default: 'Transaction Type')}" />
                        
                            <g:sortableColumn property="transactionDate" title="${message(code: 'accountTransaction.transactionDate.label', default: 'Transaction Date')}" />
                        
                            <g:sortableColumn property="description" title="${message(code: 'accountTransaction.description.label', default: 'Description')}" />
                        
                            <th><g:message code="accountTransaction.transactionFor.label" default="Transaction For" /></th>
                   	    
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${accountTransactionList}" status="i" var="accountTransaction">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${accountTransaction.id}">${fieldValue(bean: accountTransaction, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: accountTransaction, field: "amount")}</td>
                        
                            <td>${fieldValue(bean: accountTransaction, field: "transactionType")}</td>
                        
                            <td><g:formatDate date="${accountTransaction.transactionDate}" /></td>
                        
                            <td>${fieldValue(bean: accountTransaction, field: "description")}</td>
                        
                            <td>${fieldValue(bean: accountTransaction, field: "transactionFor")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${accountTransactionTotal}" />
            </div>
        </div>
    </body>
</html>


<%@ page import="com.mp.domain.accounting.Account" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'account.label', default: 'Account')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'account.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="lastTransaction" title="${message(code: 'account.lastTransaction.label', default: 'Last Transaction')}" />
                        
                            <g:sortableColumn property="balance" title="${message(code: 'account.balance.label', default: 'Balance')}" />
                        
                            <g:sortableColumn property="accountNumber" title="${message(code: 'account.accountNumber.label', default: 'Account Number')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${accountList}" status="i" var="account">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${account.id}">${fieldValue(bean: account, field: "id")}</g:link></td>
                        
                            <td><g:formatDate date="${account.lastTransaction}" /></td>
                        
                            <td>${fieldValue(bean: account, field: "balance")}</td>
                        
                            <td>${fieldValue(bean: account, field: "accountNumber")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${accountTotal}" />
            </div>
        </div>
    </body>
</html>

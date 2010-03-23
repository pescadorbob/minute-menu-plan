
<%@ page import="com.mp.domain.Quantity" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'quantity.label', default: 'Quantity')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'quantity.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="amount" title="${message(code: 'quantity.amount.label', default: 'Amount')}" />
                        
                            <th><g:message code="quantity.unit.label" default="Unit" /></th>
                   	    
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${quantityList}" status="i" var="quantity">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${quantity.id}">${fieldValue(bean: quantity, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: quantity, field: "amount")}</td>
                        
                            <td>${fieldValue(bean: quantity, field: "unit")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${quantityTotal}" />
            </div>
        </div>
    </body>
</html>

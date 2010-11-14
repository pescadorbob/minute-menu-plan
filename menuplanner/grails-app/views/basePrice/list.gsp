
<%@ page import="com.mp.domain.subscriptions.BasePrice" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'basePrice.label', default: 'BasePrice')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'basePrice.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="value" title="${message(code: 'basePrice.value.label', default: 'Value')}" />
                        
                            <g:sortableColumn property="activeTo" title="${message(code: 'basePrice.activeTo.label', default: 'Active To')}" />
                        
                            <g:sortableColumn property="activeFrom" title="${message(code: 'basePrice.activeFrom.label', default: 'Active From')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${basePriceList}" status="i" var="basePrice">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${basePrice.id}">${fieldValue(bean: basePrice, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: basePrice, field: "value")}</td>
                        
                            <td><g:formatDate date="${basePrice.activeTo}" /></td>
                        
                            <td><g:formatDate date="${basePrice.activeFrom}" /></td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${basePriceTotal}" />
            </div>
        </div>
    </body>
</html>

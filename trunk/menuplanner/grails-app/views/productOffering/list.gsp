
<%@ page import="com.mp.domain.subscriptions.ProductOffering" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'productOffering.label', default: 'ProductOffering')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'productOffering.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="activeTo" title="${message(code: 'productOffering.activeTo.label', default: 'Active To')}" />
                        
                            <g:sortableColumn property="name" title="${message(code: 'productOffering.name.label', default: 'Name')}" />
                        
                            <g:sortableColumn property="activeFrom" title="${message(code: 'productOffering.activeFrom.label', default: 'Active From')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${productOfferingList}" status="i" var="productOffering">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${productOffering.id}">${fieldValue(bean: productOffering, field: "id")}</g:link></td>
                        
                            <td><g:formatDate date="${productOffering.activeTo}" /></td>
                        
                            <td>${fieldValue(bean: productOffering, field: "name")}</td>
                        
                            <td><g:formatDate date="${productOffering.activeFrom}" /></td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${productOfferingTotal}" />
            </div>
        </div>
    </body>
</html>

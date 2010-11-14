
<%@ page import="com.mp.domain.subscriptions.PricingComponent" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'pricingComponent.label', default: 'PricingComponent')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'pricingComponent.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="value" title="${message(code: 'pricingComponent.value.label', default: 'Value')}" />
                        
                            <g:sortableColumn property="activeTo" title="${message(code: 'pricingComponent.activeTo.label', default: 'Active To')}" />
                        
                            <g:sortableColumn property="description" title="${message(code: 'pricingComponent.description.label', default: 'Description')}" />
                        
                            <th><g:message code="pricingComponent.pricingFor.label" default="Pricing For" /></th>
                   	    
                            <g:sortableColumn property="name" title="${message(code: 'pricingComponent.name.label', default: 'Name')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${pricingComponentList}" status="i" var="pricingComponent">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${pricingComponent.id}">${fieldValue(bean: pricingComponent, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: pricingComponent, field: "value")}</td>
                        
                            <td><g:formatDate date="${pricingComponent.activeTo}" /></td>
                        
                            <td>${fieldValue(bean: pricingComponent, field: "description")}</td>
                        
                            <td>${fieldValue(bean: pricingComponent, field: "pricingFor")}</td>
                        
                            <td>${fieldValue(bean: pricingComponent, field: "name")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${pricingComponentTotal}" />
            </div>
        </div>
    </body>
</html>


<%@ page import="com.mp.domain.subscriptions.RecurringCharge" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'recurringCharge.label', default: 'RecurringCharge')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'recurringCharge.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="recurrence" title="${message(code: 'recurringCharge.recurrence.label', default: 'Recurrence')}" />
                        
                            <g:sortableColumn property="value" title="${message(code: 'recurringCharge.value.label', default: 'Value')}" />
                        
                            <g:sortableColumn property="activeTo" title="${message(code: 'recurringCharge.activeTo.label', default: 'Active To')}" />
                        
                            <g:sortableColumn property="description" title="${message(code: 'recurringCharge.description.label', default: 'Description')}" />
                        
                            <th><g:message code="recurringCharge.pricingFor.label" default="Pricing For" /></th>
                   	    
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${recurringChargeList}" status="i" var="recurringCharge">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${recurringCharge.id}">${fieldValue(bean: recurringCharge, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: recurringCharge, field: "recurrence")}</td>
                        
                            <td>${fieldValue(bean: recurringCharge, field: "value")}</td>
                        
                            <td><g:formatDate date="${recurringCharge.activeTo}" /></td>
                        
                            <td>${fieldValue(bean: recurringCharge, field: "description")}</td>
                        
                            <td>${fieldValue(bean: recurringCharge, field: "pricingFor")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${recurringChargeTotal}" />
            </div>
        </div>
    </body>
</html>

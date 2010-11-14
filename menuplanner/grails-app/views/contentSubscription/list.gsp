
<%@ page import="com.mp.domain.subscriptions.ContentSubscription" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'contentSubscription.label', default: 'ContentSubscription')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'contentSubscription.id.label', default: 'Id')}" />
                        
                            <th><g:message code="contentSubscription.originatingProduct.label" default="Originating Product" /></th>
                   	    
                            <th><g:message code="contentSubscription.content.label" default="Content" /></th>
                   	    
                            <g:sortableColumn property="activeThru" title="${message(code: 'contentSubscription.activeThru.label', default: 'Active Thru')}" />
                        
                            <g:sortableColumn property="name" title="${message(code: 'contentSubscription.name.label', default: 'Name')}" />
                        
                            <g:sortableColumn property="activeFrom" title="${message(code: 'contentSubscription.activeFrom.label', default: 'Active From')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${contentSubscriptionList}" status="i" var="contentSubscription">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${contentSubscription.id}">${fieldValue(bean: contentSubscription, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: contentSubscription, field: "originatingProduct")}</td>
                        
                            <td>${fieldValue(bean: contentSubscription, field: "content")}</td>
                        
                            <td><g:formatDate date="${contentSubscription.activeThru}" /></td>
                        
                            <td>${fieldValue(bean: contentSubscription, field: "name")}</td>
                        
                            <td><g:formatDate date="${contentSubscription.activeFrom}" /></td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${contentSubscriptionTotal}" />
            </div>
        </div>
    </body>
</html>

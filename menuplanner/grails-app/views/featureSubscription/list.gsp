
<%@ page import="com.mp.domain.subscriptions.FeatureSubscription" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'featureSubscription.label', default: 'FeatureSubscription')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'featureSubscription.id.label', default: 'Id')}" />
                        
                            <th><g:message code="featureSubscription.originatingProduct.label" default="Originating Product" /></th>
                   	    
                            <g:sortableColumn property="rule" title="${message(code: 'featureSubscription.rule.label', default: 'Rule')}" />
                        
                            <th><g:message code="featureSubscription.subscribedFeature.label" default="Subscribed Feature" /></th>
                   	    
                            <g:sortableColumn property="activeThru" title="${message(code: 'featureSubscription.activeThru.label', default: 'Active Thru')}" />
                        
                            <g:sortableColumn property="name" title="${message(code: 'featureSubscription.name.label', default: 'Name')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${featureSubscriptionList}" status="i" var="featureSubscription">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${featureSubscription.id}">${fieldValue(bean: featureSubscription, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: featureSubscription, field: "originatingProduct")}</td>
                        
                            <td>${fieldValue(bean: featureSubscription, field: "rule")}</td>
                        
                            <td>${fieldValue(bean: featureSubscription, field: "subscribedFeature")}</td>
                        
                            <td><g:formatDate date="${featureSubscription.activeThru}" /></td>
                        
                            <td>${fieldValue(bean: featureSubscription, field: "name")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${featureSubscriptionTotal}" />
            </div>
        </div>
    </body>
</html>

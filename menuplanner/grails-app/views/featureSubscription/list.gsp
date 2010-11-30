
<%@ page import="com.mp.domain.subscriptions.FeatureSubscription" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'featureSubscription.label', default: 'FeatureSubscription')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
       <div id="container">
  <div id="wrapper" class="clearfix">
    <div id="content-wrapper" class="clearfix">
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h3><g:message code="default.list.label" args="[entityName]" /></h3>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'featureSubscription.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="originalProductOffering" title="${message(code: 'featureSubscription.originalProductOffering.label', default: 'Original Product Offering')}" />
                        
                            <th><g:message code="featureSubscription.subscriptionFor.label" default="Subscription For" /></th>
                   	    
                            <g:sortableColumn property="rule" title="${message(code: 'featureSubscription.rule.label', default: 'Rule')}" />
                        
                            <th><g:message code="featureSubscription.subscribedFeature.label" default="Subscribed Feature" /></th>
                   	    
                            <g:sortableColumn property="activeTo" title="${message(code: 'featureSubscription.activeTo.label', default: 'Active Thru')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${featureSubscriptionList}" status="i" var="featureSubscription">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${featureSubscription.id}">${fieldValue(bean: featureSubscription, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: featureSubscription, field: "originalProductOffering")}</td>
                        
                            <td>${fieldValue(bean: featureSubscription, field: "subscriptionFor")}</td>
                        
                            <td>${fieldValue(bean: featureSubscription, field: "rule")}</td>
                        
                            <td>${fieldValue(bean: featureSubscription, field: "subscribedFeature")}</td>
                        
                            <td><g:formatDate date="${featureSubscription.activeTo}" /></td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${featureSubscriptionTotal}" />
            </div>
          </div></div></div>
        </div>
    </body>
</html>

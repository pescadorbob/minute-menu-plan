
<%@ page import="com.mp.domain.subscriptions.Subscription" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'subscription.label', default: 'Subscription')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'subscription.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="originalProductOffering" title="${message(code: 'subscription.originalProductOffering.label', default: 'Original Product Offering')}" />
                        
                            <th><g:message code="subscription.subscriptionFor.label" default="Subscription For" /></th>
                   	    
                            <g:sortableColumn property="activeFrom" title="${message(code: 'subscription.activeFrom.label', default: 'Active From')}" />
                        
                            <g:sortableColumn property="activeTo" title="${message(code: 'subscription.activeTo.label', default: 'Active Thru')}" />

                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${subscriptionList}" status="i" var="subscription">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${subscription.id}">${fieldValue(bean: subscription, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: subscription, field: "originalProductOffering")}</td>
                        
                            <td>${fieldValue(bean: subscription, field: "subscriptionFor")}</td>
                        
                            <td><g:formatDate date="${subscription.activeFrom}" format="dd-MMM-yyyy"/></td>
                        
                          <td><g:formatDate date="${subscription.activeTo}" format="dd-MMM-yyyy"/></td>

                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${subscriptionTotal}" />
            </div>
          </div></div></div>
        </div>
    </body>
</html>

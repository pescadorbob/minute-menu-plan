
<%@ page import="com.mp.domain.themes.SubscriptionOfferingPage" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'subscriptionOfferingPage.label', default: 'SubscriptionOfferingPage')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'subscriptionOfferingPage.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="name" title="${message(code: 'subscriptionOfferingPage.name.label', default: 'Name')}" />
                        
                            <g:sortableColumn property="template" title="${message(code: 'subscriptionOfferingPage.template.label', default: 'Template')}" />
                        
                            <g:sortableColumn property="lastModified" title="${message(code: 'subscriptionOfferingPage.lastModified.label', default: 'Last Modified')}" />
                        
                            <g:sortableColumn property="activeTo" title="${message(code: 'subscriptionOfferingPage.activeTo.label', default: 'Active To')}" />
                        
                            <g:sortableColumn property="activeFrom" title="${message(code: 'subscriptionOfferingPage.activeFrom.label', default: 'Active From')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${subscriptionOfferingPageList}" status="i" var="subscriptionOfferingPage">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${subscriptionOfferingPage.id}">${fieldValue(bean: subscriptionOfferingPage, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: subscriptionOfferingPage, field: "name")}</td>
                        
                            <td>${fieldValue(bean: subscriptionOfferingPage, field: "template")}</td>
                        
                            <td><g:formatDate date="${subscriptionOfferingPage.lastModified}" /></td>
                        
                            <td>${fieldValue(bean: subscriptionOfferingPage, field: "activeTo")}</td>
                        
                            <td>${fieldValue(bean: subscriptionOfferingPage, field: "activeFrom")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${subscriptionOfferingPageTotal}" />
            </div>
          </div></div></div>
        </div>
    </body>
</html>

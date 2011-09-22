
<%@ page import="com.mp.analytics.TestInterval" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'call.label', default: 'Call')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'call.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="outTime" title="${message(code: 'call.outTime.label', default: 'Out Time')}" />
                        
                            <g:sortableColumn property="total" title="${message(code: 'call.total.label', default: 'Total')}" />
                        
                            <g:sortableColumn property="details" title="${message(code: 'call.details.label', default: 'Details')}" />
                        
                            <g:sortableColumn property="inTime" title="${message(code: 'call.inTime.label', default: 'In Time')}" />
                        
                            <g:sortableColumn property="name" title="${message(code: 'call.name.label', default: 'Name')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${callList}" status="i" var="call">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${call.id}">${fieldValue(bean: call, field: "id")}</g:link></td>
                        
                            <td><g:formatDate date="${call.outTime}" /></td>
                        
                            <td>${fieldValue(bean: call, field: "total")}</td>
                        
                            <td>${fieldValue(bean: call, field: "details")}</td>
                        
                            <td><g:formatDate date="${call.inTime}" /></td>
                        
                            <td>${fieldValue(bean: call, field: "name")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${callTotal}" />
            </div>
          </div></div></div>
        </div>
    </body>
</html>

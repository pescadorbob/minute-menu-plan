
<%@ page import="com.mp.domain.access.AccessFilter" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'accessFilter.label', default: 'AccessFilter')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'accessFilter.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="name" title="${message(code: 'accessFilter.name.label', default: 'Name')}" />
                        
                            <g:sortableColumn property="controllerFilter" title="${message(code: 'accessFilter.controllerFilter.label', default: 'Controller Filter')}" />
                        
                            <g:sortableColumn property="actionFilter" title="${message(code: 'accessFilter.actionFilter.label', default: 'Action Filter')}" />
                        
                            <g:sortableColumn property="uriFilter" title="${message(code: 'accessFilter.uriFilter.label', default: 'Uri Filter')}" />
                        
                            <th><g:message code="accessFilter.filterFor.label" default="Filter For" /></th>
                   	    
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${accessFilterList}" status="i" var="accessFilter">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${accessFilter.id}">${fieldValue(bean: accessFilter, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: accessFilter, field: "name")}</td>
                        
                            <td>${fieldValue(bean: accessFilter, field: "controllerFilter")}</td>
                        
                            <td>${fieldValue(bean: accessFilter, field: "actionFilter")}</td>
                        
                            <td>${fieldValue(bean: accessFilter, field: "uriFilter")}</td>
                        
                            <td>${fieldValue(bean: accessFilter, field: "filterFor")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${accessFilterTotal}" />
            </div>
          </div></div></div>
        </div>
    </body>
</html>

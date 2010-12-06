
<%@ page import="com.mp.domain.access.AccessFilterSet" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'accessFilterSet.label', default: 'AccessFilterSet')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'accessFilterSet.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="activeTo" title="${message(code: 'accessFilterSet.activeTo.label', default: 'Active To')}" />
                        
                            <g:sortableColumn property="type" title="${message(code: 'accessFilterSet.type.label', default: 'Type')}" />
                        
                            <g:sortableColumn property="description" title="${message(code: 'accessFilterSet.description.label', default: 'Description')}" />
                        
                            <g:sortableColumn property="name" title="${message(code: 'accessFilterSet.name.label', default: 'Name')}" />
                        
                            <g:sortableColumn property="activeFrom" title="${message(code: 'accessFilterSet.activeFrom.label', default: 'Active From')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${accessFilterSetList}" status="i" var="accessFilterSet">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${accessFilterSet.id}">${fieldValue(bean: accessFilterSet, field: "id")}</g:link></td>
                        
                            <td><g:formatDate date="${accessFilterSet.activeTo}" /></td>
                        
                            <td>${fieldValue(bean: accessFilterSet, field: "type")}</td>
                        
                            <td>${fieldValue(bean: accessFilterSet, field: "description")}</td>
                        
                            <td>${fieldValue(bean: accessFilterSet, field: "name")}</td>
                        
                            <td><g:formatDate date="${accessFilterSet.activeFrom}" /></td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${accessFilterSetTotal}" />
            </div>
          </div></div></div>
        </div>
    </body>
</html>

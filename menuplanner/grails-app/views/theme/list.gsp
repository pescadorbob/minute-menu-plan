
<%@ page import="com.mp.domain.themes.Theme" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'theme.label', default: 'Theme')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body><div id="container">
  <div id="wrapper" class="clearfix">
    <div id="content-wrapper" class="clearfix">
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'theme.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="name" title="${message(code: 'theme.name.label', default: 'Name')}" />
                        
                            <g:sortableColumn property="lastModified" title="${message(code: 'theme.lastModified.label', default: 'Last Modified')}" />
                        
                            <g:sortableColumn property="activeTo" title="${message(code: 'theme.activeTo.label', default: 'Active To')}" />
                        
                            <g:sortableColumn property="controllerFilter" title="${message(code: 'theme.controllerFilter.label', default: 'Controller Filter')}" />
                        
                            <g:sortableColumn property="activeFrom" title="${message(code: 'theme.activeFrom.label', default: 'Active From')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${themeList}" status="i" var="theme">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${theme.id}">${fieldValue(bean: theme, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: theme, field: "name")}</td>
                        
                            <td><g:formatDate date="${theme.lastModified}" /></td>
                        
                            <td><g:formatDate date="${theme.activeTo}" /></td>
                        
                            <td>${fieldValue(bean: theme, field: "controllerFilter")}</td>
                        
                            <td><g:formatDate date="${theme.activeFrom}" /></td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${themeTotal}" />
            </div></div></div></div>
        </div>
    </body>
</html>

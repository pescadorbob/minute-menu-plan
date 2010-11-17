
<%@ page import="com.mp.domain.themes.PageElement" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'pageElement.label', default: 'PageElement')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'pageElement.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="name" title="${message(code: 'pageElement.name.label', default: 'Name')}" />
                        
                            <g:sortableColumn property="text" title="${message(code: 'pageElement.text.label', default: 'Text')}" />
                        
                            <g:sortableColumn property="lastModified" title="${message(code: 'pageElement.lastModified.label', default: 'Last Modified')}" />
                        
                            <g:sortableColumn property="listOrder" title="${message(code: 'pageElement.listOrder.label', default: 'List Order')}" />
                        
                            <g:sortableColumn property="controllerFilter" title="${message(code: 'pageElement.controllerFilter.label', default: 'Controller Filter')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${pageElementList}" status="i" var="pageElement">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${pageElement.id}">${fieldValue(bean: pageElement, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: pageElement, field: "name")}</td>
                        
                            <td>${fieldValue(bean: pageElement, field: "text")}</td>
                        
                            <td><g:formatDate date="${pageElement.lastModified}" /></td>
                        
                            <td>${fieldValue(bean: pageElement, field: "listOrder")}</td>
                        
                            <td>${fieldValue(bean: pageElement, field: "controllerFilter")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${pageElementTotal}" />
            </div>
        </div>
    </body>
</html>

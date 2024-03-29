
<%@ page import="com.mp.domain.Aisle" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'aisle.label', default: 'Aisle')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'aisle.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="name" title="${message(code: 'aisle.name.label', default: 'Name')}" />
                        
                            <g:sortableColumn property="ownedByUser" title="${message(code: 'aisle.ownedByUser.label', default: 'Owned By User')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${aisleList}" status="i" var="aisle">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${aisle.id}">${fieldValue(bean: aisle, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: aisle, field: "name")}</td>
                        
                            <td><g:formatBoolean boolean="${aisle.ownedByUser}" /></td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${aisleTotal}" />
            </div>
        </div>
    </body>
</html>

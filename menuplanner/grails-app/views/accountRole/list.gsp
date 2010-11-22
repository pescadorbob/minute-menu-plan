
<%@ page import="com.mp.domain.accounting.AccountRole" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'accountRole.label', default: 'AccountRole')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'accountRole.id.label', default: 'Id')}" />
                        
                            <th><g:message code="accountRole.roleFor.label" default="Role For" /></th>
                   	    
                            <g:sortableColumn property="type" title="${message(code: 'accountRole.type.label', default: 'Type')}" />
                        
                            <th><g:message code="accountRole.describes.label" default="Describes" /></th>
                   	    
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${accountRoleList}" status="i" var="accountRole">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${accountRole.id}">${fieldValue(bean: accountRole, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: accountRole, field: "roleFor")}</td>
                        
                            <td>${fieldValue(bean: accountRole, field: "type")}</td>
                        
                            <td>${fieldValue(bean: accountRole, field: "describes")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${accountRoleTotal}" />
            </div>
        </div>
    </body>
</html>

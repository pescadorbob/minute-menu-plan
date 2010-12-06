
<%@ page import="com.mp.domain.access.RoleAccess" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'roleAccess.label', default: 'RoleAccess')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'roleAccess.id.label', default: 'Id')}" />
                        
                            <th><g:message code="roleAccess.to.label" default="To" /></th>
                   	    
                            <th><g:message code="roleAccess.from.label" default="From" /></th>
                   	    
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${roleAccessList}" status="i" var="roleAccess">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${roleAccess.id}">${fieldValue(bean: roleAccess, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: roleAccess, field: "to")}</td>
                        
                            <td>${fieldValue(bean: roleAccess, field: "from")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${roleAccessTotal}" />
            </div>
          </div></div></div>
        </div>
    </body>
</html>

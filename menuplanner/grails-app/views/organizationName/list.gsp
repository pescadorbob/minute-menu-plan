
<%@ page import="com.mp.domain.party.OrganizationName" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'organizationName.label', default: 'OrganizationName')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'organizationName.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="description" title="${message(code: 'organizationName.description.label', default: 'Description')}" />
                        
                            <g:sortableColumn property="name" title="${message(code: 'organizationName.name.label', default: 'Name')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${organizationNameList}" status="i" var="organizationName">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${organizationName.id}">${fieldValue(bean: organizationName, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: organizationName, field: "description")}</td>
                        
                            <td>${fieldValue(bean: organizationName, field: "name")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${organizationNameTotal}" />
            </div>
          </div></div></div>
        </div>
    </body>
</html>

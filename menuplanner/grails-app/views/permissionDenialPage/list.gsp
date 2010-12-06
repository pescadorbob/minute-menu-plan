
<%@ page import="com.mp.domain.themes.PermissionDenialPage" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'permissionDenialPage.label', default: 'PermissionDenialPage')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'permissionDenialPage.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="name" title="${message(code: 'permissionDenialPage.name.label', default: 'Name')}" />
                        
                            <g:sortableColumn property="template" title="${message(code: 'permissionDenialPage.template.label', default: 'Template')}" />
                        
                            <g:sortableColumn property="lastModified" title="${message(code: 'permissionDenialPage.lastModified.label', default: 'Last Modified')}" />
                        
                            <g:sortableColumn property="activeTo" title="${message(code: 'permissionDenialPage.activeTo.label', default: 'Active To')}" />
                        
                            <g:sortableColumn property="activeFrom" title="${message(code: 'permissionDenialPage.activeFrom.label', default: 'Active From')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${permissionDenialPageList}" status="i" var="permissionDenialPage">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${permissionDenialPage.id}">${fieldValue(bean: permissionDenialPage, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: permissionDenialPage, field: "name")}</td>
                        
                            <td>${fieldValue(bean: permissionDenialPage, field: "template")}</td>
                        
                            <td><g:formatDate date="${permissionDenialPage.lastModified}" /></td>
                        
                            <td>${fieldValue(bean: permissionDenialPage, field: "activeTo")}</td>
                        
                            <td>${fieldValue(bean: permissionDenialPage, field: "activeFrom")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${permissionDenialPageTotal}" />
            </div>
          </div></div></div>
        </div>
    </body>
</html>

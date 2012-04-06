
<%@ page import="com.mp.domain.themes.LandingPage" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'landingPage.label', default: 'LandingPage')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'landingPage.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="redirectURI" title="${message(code: 'landingPage.redirectURI.label', default: 'Redirect URI')}" />
                        
                            <g:sortableColumn property="controllerName" title="${message(code: 'landingPage.controllerName.label', default: 'Controller Name')}" />
                        
                            <g:sortableColumn property="actionName" title="${message(code: 'landingPage.actionName.label', default: 'Action Name')}" />
                        
                            <g:sortableColumn property="name" title="${message(code: 'landingPage.name.label', default: 'Name')}" />
                        
                            <g:sortableColumn property="lastModified" title="${message(code: 'landingPage.lastModified.label', default: 'Last Modified')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${landingPageList}" status="i" var="landingPage">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${landingPage.id}">${fieldValue(bean: landingPage, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: landingPage, field: "redirectURI")}</td>
                        
                            <td>${fieldValue(bean: landingPage, field: "controllerName")}</td>
                        
                            <td>${fieldValue(bean: landingPage, field: "actionName")}</td>
                        
                            <td>${fieldValue(bean: landingPage, field: "name")}</td>
                        
                            <td><g:formatDate date="${landingPage.lastModified}" /></td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${landingPageTotal}" />
            </div>
          </div></div></div>
        </div>
    </body>
</html>

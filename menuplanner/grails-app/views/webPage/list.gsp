
<%@ page import="com.mp.domain.themes.WebPage" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'webPage.label', default: 'WebPage')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'webPage.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="name" title="${message(code: 'webPage.name.label', default: 'Name')}" />
                        
                            <g:sortableColumn property="template" title="${message(code: 'webPage.template.label', default: 'Template')}" />
                        
                            <g:sortableColumn property="lastModified" title="${message(code: 'webPage.lastModified.label', default: 'Last Modified')}" />
                        
                            <g:sortableColumn property="activeTo" title="${message(code: 'webPage.activeTo.label', default: 'Active To')}" />
                        
                            <g:sortableColumn property="activeFrom" title="${message(code: 'webPage.activeFrom.label', default: 'Active From')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${webPageList}" status="i" var="webPage">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${webPage.id}">${fieldValue(bean: webPage, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: webPage, field: "name")}</td>
                        
                            <td>${fieldValue(bean: webPage, field: "template")}</td>
                        
                            <td><g:formatDate date="${webPage.lastModified}" /></td>
                        
                            <td>${fieldValue(bean: webPage, field: "activeTo")}</td>
                        
                            <td>${fieldValue(bean: webPage, field: "activeFrom")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${webPageTotal}" />
            </div>
          </div></div></div>
        </div>
    </body>
</html>


<%@ page import="com.mp.domain.subscriptions.Content" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'content.label', default: 'Content')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'content.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="contentUrlPattern" title="${message(code: 'content.contentUrlPattern.label', default: 'Content Url Pattern')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${contentList}" status="i" var="content">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${content.id}">${fieldValue(bean: content, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: content, field: "contentUrlPattern")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${contentTotal}" />
            </div>
        </div>
    </body>
</html>

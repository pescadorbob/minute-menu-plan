
<%@ page import="com.mp.domain.subscriptions.Feature" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'feature.label', default: 'Feature')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'feature.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="activeTo" title="${message(code: 'feature.activeTo.label', default: 'Active To')}" />
                        
                            <g:sortableColumn property="rule" title="${message(code: 'feature.rule.label', default: 'Rule')}" />
                        
                            <g:sortableColumn property="name" title="${message(code: 'feature.name.label', default: 'Name')}" />
                        
                            <g:sortableColumn property="activeFrom" title="${message(code: 'feature.activeFrom.label', default: 'Active From')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${featureList}" status="i" var="feature">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${feature.id}">${fieldValue(bean: feature, field: "id")}</g:link></td>
                        
                            <td><g:formatDate date="${feature.activeTo}" /></td>
                        
                            <td>${fieldValue(bean: feature, field: "rule")}</td>
                        
                            <td>${fieldValue(bean: feature, field: "name")}</td>
                        
                            <td><g:formatDate date="${feature.activeFrom}" /></td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${featureTotal}" />
            </div>
        </div>
    </body>
</html>


<%@ page import="com.mp.domain.Metric" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'metric.label', default: 'Metric')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'metric.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="name" title="${message(code: 'metric.name.label', default: 'Name')}" />
                        
                            <g:sortableColumn property="symbol" title="${message(code: 'metric.symbol.label', default: 'Symbol')}" />
                        
                            <g:sortableColumn property="definition" title="${message(code: 'metric.definition.label', default: 'Definition')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${metricList}" status="i" var="metric">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${metric.id}">${fieldValue(bean: metric, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: metric, field: "name")}</td>
                        
                            <td>${fieldValue(bean: metric, field: "symbol")}</td>
                        
                            <td>${fieldValue(bean: metric, field: "definition")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${metricTotal}" />
            </div>
        </div>
    </body>
</html>

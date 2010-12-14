
%{--<%@ page import="com.mp.domain.subscriptions.TimeFrequencyMeasure" %>--}%
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'timeFrequencyMeasure.label', default: 'TimeFrequencyMeasure')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'timeFrequencyMeasure.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="name" title="${message(code: 'timeFrequencyMeasure.name.label', default: 'Name')}" />
                        
                            <g:sortableColumn property="metricType" title="${message(code: 'timeFrequencyMeasure.metricType.label', default: 'Metric Type')}" />
                        
                            <g:sortableColumn property="definition" title="${message(code: 'timeFrequencyMeasure.definition.label', default: 'Definition')}" />
                        
                            <g:sortableColumn property="symbol" title="${message(code: 'timeFrequencyMeasure.symbol.label', default: 'Symbol')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${timeFrequencyMeasureList}" status="i" var="timeFrequencyMeasure">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${timeFrequencyMeasure.id}">${fieldValue(bean: timeFrequencyMeasure, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: timeFrequencyMeasure, field: "name")}</td>
                        
                            <td>${fieldValue(bean: timeFrequencyMeasure, field: "metricType")}</td>
                        
                            <td>${fieldValue(bean: timeFrequencyMeasure, field: "definition")}</td>
                        
                            <td>${fieldValue(bean: timeFrequencyMeasure, field: "symbol")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${timeFrequencyMeasureTotal}" />
            </div>
        </div>
    </body>
</html>

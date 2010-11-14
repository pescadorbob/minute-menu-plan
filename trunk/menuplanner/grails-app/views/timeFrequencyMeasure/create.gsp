
<%@ page import="com.mp.domain.subscriptions.TimeFrequencyMeasure" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'timeFrequencyMeasure.label', default: 'TimeFrequencyMeasure')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${timeFrequencyMeasure}">
            <div class="errors">
                <g:renderErrors bean="${timeFrequencyMeasure}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name"><g:message code="timeFrequencyMeasure.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: timeFrequencyMeasure, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${timeFrequencyMeasure?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="metricType"><g:message code="timeFrequencyMeasure.metricType.label" default="Metric Type" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: timeFrequencyMeasure, field: 'metricType', 'errors')}">
                                    <g:select name="metricType" from="${com.mp.domain.MetricType?.values()}" value="${timeFrequencyMeasure?.metricType}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="definition"><g:message code="timeFrequencyMeasure.definition.label" default="Definition" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: timeFrequencyMeasure, field: 'definition', 'errors')}">
                                    <g:textField name="definition" value="${timeFrequencyMeasure?.definition}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="symbol"><g:message code="timeFrequencyMeasure.symbol.label" default="Symbol" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: timeFrequencyMeasure, field: 'symbol', 'errors')}">
                                    <g:textField name="symbol" value="${timeFrequencyMeasure?.symbol}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>


<%@ page import="com.mp.domain.subscriptions.TimeFrequencyMeasure" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'timeFrequencyMeasure.label', default: 'TimeFrequencyMeasure')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="timeFrequencyMeasure.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: timeFrequencyMeasure, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="timeFrequencyMeasure.name.label" default="Name" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: timeFrequencyMeasure, field: "name")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="timeFrequencyMeasure.metricType.label" default="Metric Type" /></td>
                            
                            <td valign="top" class="value">${timeFrequencyMeasure?.metricType?.encodeAsHTML()}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="timeFrequencyMeasure.definition.label" default="Definition" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: timeFrequencyMeasure, field: "definition")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="timeFrequencyMeasure.symbol.label" default="Symbol" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: timeFrequencyMeasure, field: "symbol")}</td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${timeFrequencyMeasure?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>

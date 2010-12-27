
<%@ page import="com.mp.domain.Unit" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'unit.label', default: 'Unit')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${unit}">
            <div class="errors">
                <g:renderErrors bean="${unit}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${unit?.id}" />
                <g:hiddenField name="version" value="${unit?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="name"><g:message code="unit.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: unit, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${unit?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="symbol"><g:message code="unit.symbol.label" default="Symbol" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: unit, field: 'symbol', 'errors')}">
                                    <g:textField name="symbol" value="${unit?.symbol}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="definition"><g:message code="unit.definition.label" default="Definition" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: unit, field: 'definition', 'errors')}">
                                    <g:textField name="definition" value="${unit?.definition}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
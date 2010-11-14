
<%@ page import="com.mp.domain.Item" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'item.label', default: 'Item')}" />
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
            <g:hasErrors bean="${item}">
            <div class="errors">
                <g:renderErrors bean="${item}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${item?.id}" />
                <g:hiddenField name="version" value="${item?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="suggestedAisle"><g:message code="item.suggestedAisle.label" default="Suggested Aisle" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: item, field: 'suggestedAisle', 'errors')}">
                                    <g:select name="suggestedAisle.id" from="${com.mp.domain.Aisle.list()}" optionKey="id" value="${item?.suggestedAisle?.id}" noSelection="['null': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="isAlcoholic"><g:message code="item.isAlcoholic.label" default="Is Alcoholic" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: item, field: 'isAlcoholic', 'errors')}">
                                    <g:checkBox name="isAlcoholic" value="${item?.isAlcoholic}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="shareWithCommunity"><g:message code="item.shareWithCommunity.label" default="Share With Community" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: item, field: 'shareWithCommunity', 'errors')}">
                                    <g:checkBox name="shareWithCommunity" value="${item?.shareWithCommunity}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="density"><g:message code="item.density.label" default="Density" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: item, field: 'density', 'errors')}">
                                    <g:textField name="density" value="${fieldValue(bean: item, field: 'density')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="name"><g:message code="item.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: item, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${item?.name}" />
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


<%@ page import="com.mp.domain.themes.PageElement" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'pageElement.label', default: 'PageElement')}" />
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
            <g:hasErrors bean="${pageElement}">
            <div class="errors">
                <g:renderErrors bean="${pageElement}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${pageElement?.id}" />
                <g:hiddenField name="version" value="${pageElement?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="name"><g:message code="pageElement.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: pageElement, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${pageElement?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="text"><g:message code="pageElement.text.label" default="Text" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: pageElement, field: 'text', 'errors')}">
                                    <g:textArea name="text" cols="40" rows="5" value="${pageElement?.text}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="lastModified"><g:message code="pageElement.lastModified.label" default="Last Modified" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: pageElement, field: 'lastModified', 'errors')}">
                                    <g:datePicker name="lastModified" precision="day" value="${pageElement?.lastModified}" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="listOrder"><g:message code="pageElement.listOrder.label" default="List Order" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: pageElement, field: 'listOrder', 'errors')}">
                                    <g:textField name="listOrder" value="${fieldValue(bean: pageElement, field: 'listOrder')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="controllerFilter"><g:message code="pageElement.controllerFilter.label" default="Controller Filter" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: pageElement, field: 'controllerFilter', 'errors')}">
                                    <g:textField name="controllerFilter" value="${pageElement?.controllerFilter}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="contextRule"><g:message code="pageElement.contextRule.label" default="Context Rule" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: pageElement, field: 'contextRule', 'errors')}">
                                    <g:textField name="contextRule" value="${pageElement?.contextRule}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="elementFor"><g:message code="pageElement.elementFor.label" default="Element For" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: pageElement, field: 'elementFor', 'errors')}">
                                    <g:select name="elementFor.id" from="${com.mp.domain.themes.Theme.list()}" optionKey="id" value="${pageElement?.elementFor?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="isTemplate"><g:message code="pageElement.isTemplate.label" default="Is Template" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: pageElement, field: 'isTemplate', 'errors')}">
                                    <g:checkBox name="isTemplate" value="${pageElement?.isTemplate}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="actionFilter"><g:message code="pageElement.actionFilter.label" default="Action Filter" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: pageElement, field: 'actionFilter', 'errors')}">
                                    <g:textField name="actionFilter" value="${pageElement?.actionFilter}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="uriFilter"><g:message code="pageElement.uriFilter.label" default="Uri Filter" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: pageElement, field: 'uriFilter', 'errors')}">
                                    <g:textField name="uriFilter" value="${pageElement?.uriFilter}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="location"><g:message code="pageElement.location.label" default="Location" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: pageElement, field: 'location', 'errors')}">
                                    <g:select name="location" from="${com.mp.domain.ElementLocation?.values()}" value="${pageElement?.location}"  />
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

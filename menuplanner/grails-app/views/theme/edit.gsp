
<%@ page import="com.mp.domain.themes.Theme" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'theme.label', default: 'Theme')}" />
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
            <g:hasErrors bean="${theme}">
            <div class="errors">
                <g:renderErrors bean="${theme}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${theme?.id}" />
                <g:hiddenField name="version" value="${theme?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="name"><g:message code="theme.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: theme, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${theme?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="lastModified"><g:message code="theme.lastModified.label" default="Last Modified" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: theme, field: 'lastModified', 'errors')}">
                                    <g:datePicker name="lastModified" precision="day" value="${theme?.lastModified}" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="activeTo"><g:message code="theme.activeTo.label" default="Active To" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: theme, field: 'activeTo', 'errors')}">
                                    <g:datePicker name="activeTo" precision="day" value="${theme?.activeTo}" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="controllerFilter"><g:message code="theme.controllerFilter.label" default="Controller Filter" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: theme, field: 'controllerFilter', 'errors')}">
                                    <g:textField name="controllerFilter" value="${theme?.controllerFilter}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="activeFrom"><g:message code="theme.activeFrom.label" default="Active From" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: theme, field: 'activeFrom', 'errors')}">
                                    <g:datePicker name="activeFrom" precision="day" value="${theme?.activeFrom}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="actionFilter"><g:message code="theme.actionFilter.label" default="Action Filter" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: theme, field: 'actionFilter', 'errors')}">
                                    <g:textField name="actionFilter" value="${theme?.actionFilter}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="uriFilter"><g:message code="theme.uriFilter.label" default="Uri Filter" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: theme, field: 'uriFilter', 'errors')}">
                                    <g:textField name="uriFilter" value="${theme?.uriFilter}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="pageElements"><g:message code="theme.pageElements.label" default="Page Elements" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: theme, field: 'pageElements', 'errors')}">
                                    
<ul>
<g:each in="${theme?.pageElements?}" var="p">
    <li><g:link controller="pageElement" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="pageElement" action="create" params="['theme.id': theme?.id]">${message(code: 'default.add.label', args: [message(code: 'pageElement.label', default: 'PageElement')])}</g:link>

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

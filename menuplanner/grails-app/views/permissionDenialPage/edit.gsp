
<%@ page import="com.mp.domain.themes.PermissionDenialPage" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'permissionDenialPage.label', default: 'PermissionDenialPage')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
<div id="container">
  <div id="wrapper" class="clearfix">
    <div id="content-wrapper" class="clearfix">
      <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <div class="headbox">
              <h3><g:message code="default.edit.label" args="[entityName]" /></h3>
            </div>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${permissionDenialPage}">
            <div class="errors">
                <g:renderErrors bean="${permissionDenialPage}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${permissionDenialPage?.id}" />
                <g:hiddenField name="version" value="${permissionDenialPage?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="name"><g:message code="permissionDenialPage.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: permissionDenialPage, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${permissionDenialPage?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="template"><g:message code="permissionDenialPage.template.label" default="Template" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: permissionDenialPage, field: 'template', 'errors')}">
                                    <g:textArea name="template" cols="40" rows="5" value="${permissionDenialPage?.template}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="lastModified"><g:message code="permissionDenialPage.lastModified.label" default="Last Modified" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: permissionDenialPage, field: 'lastModified', 'errors')}">
                                    <g:datePicker name="lastModified" precision="day" value="${permissionDenialPage?.lastModified}" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="activeTo"><g:message code="permissionDenialPage.activeTo.label" default="Active To" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: permissionDenialPage, field: 'activeTo', 'errors')}">
                                    <g:textField name="activeTo" value="${permissionDenialPage?.activeTo}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="activeFrom"><g:message code="permissionDenialPage.activeFrom.label" default="Active From" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: permissionDenialPage, field: 'activeFrom', 'errors')}">
                                    <g:textField name="activeFrom" value="${permissionDenialPage?.activeFrom}" />
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
        </div>
        </div>
        </div>
    </body>
</html>

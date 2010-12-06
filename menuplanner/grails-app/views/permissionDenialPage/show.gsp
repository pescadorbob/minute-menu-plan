
<%@ page import="com.mp.domain.themes.PermissionDenialPage" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'permissionDenialPage.label', default: 'PermissionDenialPage')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
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
            <h3><g:message code="default.show.label" args="[entityName]" /></h3>
            </div>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="permissionDenialPage.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: permissionDenialPage, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="permissionDenialPage.name.label" default="Name" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: permissionDenialPage, field: "name")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="permissionDenialPage.template.label" default="Template" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: permissionDenialPage, field: "template")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="permissionDenialPage.lastModified.label" default="Last Modified" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${permissionDenialPage?.lastModified}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="permissionDenialPage.activeTo.label" default="Active To" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: permissionDenialPage, field: "activeTo")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="permissionDenialPage.activeFrom.label" default="Active From" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: permissionDenialPage, field: "activeFrom")}</td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${permissionDenialPage?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </div></div></div>

    </body>
</html>

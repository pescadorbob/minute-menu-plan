
<%@ page import="com.mp.domain.access.AccessFilter" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'accessFilter.label', default: 'AccessFilter')}" />
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
                            <td valign="top" class="name"><g:message code="accessFilter.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: accessFilter, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="accessFilter.name.label" default="Name" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: accessFilter, field: "name")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="accessFilter.controllerFilter.label" default="Controller Filter" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: accessFilter, field: "controllerFilter")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="accessFilter.actionFilter.label" default="Action Filter" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: accessFilter, field: "actionFilter")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="accessFilter.uriFilter.label" default="Uri Filter" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: accessFilter, field: "uriFilter")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="accessFilter.filterFor.label" default="Filter For" /></td>
                            
                            <td valign="top" class="value"><g:link controller="accessFilterSet" action="show"
                                    id="${accessFilter?.filterFor?.id}">
                              ${accessFilter?.filterFor?.name?.encodeAsHTML()}:
                              ${accessFilter?.filterFor?.type?.encodeAsHTML()}
                            </g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="accessFilter.description.label" default="Description" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: accessFilter, field: "description")}</td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${accessFilter?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </div></div></div>

    </body>
</html>

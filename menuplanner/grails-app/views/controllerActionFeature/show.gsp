
<%@ page import="com.mp.domain.subscriptions.ControllerActionFeature" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'controllerActionFeature.label', default: 'ControllerActionFeature')}" />
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
                            <td valign="top" class="name"><g:message code="controllerActionFeature.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: controllerActionFeature, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="controllerActionFeature.rule.label" default="Rule" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: controllerActionFeature, field: "rule")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="controllerActionFeature.activeTo.label" default="Active To" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${controllerActionFeature?.activeTo}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="controllerActionFeature.controllerFilter.label" default="Controller Filter" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: controllerActionFeature, field: "controllerFilter")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="controllerActionFeature.actionFilter.label" default="Action Filter" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: controllerActionFeature, field: "actionFilter")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="controllerActionFeature.uriFilter.label" default="Uri Filter" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: controllerActionFeature, field: "uriFilter")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="controllerActionFeature.activeFrom.label" default="Active From" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${controllerActionFeature?.activeFrom}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="controllerActionFeature.description.label" default="Description" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: controllerActionFeature, field: "description")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="controllerActionFeature.name.label" default="Name" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: controllerActionFeature, field: "name")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="controllerActionFeature.usedToDefine.label" default="Used To Define" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${controllerActionFeature.usedToDefine}" var="u">
                                    <li><g:link controller="featuredOfferingApplicability" action="show" id="${u.id}">${u?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${controllerActionFeature?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </div></div></div>

    </body>
</html>

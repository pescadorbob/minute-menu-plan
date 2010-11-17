
<%@ page import="com.mp.domain.themes.PageElement" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'pageElement.label', default: 'PageElement')}" />
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
                            <td valign="top" class="name"><g:message code="pageElement.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: pageElement, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="pageElement.name.label" default="Name" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: pageElement, field: "name")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="pageElement.text.label" default="Text" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: pageElement, field: "text")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="pageElement.lastModified.label" default="Last Modified" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${pageElement?.lastModified}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="pageElement.listOrder.label" default="List Order" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: pageElement, field: "listOrder")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="pageElement.controllerFilter.label" default="Controller Filter" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: pageElement, field: "controllerFilter")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="pageElement.contextRule.label" default="Context Rule" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: pageElement, field: "contextRule")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="pageElement.elementFor.label" default="Element For" /></td>
                            
                            <td valign="top" class="value"><g:link controller="theme" action="show" id="${pageElement?.elementFor?.id}">${pageElement?.elementFor?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="pageElement.isTemplate.label" default="Is Template" /></td>
                            
                            <td valign="top" class="value"><g:formatBoolean boolean="${pageElement?.isTemplate}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="pageElement.actionFilter.label" default="Action Filter" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: pageElement, field: "actionFilter")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="pageElement.uriFilter.label" default="Uri Filter" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: pageElement, field: "uriFilter")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="pageElement.location.label" default="Location" /></td>
                            
                            <td valign="top" class="value">${pageElement?.location?.encodeAsHTML()}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="pageElement.imageDir.label" default="Image Dir" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: pageElement, field: "imageDir")}</td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${pageElement?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>

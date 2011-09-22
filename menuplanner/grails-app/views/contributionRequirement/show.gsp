
<%@ page import="com.mp.domain.subscriptions.ContributionRequirement" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'contributionRequirement.label', default: 'ContributionRequirement')}" />
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
                            <td valign="top" class="name"><g:message code="contributionRequirement.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: contributionRequirement, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="contributionRequirement.activeTo.label" default="Active To" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${contributionRequirement?.activeTo}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="contributionRequirement.description.label" default="Description" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: contributionRequirement, field: "description")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="contributionRequirement.action.label" default="Action Name" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: contributionRequirement, field: "actionName")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="contributionRequirement.activeFrom.label" default="Active From" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${contributionRequirement?.activeFrom}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="contributionRequirement.controller.label" default="Controller Name" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: contributionRequirement, field: "controllerName")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="contributionRequirement.name.label" default="Name" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: contributionRequirement, field: "name")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="contributionRequirement.pricingFor.label" default="Pricing For" /></td>
                            
                            <td valign="top" class="value"><g:link controller="productOffering" action="show" id="${contributionRequirement?.pricingFor?.id}">${contributionRequirement?.pricingFor?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="contributionRequirement.value.label" default="Value" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: contributionRequirement, field: "value")}</td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${contributionRequirement?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </div></div></div>

    </body>
</html>


<%@ page import="com.mp.domain.subscriptions.RecurringCharge" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'recurringCharge.label', default: 'RecurringCharge')}" />
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
                            <td valign="top" class="name"><g:message code="recurringCharge.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: recurringCharge, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="recurringCharge.recurrence.label" default="Recurrence" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: recurringCharge, field: "recurrence")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="recurringCharge.value.label" default="Value" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: recurringCharge, field: "value")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="recurringCharge.activeTo.label" default="Active To" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${recurringCharge?.activeTo}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="recurringCharge.description.label" default="Description" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: recurringCharge, field: "description")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="recurringCharge.pricingFor.label" default="Pricing For" /></td>
                            
                            <td valign="top" class="value"><g:link controller="productOffering" action="show" id="${recurringCharge?.pricingFor?.id}">${recurringCharge?.pricingFor?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="recurringCharge.name.label" default="Name" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: recurringCharge, field: "name")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="recurringCharge.activeFrom.label" default="Active From" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${recurringCharge?.activeFrom}" /></td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${recurringCharge?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>

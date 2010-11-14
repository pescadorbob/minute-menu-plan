
<%@ page import="com.mp.domain.subscriptions.RecurringCharge" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'recurringCharge.label', default: 'RecurringCharge')}" />
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
            <g:hasErrors bean="${recurringCharge}">
            <div class="errors">
                <g:renderErrors bean="${recurringCharge}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${recurringCharge?.id}" />
                <g:hiddenField name="version" value="${recurringCharge?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="recurrence"><g:message code="recurringCharge.recurrence.label" default="Recurrence" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: recurringCharge, field: 'recurrence', 'errors')}">
                                    <g:textField name="recurrence" value="${recurringCharge?.recurrence}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="value"><g:message code="recurringCharge.value.label" default="Value" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: recurringCharge, field: 'value', 'errors')}">
                                    <g:textField name="value" value="${fieldValue(bean: recurringCharge, field: 'value')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="activeTo"><g:message code="recurringCharge.activeTo.label" default="Active To" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: recurringCharge, field: 'activeTo', 'errors')}">
                                    <g:datePicker name="activeTo" precision="day" value="${recurringCharge?.activeTo}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="description"><g:message code="recurringCharge.description.label" default="Description" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: recurringCharge, field: 'description', 'errors')}">
                                    <g:textField name="description" value="${recurringCharge?.description}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="pricingFor"><g:message code="recurringCharge.pricingFor.label" default="Pricing For" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: recurringCharge, field: 'pricingFor', 'errors')}">
                                    <g:select name="pricingFor.id" from="${com.mp.domain.subscriptions.ProductOffering.list()}" optionKey="id" value="${recurringCharge?.pricingFor?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="name"><g:message code="recurringCharge.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: recurringCharge, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${recurringCharge?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="activeFrom"><g:message code="recurringCharge.activeFrom.label" default="Active From" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: recurringCharge, field: 'activeFrom', 'errors')}">
                                    <g:datePicker name="activeFrom" precision="day" value="${recurringCharge?.activeFrom}"  />
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

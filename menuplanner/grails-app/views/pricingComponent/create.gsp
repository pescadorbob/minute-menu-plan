
<%@ page import="com.mp.domain.subscriptions.PricingComponent" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'pricingComponent.label', default: 'PricingComponent')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${pricingComponent}">
            <div class="errors">
                <g:renderErrors bean="${pricingComponent}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="value"><g:message code="pricingComponent.value.label" default="Value" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: pricingComponent, field: 'value', 'errors')}">
                                    <g:textField name="value" value="${fieldValue(bean: pricingComponent, field: 'value')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="activeTo"><g:message code="pricingComponent.activeTo.label" default="Active To" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: pricingComponent, field: 'activeTo', 'errors')}">
                                    <g:datePicker name="activeTo" precision="day" value="${pricingComponent?.activeTo}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="description"><g:message code="pricingComponent.description.label" default="Description" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: pricingComponent, field: 'description', 'errors')}">
                                    <g:textField name="description" value="${pricingComponent?.description}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="pricingFor"><g:message code="pricingComponent.pricingFor.label" default="Pricing For" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: pricingComponent, field: 'pricingFor', 'errors')}">
                                    <g:select name="pricingFor.id" from="${com.mp.domain.subscriptions.ProductOffering.list()}" optionKey="id" value="${pricingComponent?.pricingFor?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name"><g:message code="pricingComponent.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: pricingComponent, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${pricingComponent?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="activeFrom"><g:message code="pricingComponent.activeFrom.label" default="Active From" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: pricingComponent, field: 'activeFrom', 'errors')}">
                                    <g:datePicker name="activeFrom" precision="day" value="${pricingComponent?.activeFrom}"  />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>

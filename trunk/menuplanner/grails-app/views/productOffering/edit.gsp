
<%@ page import="com.mp.domain.subscriptions.ProductOffering" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'productOffering.label', default: 'ProductOffering')}" />
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
            <g:hasErrors bean="${productOffering}">
            <div class="errors">
                <g:renderErrors bean="${productOffering}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${productOffering?.id}" />
                <g:hiddenField name="version" value="${productOffering?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="pricing"><g:message code="productOffering.pricing.label" default="Pricing" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: productOffering, field: 'pricing', 'errors')}">
                                    
<ul>
<g:each in="${productOffering?.pricing?}" var="p">
    <li><g:link controller="pricingComponent" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="pricingComponent" action="create" params="['productOffering.id': productOffering?.id]">${message(code: 'default.add.label', args: [message(code: 'pricingComponent.label', default: 'PricingComponent')])}</g:link>

                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="activeTo"><g:message code="productOffering.activeTo.label" default="Active To" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: productOffering, field: 'activeTo', 'errors')}">
                                    <g:datePicker name="activeTo" precision="day" value="${productOffering?.activeTo}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="applicableFeatures"><g:message code="productOffering.applicableFeatures.label" default="Applicable Features" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: productOffering, field: 'applicableFeatures', 'errors')}">
                                    
<ul>
<g:each in="${productOffering?.applicableFeatures?}" var="a">
    <li><g:link controller="featuredOfferingApplicability" action="show" id="${a.id}">${a?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="featuredOfferingApplicability" action="create" params="['productOffering.id': productOffering?.id]">${message(code: 'default.add.label', args: [message(code: 'featuredOfferingApplicability.label', default: 'FeaturedOfferingApplicability')])}</g:link>

                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="name"><g:message code="productOffering.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: productOffering, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${productOffering?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="activeFrom"><g:message code="productOffering.activeFrom.label" default="Active From" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: productOffering, field: 'activeFrom', 'errors')}">
                                    <g:datePicker name="activeFrom" precision="day" value="${productOffering?.activeFrom}"  />
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

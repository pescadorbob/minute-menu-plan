
<%@ page import="com.mp.domain.subscriptions.ContributionRequirement" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'contributionRequirement.label', default: 'ContributionRequirement')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
<div id="container">
  <div id="wrapper" class="clearfix">
    <div id="content-wrapper" class="clearfix">
      <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
          <div class="headbox">
            <h3><g:message code="default.create.label" args="[entityName]" /></h3>
            </div>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${contributionRequirement}">
            <div class="errors">
                <g:renderErrors bean="${contributionRequirement}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="activeTo"><g:message code="contributionRequirement.activeTo.label" default="Active To" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: contributionRequirement, field: 'activeTo', 'errors')}">
                                    <g:datePicker name="activeTo" precision="day" value="${contributionRequirement?.activeTo}" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="description"><g:message code="contributionRequirement.description.label" default="Description" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: contributionRequirement, field: 'description', 'errors')}">
                                    <g:textField name="description" value="${contributionRequirement?.description}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="actionName"><g:message code="contributionRequirement.actionName.label" default="Action Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: contributionRequirement, field: 'actionName', 'errors')}">
                                    <g:textField name="actionName" value="${contributionRequirement?.actionName}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="activeFrom"><g:message code="contributionRequirement.activeFrom.label" default="Active From" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: contributionRequirement, field: 'activeFrom', 'errors')}">
                                    <g:datePicker name="activeFrom" precision="day" value="${contributionRequirement?.activeFrom}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="controller"><g:message code="contributionRequirement.controller.label" default="Controller" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: contributionRequirement, field: 'controller', 'errors')}">
                                    <g:textField name="controller" value="${contributionRequirement?.controllerName}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name"><g:message code="contributionRequirement.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: contributionRequirement, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${contributionRequirement?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="pricingFor"><g:message code="contributionRequirement.pricingFor.label" default="Pricing For" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: contributionRequirement, field: 'pricingFor', 'errors')}">
                                    <g:select name="pricingFor.id" from="${com.mp.domain.subscriptions.ProductOffering.list()}" optionKey="id" value="${contributionRequirement?.pricingFor?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="value"><g:message code="contributionRequirement.value.label" default="Value" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: contributionRequirement, field: 'value', 'errors')}">
                                    <g:textField name="value" value="${fieldValue(bean: contributionRequirement, field: 'value')}" />
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
        </div>
        </div>
        </div>
    </body>
</html>


<%@ page import="com.mp.domain.themes.SubscriptionOfferingPage" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'subscriptionOfferingPage.label', default: 'SubscriptionOfferingPage')}" />
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
            <g:hasErrors bean="${subscriptionOfferingPage}">
            <div class="errors">
                <g:renderErrors bean="${subscriptionOfferingPage}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name"><g:message code="subscriptionOfferingPage.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: subscriptionOfferingPage, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${subscriptionOfferingPage?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="template"><g:message code="subscriptionOfferingPage.template.label" default="Template" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: subscriptionOfferingPage, field: 'template', 'errors')}">
                                    <g:textArea name="template" cols="40" rows="5" value="${subscriptionOfferingPage?.template}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="lastModified"><g:message code="subscriptionOfferingPage.lastModified.label" default="Last Modified" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: subscriptionOfferingPage, field: 'lastModified', 'errors')}">
                                    <g:datePicker name="lastModified" precision="day" value="${subscriptionOfferingPage?.lastModified}" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="activeTo"><g:message code="subscriptionOfferingPage.activeTo.label" default="Active To" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: subscriptionOfferingPage, field: 'activeTo', 'errors')}">
                                    <g:textField name="activeTo" value="${subscriptionOfferingPage?.activeTo}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="activeFrom"><g:message code="subscriptionOfferingPage.activeFrom.label" default="Active From" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: subscriptionOfferingPage, field: 'activeFrom', 'errors')}">
                                    <g:textField name="activeFrom" value="${subscriptionOfferingPage?.activeFrom}" />
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


<%@ page import="com.mp.domain.subscriptions.Subscription" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'subscription.label', default: 'Subscription')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
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
              <h3><g:message code="default.edit.label" args="[entityName]" /></h3>
            </div>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${subscription}">
            <div class="errors">
                <g:renderErrors bean="${subscription}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${subscription?.id}" />
                <g:hiddenField name="version" value="${subscription?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="originalProductOffering"><g:message code="subscription.originalProductOffering.label" default="Original Product Offering" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: subscription, field: 'originalProductOffering', 'errors')}">
                                    <g:textField name="originalProductOffering" value="${subscription?.originalProductOffering}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="subscriptionFor"><g:message code="subscription.subscriptionFor.label" default="Subscription For" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: subscription, field: 'subscriptionFor', 'errors')}">
                                    <g:select name="subscriptionFor.id" from="${com.mp.domain.party.Subscriber.list()}" optionKey="id" value="${subscription?.subscriptionFor?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="activeThru"><g:message code="subscription.activeThru.label" default="Active Thru" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: subscription, field: 'activeThru', 'errors')}">
                                    <g:datePicker name="activeThru" precision="day" value="${subscription?.activeThru}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="activeFrom"><g:message code="subscription.activeFrom.label" default="Active From" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: subscription, field: 'activeFrom', 'errors')}">
                                    <g:datePicker name="activeFrom" precision="day" value="${subscription?.activeFrom}"  />
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
        </div>
        </div>
        </div>
    </body>
</html>

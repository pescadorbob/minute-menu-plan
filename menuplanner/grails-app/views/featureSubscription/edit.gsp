
<%@ page import="com.mp.domain.subscriptions.FeatureSubscription" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'featureSubscription.label', default: 'FeatureSubscription')}" />
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
            <g:hasErrors bean="${featureSubscription}">
            <div class="errors">
                <g:renderErrors bean="${featureSubscription}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${featureSubscription?.id}" />
                <g:hiddenField name="version" value="${featureSubscription?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="originalProductOffering"><g:message code="featureSubscription.originalProductOffering.label" default="Original Product Offering" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: featureSubscription, field: 'originalProductOffering', 'errors')}">
                                    <g:textField name="originalProductOffering" value="${featureSubscription?.originalProductOffering}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="subscriptionFor"><g:message code="featureSubscription.subscriptionFor.label" default="Subscription For" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: featureSubscription, field: 'subscriptionFor', 'errors')}">
                                    <g:select name="subscriptionFor.id" from="${com.mp.domain.party.Subscriber.list()}" optionKey="id" value="${featureSubscription?.subscriptionFor?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="subscribedFeature"><g:message code="featureSubscription.subscribedFeature.label" default="Subscribed Feature" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: featureSubscription, field: 'subscribedFeature', 'errors')}">
                                    <g:select name="subscribedFeature.id" from="${com.mp.domain.subscriptions.Feature.list()}" optionKey="id"
                                            optionValue="${{it.name?.toUpperCase()}}" value="${featureSubscription?.subscribedFeature?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="activeTo"><g:message code="featureSubscription.activeTo.label" default="Active Thru" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: featureSubscription, field: 'activeTo', 'errors')}">
                                    <g:datePicker name="activeTo" precision="day" value="${featureSubscription?.activeTo}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="activeFrom"><g:message code="featureSubscription.activeFrom.label" default="Active From" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: featureSubscription, field: 'activeFrom', 'errors')}">
                                    <g:datePicker name="activeFrom" precision="day" value="${featureSubscription?.activeFrom}"  />
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

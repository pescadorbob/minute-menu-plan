
<%@ page import="com.mp.domain.subscriptions.FeatureSubscription" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'featureSubscription.label', default: 'FeatureSubscription')}" />
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
            <g:hasErrors bean="${featureSubscription}">
            <div class="errors">
                <g:renderErrors bean="${featureSubscription}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="originatingProduct"><g:message code="featureSubscription.originatingProduct.label" default="Originating Product" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: featureSubscription, field: 'originatingProduct', 'errors')}">
                                    <g:select name="originatingProduct.id" from="${com.mp.domain.subscriptions.ProductOffering.list()}" optionKey="id" value="${featureSubscription?.originatingProduct?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="rule"><g:message code="featureSubscription.rule.label" default="Rule" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: featureSubscription, field: 'rule', 'errors')}">
                                    <g:textField name="rule" value="${featureSubscription?.rule}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="subscribedFeature"><g:message code="featureSubscription.subscribedFeature.label" default="Subscribed Feature" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: featureSubscription, field: 'subscribedFeature', 'errors')}">
                                    <g:select name="subscribedFeature.id" from="${com.mp.domain.subscriptions.Feature.list()}" optionKey="id" value="${featureSubscription?.subscribedFeature?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="activeThru"><g:message code="featureSubscription.activeThru.label" default="Active Thru" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: featureSubscription, field: 'activeThru', 'errors')}">
                                    <g:datePicker name="activeThru" precision="day" value="${featureSubscription?.activeThru}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name"><g:message code="featureSubscription.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: featureSubscription, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${featureSubscription?.name}" />
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
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>

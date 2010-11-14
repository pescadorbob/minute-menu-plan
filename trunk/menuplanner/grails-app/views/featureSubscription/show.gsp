
<%@ page import="com.mp.domain.subscriptions.FeatureSubscription" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'featureSubscription.label', default: 'FeatureSubscription')}" />
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
                            <td valign="top" class="name"><g:message code="featureSubscription.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: featureSubscription, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="featureSubscription.originatingProduct.label" default="Originating Product" /></td>
                            
                            <td valign="top" class="value"><g:link controller="productOffering" action="show" id="${featureSubscription?.originatingProduct?.id}">${featureSubscription?.originatingProduct?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="featureSubscription.rule.label" default="Rule" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: featureSubscription, field: "rule")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="featureSubscription.subscribedFeature.label" default="Subscribed Feature" /></td>
                            
                            <td valign="top" class="value"><g:link controller="feature" action="show" id="${featureSubscription?.subscribedFeature?.id}">${featureSubscription?.subscribedFeature?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="featureSubscription.activeThru.label" default="Active Thru" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${featureSubscription?.activeThru}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="featureSubscription.name.label" default="Name" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: featureSubscription, field: "name")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="featureSubscription.activeFrom.label" default="Active From" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${featureSubscription?.activeFrom}" /></td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${featureSubscription?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>

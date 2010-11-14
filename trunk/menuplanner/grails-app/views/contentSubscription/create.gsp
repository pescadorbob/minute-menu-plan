
<%@ page import="com.mp.domain.subscriptions.ContentSubscription" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'contentSubscription.label', default: 'ContentSubscription')}" />
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
            <g:hasErrors bean="${contentSubscription}">
            <div class="errors">
                <g:renderErrors bean="${contentSubscription}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="originatingProduct"><g:message code="contentSubscription.originatingProduct.label" default="Originating Product" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: contentSubscription, field: 'originatingProduct', 'errors')}">
                                    <g:select name="originatingProduct.id" from="${com.mp.domain.subscriptions.ProductOffering.list()}" optionKey="id" value="${contentSubscription?.originatingProduct?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="content"><g:message code="contentSubscription.content.label" default="Content" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: contentSubscription, field: 'content', 'errors')}">
                                    <g:select name="content.id" from="${com.mp.domain.subscriptions.Content.list()}" optionKey="id" value="${contentSubscription?.content?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="activeThru"><g:message code="contentSubscription.activeThru.label" default="Active Thru" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: contentSubscription, field: 'activeThru', 'errors')}">
                                    <g:datePicker name="activeThru" precision="day" value="${contentSubscription?.activeThru}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name"><g:message code="contentSubscription.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: contentSubscription, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${contentSubscription?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="activeFrom"><g:message code="contentSubscription.activeFrom.label" default="Active From" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: contentSubscription, field: 'activeFrom', 'errors')}">
                                    <g:datePicker name="activeFrom" precision="day" value="${contentSubscription?.activeFrom}"  />
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

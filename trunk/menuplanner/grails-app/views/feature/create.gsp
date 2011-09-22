
<%@ page import="com.mp.domain.subscriptions.Feature" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'feature.label', default: 'Feature')}" />
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
            <g:hasErrors bean="${feature}">
            <div class="errors">
                <g:renderErrors bean="${feature}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="activeTo"><g:message code="feature.activeTo.label" default="Active To" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: feature, field: 'activeTo', 'errors')}">
                                    <g:datePicker name="activeTo" precision="day" value="${feature?.activeTo}"  />
                                </td>
                            </tr>
                        

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name"><g:message code="feature.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: feature, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${feature?.name}" />
                                </td>
                            </tr>
                        
                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="description"><g:message code="feature.description.label" default="Description" /></label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: feature, field: 'description', 'errors')}">
                                <g:textField name="description" value="${feature?.description}" />
                            </td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="rule"><g:message code="feature.rule.label" default="Rule" /></label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: feature, field: 'rule', 'errors')}">
                                <g:textField name="rule" value="${feature?.rule}" />
                            </td>
                        </tr>
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="activeFrom"><g:message code="feature.activeFrom.label" default="Active From" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: feature, field: 'activeFrom', 'errors')}">
                                    <g:datePicker name="activeFrom" precision="day" value="${feature?.activeFrom}"  />
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

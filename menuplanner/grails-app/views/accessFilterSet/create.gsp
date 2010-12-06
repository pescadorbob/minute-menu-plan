
<%@ page import="com.mp.domain.access.AccessFilterSet" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'accessFilterSet.label', default: 'AccessFilterSet')}" />
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
            <g:hasErrors bean="${accessFilterSet}">
            <div class="errors">
                <g:renderErrors bean="${accessFilterSet}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="activeTo"><g:message code="accessFilterSet.activeTo.label" default="Active To" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: accessFilterSet, field: 'activeTo', 'errors')}">
                                    <g:datePicker name="activeTo" precision="day" value="${accessFilterSet?.activeTo}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="type"><g:message code="accessFilterSet.type.label" default="Type" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: accessFilterSet, field: 'type', 'errors')}">
                                    <g:select name="type" from="${com.mp.domain.access.AccessFilterType?.values()}" value="${accessFilterSet?.type}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="description"><g:message code="accessFilterSet.description.label" default="Description" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: accessFilterSet, field: 'description', 'errors')}">
                                    <g:textField name="description" value="${accessFilterSet?.description}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name"><g:message code="accessFilterSet.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: accessFilterSet, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${accessFilterSet?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="activeFrom"><g:message code="accessFilterSet.activeFrom.label" default="Active From" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: accessFilterSet, field: 'activeFrom', 'errors')}">
                                    <g:datePicker name="activeFrom" precision="day" value="${accessFilterSet?.activeFrom}"  />
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

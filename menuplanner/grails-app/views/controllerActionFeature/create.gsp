
<%@ page import="com.mp.domain.subscriptions.ControllerActionFeature" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'controllerActionFeature.label', default: 'ControllerActionFeature')}" />
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
            <g:hasErrors bean="${controllerActionFeature}">
            <div class="errors">
                <g:renderErrors bean="${controllerActionFeature}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="rule"><g:message code="controllerActionFeature.rule.label" default="Rule" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: controllerActionFeature, field: 'rule', 'errors')}">
                                    <g:textField name="rule" value="${controllerActionFeature?.rule}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="activeTo"><g:message code="controllerActionFeature.activeTo.label" default="Active To" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: controllerActionFeature, field: 'activeTo', 'errors')}">
                                    <g:datePicker name="activeTo" precision="day" value="${controllerActionFeature?.activeTo}" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="controllerFilter"><g:message code="controllerActionFeature.controllerFilter.label" default="Controller Filter" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: controllerActionFeature, field: 'controllerFilter', 'errors')}">
                                    <g:textField name="controllerFilter" value="${controllerActionFeature?.controllerFilter}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="actionFilter"><g:message code="controllerActionFeature.actionFilter.label" default="Action Filter" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: controllerActionFeature, field: 'actionFilter', 'errors')}">
                                    <g:textField name="actionFilter" value="${controllerActionFeature?.actionFilter}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="uriFilter"><g:message code="controllerActionFeature.uriFilter.label" default="Uri Filter" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: controllerActionFeature, field: 'uriFilter', 'errors')}">
                                    <g:textField name="uriFilter" value="${controllerActionFeature?.uriFilter}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="activeFrom"><g:message code="controllerActionFeature.activeFrom.label" default="Active From" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: controllerActionFeature, field: 'activeFrom', 'errors')}">
                                    <g:datePicker name="activeFrom" precision="day" value="${controllerActionFeature?.activeFrom}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="description"><g:message code="controllerActionFeature.description.label" default="Description" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: controllerActionFeature, field: 'description', 'errors')}">
                                    <g:textField name="description" value="${controllerActionFeature?.description}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name"><g:message code="controllerActionFeature.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: controllerActionFeature, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${controllerActionFeature?.name}" />
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

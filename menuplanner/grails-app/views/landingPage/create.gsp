
<%@ page import="com.mp.domain.themes.LandingPage" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'landingPage.label', default: 'LandingPage')}" />
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
            <g:hasErrors bean="${landingPage}">
            <div class="errors">
                <g:renderErrors bean="${landingPage}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="redirectURI"><g:message code="landingPage.redirectURI.label" default="Redirect URI" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: landingPage, field: 'redirectURI', 'errors')}">
                                    <g:textField name="redirectURI" value="${landingPage?.redirectURI}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="controllerName"><g:message code="landingPage.controllerName.label" default="Controller Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: landingPage, field: 'controllerName', 'errors')}">
                                    <g:textField name="controllerName" value="${landingPage?.controllerName}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="actionName"><g:message code="landingPage.actionName.label" default="Action Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: landingPage, field: 'actionName', 'errors')}">
                                    <g:textField name="actionName" value="${landingPage?.actionName}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name"><g:message code="landingPage.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: landingPage, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${landingPage?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="lastModified"><g:message code="landingPage.lastModified.label" default="Last Modified" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: landingPage, field: 'lastModified', 'errors')}">
                                    <g:datePicker name="lastModified" precision="day" value="${landingPage?.lastModified}" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="activeTo"><g:message code="landingPage.activeTo.label" default="Active To" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: landingPage, field: 'activeTo', 'errors')}">
                                    <g:datePicker name="activeTo" precision="day" value="${landingPage?.activeTo}" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="activeFrom"><g:message code="landingPage.activeFrom.label" default="Active From" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: landingPage, field: 'activeFrom', 'errors')}">
                                    <g:datePicker name="activeFrom" precision="day" value="${landingPage?.activeFrom}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="description"><g:message code="landingPage.description.label" default="Description" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: landingPage, field: 'description', 'errors')}">
                                    <g:textField name="description" value="${landingPage?.description}" />
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

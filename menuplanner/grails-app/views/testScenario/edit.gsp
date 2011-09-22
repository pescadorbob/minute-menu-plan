
<%@ page import="com.mp.analytics.TestScenario" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'testScenario.label', default: 'TestScenario')}" />
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
            <g:hasErrors bean="${testScenario}">
            <div class="errors">
                <g:renderErrors bean="${testScenario}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${testScenario?.id}" />
                <g:hiddenField name="version" value="${testScenario?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="active"><g:message code="testScenario.active.label" default="Active" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: testScenario, field: 'active', 'errors')}">
                                    <g:checkBox name="active" value="${testScenario?.active}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="description"><g:message code="testScenario.description.label" default="Description" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: testScenario, field: 'description', 'errors')}">
                                    <g:textField name="description" value="${testScenario?.description}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="name"><g:message code="testScenario.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: testScenario, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${testScenario?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="notes"><g:message code="testScenario.notes.label" default="Notes" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: testScenario, field: 'notes', 'errors')}">
                                    <g:textField name="notes" value="${testScenario?.notes}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="requests"><g:message code="testScenario.requests.label" default="Requests" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: testScenario, field: 'requests', 'errors')}">
                                    
<ul>
<g:each in="${testScenario?.requests?}" var="r">
    <li><g:link controller="appRequest" action="show" id="${r.id}">${r?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="appRequest" action="create" params="['testScenario.id': testScenario?.id]">${message(code: 'default.add.label', args: [message(code: 'appRequest.label', default: 'AppRequest')])}</g:link>

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

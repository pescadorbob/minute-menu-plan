
<%@ page import="com.mp.analytics.TestScenario" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'testScenario.label', default: 'TestScenario')}" />
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
            <g:hasErrors bean="${testScenario}">
            <div class="errors">
                <g:renderErrors bean="${testScenario}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
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


<%@ page import="com.mp.domain.party.Grocer" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'grocer.label', default: 'Grocer')}" />
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
            <g:hasErrors bean="${grocer}">
            <div class="errors">
                <g:renderErrors bean="${grocer}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="organizationName"><g:message code="grocer.organizationName.label" default="Organization Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: grocer, field: 'organizationName', 'errors')}">
                                    <g:select name="organizationName.id" from="${com.mp.domain.party.OrganizationName.list()}" optionKey="id" value="${grocer?.organizationName?.id}" noSelection="['null': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="description"><g:message code="grocer.description.label" default="Description" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: grocer, field: 'description', 'errors')}">
                                    <g:textField name="description" value="${grocer?.description}" />
                                </td>
                            </tr>

                            <tr class="prop">

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

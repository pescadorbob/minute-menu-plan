
<%@ page import="com.mp.analytics.AppRequest" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'appRequest.label', default: 'AppRequest')}" />
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
            <g:hasErrors bean="${appRequest}">
            <div class="errors">
                <g:renderErrors bean="${appRequest}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${appRequest?.id}" />
                <g:hiddenField name="version" value="${appRequest?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="calls"><g:message code="appRequest.calls.label" default="Calls" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: appRequest, field: 'calls', 'errors')}">
                                    
<ul>
<g:each in="${appRequest?.calls?}" var="c">
    <li><g:link controller="call" action="show" id="${c.id}">${c?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="call" action="create" params="['appRequest.id': appRequest?.id]">${message(code: 'default.add.label', args: [message(code: 'call.label', default: 'Call')])}</g:link>

                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="within"><g:message code="appRequest.within.label" default="Within" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: appRequest, field: 'within', 'errors')}">
                                    <g:select name="within.id" from="${com.mp.analytics.TestScenario.list()}" optionKey="id" value="${appRequest?.within?.id}"  />
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

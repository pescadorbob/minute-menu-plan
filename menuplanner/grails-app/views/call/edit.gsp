
<%@ page import="com.mp.analytics.TestInterval" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'call.label', default: 'Call')}" />
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
            <g:hasErrors bean="${call}">
            <div class="errors">
                <g:renderErrors bean="${call}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${call?.id}" />
                <g:hiddenField name="version" value="${call?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="outTime"><g:message code="call.outTime.label" default="Out Time" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: call, field: 'outTime', 'errors')}">
                                    <g:datePicker name="outTime" precision="day" value="${call?.outTime}" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="total"><g:message code="call.total.label" default="Total" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: call, field: 'total', 'errors')}">
                                    <g:textField name="total" value="${fieldValue(bean: call, field: 'total')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="details"><g:message code="call.details.label" default="Details" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: call, field: 'details', 'errors')}">
                                    <g:textField name="details" value="${call?.details}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="inTime"><g:message code="call.inTime.label" default="In Time" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: call, field: 'inTime', 'errors')}">
                                    <g:datePicker name="inTime" precision="day" value="${call?.inTime}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="name"><g:message code="call.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: call, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${call?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="within"><g:message code="call.within.label" default="Within" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: call, field: 'within', 'errors')}">
                                    <g:select name="within.id" from="${com.mp.analytics.AppRequest.list()}" optionKey="id" value="${call?.within?.id}"  />
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

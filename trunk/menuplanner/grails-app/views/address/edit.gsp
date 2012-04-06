
<%@ page import="com.mp.domain.party.Address" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'address.label', default: 'Address')}" />
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
            <g:hasErrors bean="${address}">
            <div class="errors">
                <g:renderErrors bean="${address}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${address?.id}" />
                <g:hiddenField name="version" value="${address?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="addressLines"><g:message code="address.addressLines.label" default="Address Lines" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: address, field: 'addressLines', 'errors')}">
                                    <g:textField name="addressLines" value="${address?.addressLines}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="city"><g:message code="address.city.label" default="City" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: address, field: 'city', 'errors')}">
                                    <g:textField name="city" value="${address?.city}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="regionOrState"><g:message code="address.regionOrState.label" default="Region Or State" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: address, field: 'regionOrState', 'errors')}">
                                    <g:textField name="regionOrState" value="${address?.regionOrState}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="zipOrPostCode"><g:message code="address.zipOrPostCode.label" default="Zip Or Post Code" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: address, field: 'zipOrPostCode', 'errors')}">
                                    <g:textField name="zipOrPostCode" value="${address?.zipOrPostCode}" />
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


<%@ page import="com.mp.domain.access.AccessFilter" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'accessFilter.label', default: 'AccessFilter')}" />
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
            <g:hasErrors bean="${accessFilter}">
            <div class="errors">
                <g:renderErrors bean="${accessFilter}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name"><g:message code="accessFilter.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: accessFilter, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${accessFilter?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="controllerFilter"><g:message code="accessFilter.controllerFilter.label" default="Controller Filter" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: accessFilter, field: 'controllerFilter', 'errors')}">
                                    <g:textField name="controllerFilter" value="${accessFilter?.controllerFilter}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="actionFilter"><g:message code="accessFilter.actionFilter.label" default="Action Filter" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: accessFilter, field: 'actionFilter', 'errors')}">
                                    <g:textField name="actionFilter" value="${accessFilter?.actionFilter}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="uriFilter"><g:message code="accessFilter.uriFilter.label" default="Uri Filter" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: accessFilter, field: 'uriFilter', 'errors')}">
                                    <g:textField name="uriFilter" value="${accessFilter?.uriFilter}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="filterFor"><g:message code="accessFilter.filterFor.label" default="Filter For" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: accessFilter, field: 'filterFor', 'errors')}">
                                    <g:select name="filterFor.id" from="${com.mp.domain.access.AccessFilterSet.list()}" optionKey="id" value="${accessFilter?.filterFor?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="description"><g:message code="accessFilter.description.label" default="Description" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: accessFilter, field: 'description', 'errors')}">
                                    <g:textField name="description" value="${accessFilter?.description}" />
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

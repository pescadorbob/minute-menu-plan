
<%@ page import="com.mp.domain.access.RoleAccess" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'roleAccess.label', default: 'RoleAccess')}" />
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
            <g:hasErrors bean="${roleAccess}">
            <div class="errors">
                <g:renderErrors bean="${roleAccess}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${roleAccess?.id}" />
                <g:hiddenField name="version" value="${roleAccess?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="to"><g:message code="roleAccess.to.label" default="To" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: roleAccess, field: 'to', 'errors')}">
                                    <g:select name="to.id" from="${com.mp.domain.access.AccessFilter.list()}" optionKey="id" value="${roleAccess?.to?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="from"><g:message code="roleAccess.from.label" default="From" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: roleAccess, field: 'from', 'errors')}">
                                    <g:select name="from.id" from="${com.mp.domain.party.PartyRole.list()}" optionKey="id" value="${roleAccess?.from?.id}"  />
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

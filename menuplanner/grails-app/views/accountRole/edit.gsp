
<%@ page import="com.mp.domain.accounting.AccountRole" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'accountRole.label', default: 'AccountRole')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${accountRole}">
            <div class="errors">
                <g:renderErrors bean="${accountRole}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${accountRole?.id}" />
                <g:hiddenField name="version" value="${accountRole?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="roleFor"><g:message code="accountRole.roleFor.label" default="Role For" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: accountRole, field: 'roleFor', 'errors')}">
                                    <g:select name="roleFor.id" from="${com.mp.domain.party.Party.list()}" optionKey="id" value="${accountRole?.roleFor?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="type"><g:message code="accountRole.type.label" default="Type" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: accountRole, field: 'type', 'errors')}">
                                    <g:select name="type" from="${com.mp.domain.accounting.AccountRoleType?.values()}" value="${accountRole?.type}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="describes"><g:message code="accountRole.describes.label" default="Describes" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: accountRole, field: 'describes', 'errors')}">
                                    <g:select name="describes.id" from="${com.mp.domain.accounting.Account.list()}" optionKey="id" value="${accountRole?.describes?.id}"  />
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
    </body>
</html>

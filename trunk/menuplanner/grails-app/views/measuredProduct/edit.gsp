
<%@ page import="com.mp.domain.MeasuredProduct" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'measuredProduct.label', default: 'MeasuredProduct')}" />
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
            <g:hasErrors bean="${measuredProduct}">
            <div class="errors">
                <g:renderErrors bean="${measuredProduct}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${measuredProduct?.id}" />
                <g:hiddenField name="version" value="${measuredProduct?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="name"><g:message code="measuredProduct.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: measuredProduct, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${measuredProduct?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="preferredUnit"><g:message code="measuredProduct.preferredUnit.label" default="Preferred Unit" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: measuredProduct, field: 'preferredUnit', 'errors')}">
                                    <g:select name="preferredUnit.id" from="${com.mp.domain.Unit.list()}" optionKey="id" value="${measuredProduct?.preferredUnit?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="possibleUnits"><g:message code="measuredProduct.possibleUnits.label" default="Possible Units" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: measuredProduct, field: 'possibleUnits', 'errors')}">
                                    <g:select name="possibleUnits" from="${com.mp.domain.Unit.list()}" multiple="yes" optionKey="id" size="5" value="${measuredProduct?.possibleUnits}" />
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
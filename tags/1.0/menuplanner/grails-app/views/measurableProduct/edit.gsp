
<%@ page import="com.mp.domain.MeasurableProduct" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'measurableProduct.label', default: 'MeasurableProduct')}" />
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
            <g:hasErrors bean="${measurableProduct}">
            <div class="errors">
                <g:renderErrors bean="${measurableProduct}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${measurableProduct?.id}" />
                <g:hiddenField name="version" value="${measurableProduct?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="name"><g:message code="measurableProduct.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: measurableProduct, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${measurableProduct?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="preferredUnit"><g:message code="measurableProduct.preferredUnit.label" default="Preferred Unit" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: measurableProduct, field: 'preferredUnit', 'errors')}">
                                    <g:select name="preferredUnit.id" from="${com.mp.domain.Unit.list()}" optionKey="id" value="${measurableProduct?.preferredUnit?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="possibleUnits"><g:message code="measurableProduct.possibleUnits.label" default="Possible Units" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: measurableProduct, field: 'possibleUnits', 'errors')}">
                                    <g:select name="possibleUnits" from="${com.mp.domain.Unit.list()}" multiple="yes" optionKey="id" size="5" value="${measurableProduct?.possibleUnits}" />
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

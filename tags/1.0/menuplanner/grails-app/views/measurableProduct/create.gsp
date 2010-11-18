
<%@ page import="com.mp.domain.MeasurableProduct" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'measurableProduct.label', default: 'MeasurableProduct')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${measurableProduct}">
            <div class="errors">
                <g:renderErrors bean="${measurableProduct}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
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
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>

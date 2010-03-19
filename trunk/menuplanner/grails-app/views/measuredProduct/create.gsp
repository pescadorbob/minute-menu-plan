
<%@ page import="com.mp.domain.MeasuredProduct" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'measuredProduct.label', default: 'MeasuredProduct')}" />
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
            <g:hasErrors bean="${measuredProduct}">
            <div class="errors">
                <g:renderErrors bean="${measuredProduct}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="product"><g:message code="measuredProduct.product.label" default="Product" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: measuredProduct, field: 'product', 'errors')}">
                                    <g:select name="product.id" from="${com.mp.domain.Product.list()}" optionKey="id" value="${measuredProduct?.product?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="preferredMetric"><g:message code="measuredProduct.preferredMetric.label" default="Preferred Metric" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: measuredProduct, field: 'preferredMetric', 'errors')}">
                                    <g:select name="preferredMetric.id" from="${com.mp.domain.Metric.list()}" optionKey="id" value="${measuredProduct?.preferredMetric?.id}"  />
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

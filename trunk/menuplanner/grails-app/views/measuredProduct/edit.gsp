
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
            <g:form method="post"  enctype="multipart/form-data">
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
                                  <label for="preferredMetric"><g:message code="measuredProduct.preferredMetric.label" default="Preferred Metric" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: measuredProduct, field: 'preferredMetric', 'errors')}">
                                    <g:select name="preferredMetric.id" from="${com.mp.domain.Metric.list()}" optionKey="id" value="${measuredProduct?.preferredMetric?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="image"><g:message code="measuredProduct.image.label" default="Image" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: measuredProduct, field: 'image', 'errors')}">
                                    <input type="file" id="image" name="image" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="possibleMetrics"><g:message code="measuredProduct.possibleMetrics.label" default="Possible Metrics" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: measuredProduct, field: 'possibleMetrics', 'errors')}">
                                    <g:select name="possibleMetrics" from="${com.mp.domain.Metric.list()}" multiple="yes" optionKey="id" size="5" value="${measuredProduct?.possibleMetrics}" />
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


<%@ page import="com.mp.domain.Unit" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'unit.label', default: 'Unit')}" />
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
            <g:hasErrors bean="${unit}">
            <div class="errors">
                <g:renderErrors bean="${unit}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${unit?.id}" />
                <g:hiddenField name="version" value="${unit?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="name"><g:message code="unit.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: unit, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${unit?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="small"><g:message code="unit.small.label" default="Small" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: unit, field: 'small', 'errors')}">
                                    <g:textField name="small" value="${unit?.small}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="party"><g:message code="unit.party.label" default="Party" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: unit, field: 'party', 'errors')}">
                                    <g:select name="party.id" from="${com.mp.domain.party.Party.list()}" optionKey="id" value="${unit?.party?.id}" noSelection="['null': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="definition"><g:message code="unit.definition.label" default="Definition" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: unit, field: 'definition', 'errors')}">
                                    <g:textField name="definition" value="${unit?.definition}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="isConvertible"><g:message code="unit.isConvertible.label" default="Is Convertible" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: unit, field: 'isConvertible', 'errors')}">
                                    <g:checkBox name="isConvertible" value="${unit?.isConvertible}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="isWeightUnit"><g:message code="unit.isWeightUnit.label" default="Is Weight Unit" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: unit, field: 'isWeightUnit', 'errors')}">
                                    <g:checkBox name="isWeightUnit" value="${unit?.isWeightUnit}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="metricType"><g:message code="unit.metricType.label" default="Metric Type" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: unit, field: 'metricType', 'errors')}">
                                    <g:select name="metricType" from="${com.mp.domain.MetricType?.values()}" value="${unit?.metricType}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="symbol"><g:message code="unit.symbol.label" default="Symbol" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: unit, field: 'symbol', 'errors')}">
                                    <g:textField name="symbol" value="${unit?.symbol}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="systemOfUnits"><g:message code="unit.systemOfUnits.label" default="System Of Units" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: unit, field: 'systemOfUnits', 'errors')}">
                                    
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


<%@ page import="com.mp.domain.Unit" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'unit.label', default: 'Unit')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
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
            <h3><g:message code="default.show.label" args="[entityName]" /></h3>
            </div>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="unit.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: unit, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="unit.name.label" default="Name" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: unit, field: "name")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="unit.small.label" default="Small" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: unit, field: "small")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="unit.party.label" default="Party" /></td>
                            
                            <td valign="top" class="value"><g:link controller="party" action="show" id="${unit?.party?.id}">${unit?.party?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="unit.definition.label" default="Definition" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: unit, field: "definition")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="unit.isConvertible.label" default="Is Convertible" /></td>
                            
                            <td valign="top" class="value"><g:formatBoolean boolean="${unit?.isConvertible}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="unit.isWeightUnit.label" default="Is Weight Unit" /></td>
                            
                            <td valign="top" class="value"><g:formatBoolean boolean="${unit?.isWeightUnit}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="unit.metricType.label" default="Metric Type" /></td>
                            
                            <td valign="top" class="value">${unit?.metricType?.encodeAsHTML()}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="unit.symbol.label" default="Symbol" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: unit, field: "symbol")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="unit.systemOfUnits.label" default="System Of Units" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${unit.systemOfUnits}" var="s">
                                    <li><g:link controller="systemOfUnit" action="show" id="${s.id}">${s?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${unit?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </div></div></div>

    </body>
</html>

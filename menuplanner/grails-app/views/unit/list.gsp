
<%@ page import="com.mp.domain.Unit" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'unit.label', default: 'Unit')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
       <div id="container">
  <div id="wrapper" class="clearfix">
    <div id="content-wrapper" class="clearfix">
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h3><g:message code="default.list.label" args="[entityName]" /></h3>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'unit.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="name" title="${message(code: 'unit.name.label', default: 'Name')}" />
                        
                            <g:sortableColumn property="small" title="${message(code: 'unit.small.label', default: 'Small')}" />
                        
                            <th><g:message code="unit.party.label" default="Party" /></th>
                   	    
                            <g:sortableColumn property="definition" title="${message(code: 'unit.definition.label', default: 'Definition')}" />
                        
                            <g:sortableColumn property="isConvertible" title="${message(code: 'unit.isConvertible.label', default: 'Is Convertible')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${unitList}" status="i" var="unit">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${unit.id}">${fieldValue(bean: unit, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: unit, field: "name")}</td>
                        
                            <td>${fieldValue(bean: unit, field: "small")}</td>
                        
                            <td>${fieldValue(bean: unit, field: "party")}</td>
                        
                            <td>${fieldValue(bean: unit, field: "definition")}</td>
                        
                            <td><g:formatBoolean boolean="${unit.isConvertible}" /></td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${unitTotal}" />
            </div>
          </div></div></div>                      
        </div>
    </body>
</html>

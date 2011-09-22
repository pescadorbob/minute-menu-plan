
<%@ page import="com.mp.analytics.TestScenario" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'testScenario.label', default: 'TestScenario')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'testScenario.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="active" title="${message(code: 'testScenario.active.label', default: 'Active')}" />
                        
                            <g:sortableColumn property="description" title="${message(code: 'testScenario.description.label', default: 'Description')}" />
                        
                            <g:sortableColumn property="name" title="${message(code: 'testScenario.name.label', default: 'Name')}" />
                        
                            <g:sortableColumn property="notes" title="${message(code: 'testScenario.notes.label', default: 'Notes')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${testScenarioList}" status="i" var="testScenario">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${testScenario.id}">${fieldValue(bean: testScenario, field: "id")}</g:link></td>
                        
                            <td><g:formatBoolean boolean="${testScenario.active}" /></td>
                        
                            <td>${fieldValue(bean: testScenario, field: "description")}</td>
                        
                            <td>${fieldValue(bean: testScenario, field: "name")}</td>
                        
                            <td>${fieldValue(bean: testScenario, field: "notes")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${testScenarioTotal}" />
            </div>
          </div></div></div>
        </div>
    </body>
</html>

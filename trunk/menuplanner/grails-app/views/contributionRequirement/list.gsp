
<%@ page import="com.mp.domain.subscriptions.ContributionRequirement" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'contributionRequirement.label', default: 'ContributionRequirement')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'contributionRequirement.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="activeTo" title="${message(code: 'contributionRequirement.activeTo.label', default: 'Active To')}" />
                        
                            <g:sortableColumn property="description" title="${message(code: 'contributionRequirement.description.label', default: 'Description')}" />
                        
                            <g:sortableColumn property="actionName" title="${message(code: 'contributionRequirement.actionName.label', default: 'Action Name')}" />
                        
                            <g:sortableColumn property="activeFrom" title="${message(code: 'contributionRequirement.activeFrom.label', default: 'Active From')}" />
                        
                            <g:sortableColumn property="controllerName" title="${message(code: 'contributionRequirement.controllerName.label', default: 'Controller Name')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${contributionRequirementList}" status="i" var="contributionRequirement">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${contributionRequirement.id}">${fieldValue(bean: contributionRequirement, field: "id")}</g:link></td>
                        
                            <td><g:formatDate date="${contributionRequirement.activeTo}" /></td>
                        
                            <td>${fieldValue(bean: contributionRequirement, field: "description")}</td>
                        
                            <td>${fieldValue(bean: contributionRequirement, field: "actionName")}</td>
                        
                            <td><g:formatDate date="${contributionRequirement.activeFrom}" /></td>
                        
                            <td>${fieldValue(bean: contributionRequirement, field: "controllerName")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${contributionRequirementTotal}" />
            </div>
          </div></div></div>
        </div>
    </body>
</html>

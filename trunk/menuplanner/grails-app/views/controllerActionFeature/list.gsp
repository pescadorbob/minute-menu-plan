
<%@ page import="com.mp.domain.subscriptions.ControllerActionFeature" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'controllerActionFeature.label', default: 'ControllerActionFeature')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'controllerActionFeature.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="rule" title="${message(code: 'controllerActionFeature.rule.label', default: 'Rule')}" />
                        
                            <g:sortableColumn property="activeTo" title="${message(code: 'controllerActionFeature.activeTo.label', default: 'Active To')}" />
                        
                            <g:sortableColumn property="controllerFilter" title="${message(code: 'controllerActionFeature.controllerFilter.label', default: 'Controller Filter')}" />
                        
                            <g:sortableColumn property="actionFilter" title="${message(code: 'controllerActionFeature.actionFilter.label', default: 'Action Filter')}" />
                        
                            <g:sortableColumn property="uriFilter" title="${message(code: 'controllerActionFeature.uriFilter.label', default: 'Uri Filter')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${controllerActionFeatureList}" status="i" var="controllerActionFeature">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${controllerActionFeature.id}">${fieldValue(bean: controllerActionFeature, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: controllerActionFeature, field: "rule")}</td>
                        
                            <td><g:formatDate date="${controllerActionFeature.activeTo}" /></td>
                        
                            <td>${fieldValue(bean: controllerActionFeature, field: "controllerFilter")}</td>
                        
                            <td>${fieldValue(bean: controllerActionFeature, field: "actionFilter")}</td>
                        
                            <td>${fieldValue(bean: controllerActionFeature, field: "uriFilter")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${controllerActionFeatureTotal}" />
            </div>
          </div></div></div>
        </div>
    </body>
</html>


<%@ page import="com.mp.analytics.AppRequest" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'appRequest.label', default: 'AppRequest')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'appRequest.id.label', default: 'Id')}" />
                        
                            <th><g:message code="appRequest.within.label" default="Within" /></th>
                   	    
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${appRequestList}" status="i" var="appRequest">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${appRequest.id}">${fieldValue(bean: appRequest, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: appRequest, field: "within")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${appRequestTotal}" />
            </div>
          </div></div></div>
        </div>
    </body>
</html>

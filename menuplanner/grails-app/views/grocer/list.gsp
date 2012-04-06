
<%@ page import="com.mp.domain.party.Grocer" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'grocer.label', default: 'Grocer')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'grocer.id.label', default: 'Id')}" />
                            <th>Name</th>
                            <th>Description</th>

                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${grocerList}" status="i" var="grocer">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">                        
                            <td>${fieldValue(bean: grocer, field: "id")}</td>
                            <td><g:link action="show" id="${grocer.id}">${grocer.organizationName.name}</g:link></td>
                            <td><g:link action="show" id="${grocer.id}">${grocer.organizationName.description}</g:link></td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${grocerTotal}" />
            </div>
          </div></div></div>
        </div>
    </body>
</html>

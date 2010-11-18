
<%@ page import="com.mp.domain.Category" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menuPlanner" />
        <g:set var="entityName" value="${message(code: 'category.label', default: 'Category')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'category.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="name" title="${message(code: 'category.name.label', default: 'Name')}" />
                        
                            <g:sortableColumn property="recipes" title="${message(code: 'category.recipes.label', default: 'Recipes')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${categoryList}" status="i" var="category">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${category.id}">${fieldValue(bean: category, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: category, field: "name")}</td>
                        
                            <td>${fieldValue(bean: category, field: "recipes")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${categoryTotal}" />
            </div>
        </div>
    </body>
</html>

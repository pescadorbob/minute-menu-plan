
<%@ page import="com.mp.domain.RecipeCategory" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'recipeCategory.label', default: 'RecipeCategory')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'recipeCategory.id.label', default: 'Id')}" />
                        
                            <th><g:message code="recipeCategory.recipe.label" default="Recipe" /></th>
                   	    
                            <th><g:message code="recipeCategory.category.label" default="Category" /></th>
                   	    
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${recipeCategoryList}" status="i" var="recipeCategory">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${recipeCategory.id}">${fieldValue(bean: recipeCategory, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: recipeCategory, field: "recipe")}</td>
                        
                            <td>${fieldValue(bean: recipeCategory, field: "category")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${recipeCategoryTotal}" />
            </div>
        </div>
    </body>
</html>


<%@ page import="com.mp.domain.Recipe" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'recipe.label', default: 'Recipe')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'recipe.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="name" title="${message(code: 'recipe.name.label', default: 'Name')}" />
                        
                            <g:sortableColumn property="preparationTime" title="${message(code: 'recipe.preparationTime.label', default: 'Preparation Time')}" />
                        
                            <g:sortableColumn property="cookTime" title="${message(code: 'recipe.cookingTime.label', default: 'Cook Time')}" />
                        
                            <g:sortableColumn property="difficulty" title="${message(code: 'recipe.difficulty.label', default: 'Difficulty')}" />
                        
                            <g:sortableColumn property="makesServing" title="${message(code: 'recipe.servings.label', default: 'Makes Serving')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${recipeList}" status="i" var="recipe">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${recipe.id}">${fieldValue(bean: recipe, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: recipe, field: "name")}</td>
                        
                            <td>${fieldValue(bean: recipe, field: "preparationTime")}</td>
                        
                            <td>${fieldValue(bean: recipe, field: "cookingTime")}</td>
                        
                            <td>${fieldValue(bean: recipe, field: "difficulty")}</td>
                        
                            <td>${fieldValue(bean: recipe, field: "servings")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${recipeTotal}" />
            </div>
        </div>
    </body>
</html>

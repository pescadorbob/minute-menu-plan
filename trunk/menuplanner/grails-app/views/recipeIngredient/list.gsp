
<%@ page import="com.mp.domain.RecipeIngredient" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'recipeIngredient.label', default: 'RecipeIngredient')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'recipeIngredient.id.label', default: 'Id')}" />
                        
                            <th><g:message code="recipeIngredient.recipe.label" default="Recipe" /></th>
                   	    
                            <g:sortableColumn property="sequence" title="${message(code: 'recipeIngredient.sequence.label', default: 'Sequence')}" />
                        
                            <th><g:message code="recipeIngredient.ingredient.label" default="Ingredient" /></th>
                   	    
                            <th><g:message code="recipeIngredient.quantity.label" default="Quantity" /></th>
                   	    
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${recipeIngredientList}" status="i" var="recipeIngredient">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${recipeIngredient.id}">${fieldValue(bean: recipeIngredient, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: recipeIngredient, field: "recipe")}</td>
                        
                            <td>${fieldValue(bean: recipeIngredient, field: "sequence")}</td>
                        
                            <td>${fieldValue(bean: recipeIngredient, field: "ingredient")}</td>
                        
                            <td>${fieldValue(bean: recipeIngredient, field: "quantity")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${recipeIngredientTotal}" />
            </div>
        </div>
    </body>
</html>

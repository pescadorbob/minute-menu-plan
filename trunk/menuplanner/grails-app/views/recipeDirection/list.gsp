
<%@ page import="com.mp.domain.RecipeDirection" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'recipeDirection.label', default: 'RecipeDirection')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'recipeDirection.id.label', default: 'Id')}" />
                        
                            <th><g:message code="recipeDirection.recipe.label" default="Recipe" /></th>
                   	    
                            <g:sortableColumn property="sequence" title="${message(code: 'recipeDirection.sequence.label', default: 'Sequence')}" />
                        
                            <g:sortableColumn property="step" title="${message(code: 'recipeDirection.step.label', default: 'Step')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${recipeDirectionList}" status="i" var="recipeDirection">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${recipeDirection.id}">${fieldValue(bean: recipeDirection, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: recipeDirection, field: "recipe")}</td>
                        
                            <td>${fieldValue(bean: recipeDirection, field: "sequence")}</td>
                        
                            <td>${fieldValue(bean: recipeDirection, field: "step")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${recipeDirectionTotal}" />
            </div>
        </div>
    </body>
</html>

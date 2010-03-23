
<%@ page import="com.mp.domain.RecipeCategory" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'recipeCategory.label', default: 'RecipeCategory')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${recipeCategory}">
            <div class="errors">
                <g:renderErrors bean="${recipeCategory}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="recipe"><g:message code="recipeCategory.recipe.label" default="Recipe" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: recipeCategory, field: 'recipe', 'errors')}">
                                    <g:select name="recipe.id" from="${com.mp.domain.Recipe.list()}" optionKey="id" value="${recipeCategory?.recipe?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="category"><g:message code="recipeCategory.category.label" default="Category" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: recipeCategory, field: 'category', 'errors')}">
                                    <g:select name="category.id" from="${com.mp.domain.Category.list()}" optionKey="id" value="${recipeCategory?.category?.id}"  />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>

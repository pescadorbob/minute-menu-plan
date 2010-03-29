
<%@ page import="com.mp.domain.Recipe" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'recipe.label', default: 'Recipe')}" />
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
            <g:hasErrors bean="${recipe}">
            <div class="errors">
                <g:renderErrors bean="${recipe}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name"><g:message code="recipe.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: recipe, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${recipe?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="preparationTime"><g:message code="recipe.preparationTime.label" default="Preparation Time" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: recipe, field: 'preparationTime', 'errors')}">
                                    <g:select name="preparationTime.id" from="${com.mp.domain.Time.list()}" optionKey="id" value="${recipe?.preparationTime?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="cookingTime"><g:message code="recipe.cookingTime.label" default="Cooking Time" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: recipe, field: 'cookingTime', 'errors')}">
                                    <g:select name="cookingTime.id" from="${com.mp.domain.Time.list()}" optionKey="id" value="${recipe?.cookingTime?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="difficulty"><g:message code="recipe.difficulty.label" default="Difficulty" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: recipe, field: 'difficulty', 'errors')}">
                                    <g:select name="difficulty" from="${com.mp.domain.RecipeDifficulty?.values()}" value="${recipe?.difficulty}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="servings"><g:message code="recipe.servings.label" default="Servings" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: recipe, field: 'servings', 'errors')}">
                                    <g:textField name="servings" value="${fieldValue(bean: recipe, field: 'servings')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="image"><g:message code="recipe.image.label" default="Image" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: recipe, field: 'image', 'errors')}">
                                    <g:select name="image.id" from="${com.mp.domain.Image.list()}" optionKey="id" value="${recipe?.image?.id}" noSelection="['null': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="shareWithCommunity"><g:message code="recipe.shareWithCommunity.label" default="Share With Community" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: recipe, field: 'shareWithCommunity', 'errors')}">
                                    <g:checkBox name="shareWithCommunity" value="${recipe?.shareWithCommunity}" />
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

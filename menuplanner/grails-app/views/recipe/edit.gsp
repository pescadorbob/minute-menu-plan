
<%@ page import="com.mp.domain.Recipe" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'recipe.label', default: 'Recipe')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${recipe}">
            <div class="errors">
                <g:renderErrors bean="${recipe}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${recipe?.id}" />
                <g:hiddenField name="version" value="${recipe?.version}" />
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
                                    <g:select name="preparationTime.id" from="${com.mp.domain.Quantity.list()}" optionKey="id" value="${recipe?.preparationTime?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="cookingTime"><g:message code="recipe.cookingTime.label" default="Cooking Time" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: recipe, field: 'cookingTime', 'errors')}">
                                    <g:select name="cookingTime.id" from="${com.mp.domain.Quantity.list()}" optionKey="id" value="${recipe?.cookingTime?.id}"  />
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
                                  <label for="directions"><g:message code="recipe.directions.label" default="Directions" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: recipe, field: 'directions', 'errors')}">
                                    
<ul>
<g:each in="${recipe?.directions?}" var="d">
    <li><g:link controller="recipeDirection" action="show" id="${d.id}">${d?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="recipeDirection" action="create" params="['recipe.id': recipe?.id]">${message(code: 'default.add.label', args: [message(code: 'recipeDirection.label', default: 'RecipeDirection')])}</g:link>

                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="ingredients"><g:message code="recipe.ingredients.label" default="Ingredients" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: recipe, field: 'ingredients', 'errors')}">
                                    
<ul>
<g:each in="${recipe?.ingredients?}" var="i">
    <li><g:link controller="recipeIngredient" action="show" id="${i.id}">${i?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="recipeIngredient" action="create" params="['recipe.id': recipe?.id]">${message(code: 'default.add.label', args: [message(code: 'recipeIngredient.label', default: 'RecipeIngredient')])}</g:link>

                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="nutrients"><g:message code="recipe.nutrients.label" default="Nutrients" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: recipe, field: 'nutrients', 'errors')}">
                                    
<ul>
<g:each in="${recipe?.nutrients?}" var="n">
    <li><g:link controller="recipeNutrient" action="show" id="${n.id}">${n?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="recipeNutrient" action="create" params="['recipe.id': recipe?.id]">${message(code: 'default.add.label', args: [message(code: 'recipeNutrient.label', default: 'RecipeNutrient')])}</g:link>

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
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="recipeCategories"><g:message code="recipe.recipeCategories.label" default="Recipe Categories" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: recipe, field: 'recipeCategories', 'errors')}">
                                    
<ul>
<g:each in="${recipe?.recipeCategories?}" var="r">
    <li><g:link controller="recipeCategory" action="show" id="${r.id}">${r?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="recipeCategory" action="create" params="['recipe.id': recipe?.id]">${message(code: 'default.add.label', args: [message(code: 'recipeCategory.label', default: 'RecipeCategory')])}</g:link>

                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="categories"><g:message code="recipe.categories.label" default="Categories" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: recipe, field: 'categories', 'errors')}">
                                    
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>


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
            <g:form method="post"  enctype="multipart/form-data">
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
                                  <label for="image"><g:message code="recipe.image.label" default="Image" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: recipe, field: 'image', 'errors')}">
                                    <input type="file" id="image" name="image" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="ingredients"><g:message code="recipe.ingredients.label" default="Recipe Ingredients" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: recipe, field: 'ingredients', 'errors')}">
                                    
<ul>
<g:each in="${recipe?.ingredients?}" var="r">
    <li><g:link controller="recipeIngredient" action="show" id="${r.id}">${r?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="recipeIngredient" action="create" params="['recipe.id': recipe?.id]">${message(code: 'default.add.label', args: [message(code: 'recipeIngredient.label', default: 'RecipeIngredient')])}</g:link>

                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="directions"><g:message code="recipe.directions.label" default="Recipe Directions" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: recipe, field: 'directions', 'errors')}">
                                    
<ul>
<g:each in="${recipe?.directions?}" var="r">
    <li><g:link controller="recipeDirection" action="show" id="${r.id}">${r?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="recipeDirection" action="create" params="['recipe.id': recipe?.id]">${message(code: 'default.add.label', args: [message(code: 'recipeDirection.label', default: 'RecipeDirection')])}</g:link>

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

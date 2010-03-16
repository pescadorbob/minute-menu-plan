
<%@ page import="com.mp.domain.RecipeIngredient" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'recipeIngredient.label', default: 'RecipeIngredient')}" />
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
            <g:hasErrors bean="${recipeIngredient}">
            <div class="errors">
                <g:renderErrors bean="${recipeIngredient}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${recipeIngredient?.id}" />
                <g:hiddenField name="version" value="${recipeIngredient?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="recipe"><g:message code="recipeIngredient.recipe.label" default="Recipe" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: recipeIngredient, field: 'recipe', 'errors')}">
                                    <g:select name="recipe.id" from="${com.mp.domain.Recipe.list()}" optionKey="id" value="${recipeIngredient?.recipe?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="sequence"><g:message code="recipeIngredient.sequence.label" default="Sequence" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: recipeIngredient, field: 'sequence', 'errors')}">
                                    <g:textField name="sequence" value="${fieldValue(bean: recipeIngredient, field: 'sequence')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="ingredient"><g:message code="recipeIngredient.ingredient.label" default="Ingredient" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: recipeIngredient, field: 'ingredient', 'errors')}">
                                    <g:select name="ingredient.id" from="${com.mp.domain.MeasuredProduct.list()}" optionKey="id" value="${recipeIngredient?.ingredient?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="quantity"><g:message code="recipeIngredient.quantity.label" default="Quantity" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: recipeIngredient, field: 'quantity', 'errors')}">
                                    <g:select name="quantity.id" from="${com.mp.domain.Quantity.list()}" optionKey="id" value="${recipeIngredient?.quantity?.id}"  />
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

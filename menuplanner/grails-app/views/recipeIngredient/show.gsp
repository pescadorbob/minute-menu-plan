
<%@ page import="com.mp.domain.RecipeIngredient" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'recipeIngredient.label', default: 'RecipeIngredient')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="recipeIngredient.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: recipeIngredient, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="recipeIngredient.recipe.label" default="Recipe" /></td>
                            
                            <td valign="top" class="value"><g:link controller="recipe" action="show" id="${recipeIngredient?.recipe?.id}">${recipeIngredient?.recipe?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="recipeIngredient.sequence.label" default="Sequence" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: recipeIngredient, field: "sequence")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="recipeIngredient.ingredient.label" default="Ingredient" /></td>
                            
                            <td valign="top" class="value"><g:link controller="measurableProduct" action="show" id="${recipeIngredient?.ingredient?.id}">${recipeIngredient?.ingredient?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="recipeIngredient.quantity.label" default="Quantity" /></td>
                            
                            <td valign="top" class="value"><g:link controller="quantity" action="show" id="${recipeIngredient?.quantity?.id}">${recipeIngredient?.quantity?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${recipeIngredient?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>

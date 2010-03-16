
<%@ page import="com.mp.domain.RecipeDirection" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'recipeDirection.label', default: 'RecipeDirection')}" />
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
            <g:hasErrors bean="${recipeDirection}">
            <div class="errors">
                <g:renderErrors bean="${recipeDirection}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="recipe"><g:message code="recipeDirection.recipe.label" default="Recipe" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: recipeDirection, field: 'recipe', 'errors')}">
                                    <g:select name="recipe.id" from="${com.mp.domain.Recipe.list()}" optionKey="id" value="${recipeDirection?.recipe?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="sequence"><g:message code="recipeDirection.sequence.label" default="Sequence" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: recipeDirection, field: 'sequence', 'errors')}">
                                    <g:textField name="sequence" value="${fieldValue(bean: recipeDirection, field: 'sequence')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="step"><g:message code="recipeDirection.step.label" default="Step" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: recipeDirection, field: 'step', 'errors')}">
                                    <g:textField name="step" value="${recipeDirection?.step}" />
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

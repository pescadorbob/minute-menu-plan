<%@ page import="com.mp.domain.*" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Edit</title>
    <meta name="layout" content="menuPlanner"/>
</head>
<body>
<table id="sampleIngredientRow" style="display:none;">
    <g:render template="ingredientRow"/>
</table>
<table id="sampleDirectionRow" style="display:none;">
    <g:render template="directionRow"/>
</table>
<g:form name="formCreateRecipe" controller="recipe" action="update" enctype="multipart/form-data">
    <div class="adrecipepage-left-container">
        <div class="add-recipe-header">
            <div class="add-recipe-header1">
                <img src="${resource(dir: 'images', file: 'add-recipe-header-img1.jpg')}">
            </div>
            <div class="add-recipe-header2">Edit Recipe</div>
            <div class="add-recipe-header1">
                <img src="${resource(dir: 'images', file: 'add-recipe-header-img2.jpg')}">
            </div>

            <div class="clr"></div>
        </div>

        <div>
            <g:hasErrors bean="${recipeCO}">
                <div class="errors">
                    <g:renderErrors bean="${recipeCO}"/>
                </div>
            </g:hasErrors>
        </div>

        <div class="menu-nav clr">
            <ul>
                <li><a class="tabs active" id="tabGeneralInfo" style="cursor:pointer; ${mp.checkGeneralInfoTabError(bean: recipeCO, fields: ['name', 'makesServing', 'preparationTime', 'cookTime', 'difficulty', 'categoryIds'])}">General Info</a></li>
                <li><a class="tabs" id="tabIngredients" style="cursor:pointer;${hasErrors(bean: recipeCO, field: 'hiddenIngredientProductNames', 'color:red;')}">Ingredients</a></li>
                <li><a class="tabs" id="tabCookingSteps" style="cursor:pointer; ${hasErrors(bean: recipeCO, field: 'directions', 'color:red;')}">Cooking Steps</a></li>
                <li><a class="tabs" id="tabServeWith" style="cursor:pointer;">Serve With</a></li>
                <li><a class="tabs" id="tabNutritionFacts" style="cursor:pointer;${hasErrors(bean: recipeCO, field: 'nutrientQuantities', 'color:red;')}">Nutrition Facts</a></li>
            </ul>
        </div>
        <g:hiddenField name='id' value='${recipeCO.id}'/>
        <g:render template="/recipe/generalInfo" model="[recipeCO: recipeCO, timeUnits: timeUnits]"/>
        <g:render template="/recipe/ingredients" model="[recipeCO: recipeCO, metricUnits: metricUnits]"/>
        <g:render template="/recipe/cookingSteps" model="[recipeCO: recipeCO]"/>
        <g:render template="/recipe/serveWith" model="[recipeCO: recipeCO]"/>
        <g:render template="/recipe/nutrientFacts" model="[recipeCO: recipeCO, nutrients:nutrients]"/>

    </div>

    <g:render template="/recipe/preview"/>

    <div class="save-recips">
        <g:submitButton name="btnUpdate" class="save" value="Update Recipe" style="font-size:14px;color:white;background-color:orange;width:120px;height:35px;cursor:pointer;"/>
        <input type="button" name="preview" id="preview" value="Preview" style="font-size:14px;color:white;background-color:orange;width:120px;height:35px;cursor:pointer;"/>
        <input type="button" name="delete" id="delete" value="Delete" style="font-size:14px;color:white;background-color:orange;width:120px;height:35px;cursor:pointer;"/>
    </div>

</g:form>
<g:render template="/recipe/bindEventsToTabs"/>
</body>
</html>
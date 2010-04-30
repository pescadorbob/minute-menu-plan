<%@ page import="com.mp.domain.*" %>
<html>
<head>
    <meta name="layout" content="menu"/>
    <title>Add Recipe</title>
</head>
<body>

<table id="sampleIngredientRow" style="display:none;">
    <g:render template="ingredientRow"/>
</table>
<table id="sampleDirectionRow" style="display:none;">
    <g:render template="directionRow"/>
</table>

<div id="container">
    <div id="wrapper" class="clearfix">
        <div id="content-wrapper" class="clearfix">
            <!--  start left-panel -->
            <div id="left-panel">
                <!--  start left-panel -->
                <div class="headbox">
                    <h3>Edit Recipe</h3>
                </div>
                <g:hasErrors bean="${recipeCO}">
                    <div class="errors">
                        <g:renderErrors bean="${recipeCO}"/>
                    </div>
                </g:hasErrors>

                <div id="menu">
                    <ul>
                        <li><a class="tabs current" id="tabGeneralInfo" style="${mp.checkGeneralInfoTabError(bean: recipeCO, fields: ['name', 'makesServing', 'preparationTime', 'cookTime', 'difficulty', 'categoryIds'])}"><span>General&nbsp;Info</span></a></li>
                        <li><a class="tabs" id="tabIngredients" style="${hasErrors(bean: recipeCO, field: 'hiddenIngredientProductNames', 'color:red;')}"><span>Ingredients</span></a></li>
                        <li><a class="tabs" id="tabCookingSteps" style="${hasErrors(bean: recipeCO, field: 'directions', 'color:red;')}"><span>Cooking&nbsp;Steps</span></a></li>
                        <li><a class="tabs" id="tabServeWith"><span>Serve&nbsp;With</span></a></li>
                        <li><a class="tabs" id="tabNutritionFacts" style="${hasErrors(bean: recipeCO, field: 'nutrientQuantities', 'color:red;')}"><span>Nutrition&nbsp;Facts</span></a></li>
                    </ul>
                </div>

                <g:form name="formCreateRecipe" controller="recipe" action="update" enctype="multipart/form-data">

                    <g:hiddenField name='id' value='${recipeCO.id}'/>
                    <g:render template="/recipe/generalInfo_new" model="[recipeCO: recipeCO, timeUnits: timeUnits]"/>
                    <g:render template="/recipe/ingredients_new" model="[recipeCO: recipeCO, metricUnits: metricUnits]"/>
                    <g:render template="/recipe/cookingSteps_new" model="[recipeCO: recipeCO, metricUnits: metricUnits]"/>
                    <g:render template="/recipe/serveWith_new" model="[recipeCO: recipeCO, metricUnits: metricUnits]"/>
                    <g:render template="/recipe/nutrientFacts_new" model="[recipeCO: recipeCO, metricUnits: metricUnits]"/>

                    <div class="bottom-shadow">
                        <label>&nbsp;</label>
                    </div>
                    <div id="button">
                        <ul>
                            <li><g:submitButton name="btnUpdate" class="button" value="Update Recipe" style="cursor:pointer;"/></li>
                            <li><input class="button" type="button" name="preview" id="preview" value="Preview" style="cursor:pointer;"/></li>
                            <li><input class="button" type="button" name="delete" id="delete" value="Delete" style="cursor:pointer;"/></li>
                        </ul>
                    </div>

                </g:form>

            </div>
            <g:render template="/recipe/preview_new"/>
        </div>
    </div>
</div>
<g:render template="/recipe/bindEvents_new"/>

</body>
</html>
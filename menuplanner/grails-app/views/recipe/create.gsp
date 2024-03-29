<%@ page import="com.mp.domain.*" %>
<html>
<head>
    <meta name="layout" content="menu"/>
    <title>Add Recipe</title>
    <rateable:resources/>
    <tinyMce:importJs/>
</head>
<body>

<div id="container">
    <div id="wrapper" class="clearfix">
        <div id="content-wrapper" class="clearfix">
            <!--  start left-panel -->
            <div id="left-panel">
                <!--  start left-panel -->
                <div class="headbox">
                    <h3>Add Recipe</h3>
                </div>
                <g:hasErrors bean="${recipeCO}">
                    <div id="displayRecipeErrors" class="errors">
                        <g:renderErrors bean="${recipeCO}"/>
                    </div>
                </g:hasErrors>
                <div class="top-shadow">
                    <label></label>
                </div>
                <div class=" clearfix">
                    <g:uploadForm name="formCreateRecipe">
                        <div class="tabPanel leftbox clearfix" id=panelGeneralInfo>
                            <div class=" clearfix"><g:render template="/recipe/generalInfo" model="[recipeCO: recipeCO, timeUnits: timeUnits]"/></div>
                            <div class=" clearfix"><g:render template="/recipe/ingredients" model="[recipeCO: recipeCO, metricUnits: metricUnits]"/></div>
                            <div class=" clearfix"><g:render template="/recipe/cookingSteps" model="[recipeCO: recipeCO, metricUnits: metricUnits]"/></div>
                            <div class=" clearfix"><g:render template="/recipe/serveWith" model="[recipeCO: recipeCO, metricUnits: metricUnits]"/></div>
                            <div class=" clearfix"><g:render template="/recipe/usesAlcohol" model="[recipeCO: recipeCO]"/></div>
                        </div>
                        <div class="bottom-shadow">
                            <label>&nbsp;</label>
                        </div>
                        <div id="button">
                            <ul>
                                <li>
                                    <g:actionSubmit class="button" controller="recipe" action="save" value="Create"/>
                                </li>
                                <li>
                                    <input type="button" name="preview" class="button previewButtonFT" id="preview" value="Preview"/>
                                </li>
                                <li>
                                    <g:actionSubmit class="button" controller="recipe" action="list" value="Cancel"/>
                                </li>
                            </ul>
                        </div>
                    </g:uploadForm>
                </div>
            </div>
            <g:render template="/recipe/preview"/>
        </div>
    </div>
</div>
<g:render template="/recipe/bindEventsToTabs"/>
<g:render template="unitAddPopup"/>
</body>
</html>
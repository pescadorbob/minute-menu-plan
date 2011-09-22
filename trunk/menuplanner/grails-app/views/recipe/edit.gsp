<%@ page import="com.mp.domain.*" %>
<html>
<head>
    <meta name="layout" content="menu"/>
    <title>Edit Recipe</title>
    <rateable:resources/>
    <tinyMce:importJs/>
</head>
<body>

<div id="container">
    <div id="wrapper" class="clearfix">
        <div id="content-wrapper" class="clearfix">
            <div id="left-panel">
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
                    </ul>
                </div>              
                <g:uploadForm name="formEditRecipe">
                    <g:hiddenField name='id' value='${recipeCO?.id}'/>
                    <div class="tabPanel leftbox clearfix" id=panelGeneralInfo>
                        <g:render template="/recipe/generalInfo" model="[recipeCO: recipeCO, timeUnits: timeUnits]"/>
                        <g:render template="/recipe/ingredients" model="[recipeCO: recipeCO, metricUnits: metricUnits]"/>
                        <g:render template="/recipe/cookingSteps" model="[recipeCO: recipeCO, metricUnits: metricUnits]"/>
                        <g:render template="/recipe/serveWith" model="[recipeCO: recipeCO, metricUnits: metricUnits]"/>
                        <g:render template="/recipe/usesAlcohol" model="[recipeCO: recipeCO]"/>
                    </div>
                    <div class="bottom-shadow">
                        <label>&nbsp;</label>
                    </div>
                    <div id="button">
                        <ul>
                            <li>
                                <g:actionSubmit class="button" controller="recipe" action="update" name="update" value="Update"/>
                            </li>
                            <li>
                                <input class="button" type="button" name="preview" id="preview" value="Preview"/>
                            </li>
                            <li>
                                <g:actionSubmit class="button" controller="recipe" action="delete" name="delete" value="Delete" onclick="return confirm('Are you sure?');"/>
                            </li>
                            <li>
                                <g:actionSubmit class="button" controller="recipe" action="show" name="cancel" value="Cancel"/>
                            </li>
                        </ul>
                    </div>
                </g:uploadForm>
            </div>
            <g:render template="/recipe/preview"/>
        </div>
    </div>
</div>
<g:render template="/recipe/bindEventsToTabs"/>
<g:render template="unitAddPopup"/>
</body>
</html>
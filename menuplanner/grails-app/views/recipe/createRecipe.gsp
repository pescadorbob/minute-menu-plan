<%@ page import="com.mp.domain.*" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main"/>
    <style type="text/css" media="screen">
    .columnName {
        color: green;
        font-weight: bolder;
    }
    </style>
    <title>test</title>
</head>
<body>
<div class="nav">
    <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
    <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]"/></g:link></span>
</div>
<div id="addRecipe" style="float:left; border:1px solid green; padding:10px;margin:20px;">
    <g:form name="formCreateRecipe" controller="recipe" action="saveRecipe">
        <div style="float:top; font-weight:bolder; font-size:12px; color:navy; background-color:#ddd; padding:10px;">
            RECIPE NAME:  <g:textField name="name" value=""/>
        </div>
        <div style="float:top;">
            <div Id="addRecipeForm" style="float:left; padding:20px;width:400px;">
                <span class="columnName">Categories:</span>
                <div id="category" style="padding-left:50px;">
                    <g:each in="${(1..3)}" var="count">
                        <g:select name="categoryIds" from="${Category.list()}" optionKey="id"/><br>
                    </g:each>
                </div>
                <br><span class="columnName">Prep Time:</span>
                <g:textField name="preparationTime" value="" style="width:40px;"/>
                <br><span class="columnName">Cook Time:</span>
                <g:textField name="cookTime" value="" style="width:40px;"/>
                <br><span class="columnName">Difficulty:</span>
                <g:radioGroup name="difficulty" values="${RecipeDifficulty.list()*.name()}"
                        value="1" labels="${RecipeDifficulty.list()*.name}">
                    ${it.radio} <g:message code="${it.label}"/>
                </g:radioGroup>
                <br>makes <g:textField name="makesServing" value="" style="width:40px;"/> Servings
                <br><span class="columnName">Share with Community:</span>
                <g:checkBox name="shareWithCommunity" value="${false}"/>
                <br><span class="columnName">Ingredients:<br></span>
                <div id="ingredients" style="padding-left:50px;">
                    <g:each in="${(1..3)}" var="count">
                        <g:textField name="ingredientQuantities" value="" style="width:40px;"/>
                        <g:select name="ingredientMetricIds" from="${Metric.list()}" optionKey="id"/>
                        <g:select name="ingredientProductIds" from="${Product.list()}" optionKey="id"/><br>
                    </g:each>
                </div>
                <br><span class="columnName">Cooking Steps:</span> <br>
                <div id="directions" style="padding-left:50px;">
                    <g:each in="${(1..3)}" var="count">
                        <g:textField name="directions" value=""/><br>
                    </g:each>
                </div>
                <div class="buttons" style="float:top; border:1px solid red;">
                    <span class="button"><g:submitButton name="btnSave" class="save" value="Save Recipe"/></span>
                </div>
            </div>
            %{--<div Id="recipeImage" style="float:left;">--}%
            %{--<span class="columnName">image:</span> <input type="file" name="image"/><br>--}%
            %{--<img src="${createLink(controller: 'image', action: 'imgRecipe', id: RecipeImage.findByRecipeId(1))}" width='200' height='200'/>--}%
            %{--<br><br>Nutrition Facts per Serving:--}%
            %{--<br>?? calories--}%
            %{--<br>?? total Fat--}%
            %{--<br>?? g Saturated Fat--}%
            %{--<br>?? mg Cholesterol--}%
            %{--<br>?? mg Sodium--}%
            %{--<br>?? g Carbohydrate--}%
            %{--<br>?? g Fiber--}%
            %{--<br>?? g Protein--}%
            %{--<br>--}%
            %{--</div>--}%
        </div>
    </g:form>
</div>
</body>
</html>
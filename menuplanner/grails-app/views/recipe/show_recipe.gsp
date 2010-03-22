<%@ page import="com.mp.domain.*" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main"/>
    <style type="text/css" media="screen">
        .columnName{
            color:green;
            font-weight:bolder;
        }
    </style>
    <title>test</title>
</head>
<body>
<div class="nav">
    <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
    <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
    <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
</div>
<div id="SHOW_RECIPE" style="float:left; border:1px solid green; padding:10px;margin:20px;">
    <div style="float:top; font-weight:bolder; font-size:12px; color:navy; background-color:#ddd; padding:10px;">
        RECIPE NAME:  ${recipeDetail?.recipeName}
    </div>
    <div style="float:top;">
            <div Id="FORM" style="float:left; padding:20px;width:400px;">
                <span class="columnName"> Categories: </span>
                <div id="CATEGORY" style="padding-left:50px;">
                    <g:each in="${recipeDetail?.categories}" var="c">
                        <br>${c}
                    </g:each>
                </div>
                <br><span class="columnName"> Prep Time: </span>
                ?? : ??
                <br><span class="columnName"> Cook Time: </span>
                ?? : ??
                <br><br><span class="columnName"> Difficulty: </span>
                <input type="radio" name="difficulty" checked="checked" value="easy"/> easy
                <input type="radio" name="difficulty" value="medium"/> medium
                <input type="radio" name="difficulty" value="difficult"/> difficult
                <br>makes <g:textField id="txtServings" name="txtServings" value="" style="width:40px;"/> Servings
                <br><span class="columnName"> Share with Community </span> <g:checkBox name="myCheckbox" value="${false}"/>
                <br><br><span class="columnName"> Ingradients:<br> </span>
                <div id="INGREDIENTS" style="padding-left:50px;">
                    <g:each in="${recipeDetail?.ingredients}" var="r">
                        <br>${r}
                    </g:each>
                </div>
                <br><span class="columnName"> Cooking Steps: </span> <br>
                <div id="STEPS" style="padding-left:50px;">
                    <g:each in="${recipeDetail?.directions}" var="r">
                       <br>${r}
                    </g:each>
                </div>
                <div class="buttons" style="float:top; border:1px solid red;">
                    <g:form>
                        <g:hiddenField name="id" value="${recipeDetail?.recipeId}" />
                        <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                        <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                    </g:form>
                </div>
            </div>
            <div Id="PHOTO" style="float:left;">
                <img src="${createLink(controller:'image',action:'imgRecipe', id:RecipeImage.findByRecipeId(1))}" width='200' height='200'/>
                <br><br>Nutrition Facts per Serving:
                <br>?? calories
                <br>?? total Fat
                <br>?? g Saturated Fat
                <br>?? mg Cholesterol
                <br>?? mg Sodium
                <br>?? g Carbohydrate
                <br>?? g Fiber
                <br>?? g Protein
                <br>
            </div>
    </div>
</div>
</body>
</html>

<%@ page import="com.mp.domain.RecipeDifficulty" %>
<div class=left-container2 id=panelGeneralInfo>
    <div class=min-height-container>
        <div class=add-recipe-form>
            <ul class=add-recipe-form-container>
                <li class=add-recipe-form-content5><strong>Name</strong></li>
                <li class="add-recipe-form-input">
                    <g:textField class="input1 ${hasErrors(bean:recipeCO,field:'name', 'errors')}" name="name" value="${recipeCO?.name}"/>
                </li>
            </ul>
            <ul class=add-recipe-form-container>
                <li class=add-recipe-form-content><strong>Categories</strong></li>
                <li class=add-recipe-form-input>
                    <span class="${hasErrors(bean: recipeCO, field: 'categoryIds', 'errors')}" style="display:block; clear:both;">
                        <mp:tagInput name="categoryIds" controller="recipe" action="getMatchingCategories"/>
                    </span></li>
            </ul>
            <ul class="add-recipe-form-container" style="clear:both;">
                <li class="add-recipe-form-content" style="clear:both;"><strong>Prep Time</strong></li>
                <li class=add-recipe-form-input>
                    <g:textField class="input4 ${hasErrors(bean:recipeCO,field:'preparationTime', 'errors')}" name="preparationTime" value="${recipeCO?.preparationTime}"/>
                    <g:select class="select2" name="preparationUnitId" from="${timeUnits}" optionKey="id" value="${recipeCO?.preparationUnitId}"/>
                </li>
            </ul>
            <ul class=add-recipe-form-container5>
                <li class=add-recipe-form-content><strong>Cook Time</strong></li>
                <li class=add-recipe-form-input>
                    <g:textField class="input4 ${hasErrors(bean:recipeCO,field:'cookTime', 'errors')}" name="cookTime" value="${recipeCO?.cookTime}"/>
                    <g:select class="select2" name="cookUnitId" from="${timeUnits}" optionKey="id" value="${recipeCO?.cookUnitId}"/>
                </li>
            </ul>
            <ul class=add-recipe-form-container4>
                <li class="add-recipe-form-content"><strong>Difficulty</strong></li>
                <li class="add-recipe-form-input-radio">
                    <g:radioGroup name="difficulty" values="${RecipeDifficulty.list()*.name()}" value="${recipeCO? (recipeCO?.difficulty): RecipeDifficulty.EASY.name()}" labels="${RecipeDifficulty.list()*.name}">
                        ${it.radio} <strong><g:message code="${it.label}"/></strong>
                    </g:radioGroup>
                </li>
            </ul>
            <ul class="add-recipe-form-container" style="clear:both;">
                <li class=add-recipe-form-content><strong>Makes</strong></li>
                <li class=add-recipe-form-input>
                    <div class=clr>
                        <div class=add-recipe-form-input4>
                            <g:textField class="input4 ${hasErrors(bean:recipeCO,field:'makesServing', 'errors')}" name="makesServing" value="${recipeCO?.makesServing}" style=""/>
                        </div>
                        <div class=add-recipe-form-input4><strong>Servings</strong></div>
                    </div>
                </li>
                <li class=add-recipe-form-input style="margin-left: 100px">
                    <g:checkBox style="border:0px; float:left; width:auto;" name="shareWithCommunity" value="${recipeCO?.shareWithCommunity}"/>
                    <strong style="float:left;
                    display:block; width:auto; padding-left:5px;">Share with community</strong></li>
            </ul>
            <div class=clr></div>
        </div>
        <div class="add-recipe-form5">
            <ul class="add-recipe-form-container">
                <li class="add-recipe-form-input2">
                    <div style="float:left; height:25px;line-height:25px;padding-right:10px;"><strong>image</strong></div>
                    <div>
                        <input id="selectRecipeImage" size="1" name="selectRecipeImage" class="input3" type="file"/>
                        <img id="removeRecipeImage" src="${resource(dir: 'images', file: 'remove.jpg')}" alt="Remove" border="0" height="28px;" style="cursor:pointer"/>
                    </div>
                </li>
            </ul>
            <div class="clr">
                %{--<mp:recipeImageById id="7"/>--}%
                <div id="myImageDiv">
                    %{--<img id='recipeImage'  border='0' width='195' src=""/> --}%
                    <mp:recipeImageByPath selectRecipeImagePath="${recipeCO?.selectRecipeImagePath}"/>
                </div>
                %{--<img id="recipeImage" src="${resource(dir: 'images', file: '')}" border="0" width="195" height="171" style="visibility:hidden;"/>--}%
                <input type="hidden" name="selectRecipeImagePath" id="selectRecipeImagePath" value="${recipeCO?.selectRecipeImagePath}"/>
            </div>
        </div>
    </div>
         <div>
                        <img src="${resource(dir: 'images', file: 'left-container-img1.jpg')}"/>
                    </div>
</div>
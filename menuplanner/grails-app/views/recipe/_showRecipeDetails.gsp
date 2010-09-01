<%@ page import="com.mp.domain.Recipe" %>
<div id="contectElement">
    <ul>
        <li id="leftLiElements">
            <ul>
                <li><g:if test="${recipe?.description}"><span>Description: ${recipe?.description}</span><br/></g:if></li>
                <li id="prepAndCookTimesTst">
                    <g:if test="${recipe?.preparationTime}">Prep - ${recipe.preparationTime.toReadableTimeString()}</g:if><br/>
                    <g:if test="${recipe?.cookingTime}">Cook - ${recipe.cookingTime.toReadableTimeString()}</g:if><br/>
                </li>
                <li><g:if test="${recipe?.difficulty}"><span>Difficulty Level: ${recipe?.difficulty}</span><br/></g:if>
                    <g:if test="${recipe?.servings}"><span>Servings: ${customServings ? customServings : recipe?.servings}</span></g:if>
                </li>
            %{--<li>--}%
            %{--${recipe?.isAlcoholic ? 'Recipe contains Alcoholic contents' : 'Recipe is Non-Alcoholic'}--}%
            %{--</li>--}%
                <g:if test="${recipe?.ingredients}">
                    <li id="showAllIngredientsHereTst">
                        <mp:recipeIngredients recipeId="${recipe?.id}" customServings="${customServings}"/>
                    </li>
                </g:if>
                <g:if test="${recipe?.directions}">
                    <li id="showAllStepsHereTst">Directions: ${recipe?.directions*.toString().join(" ")}</li>
                </g:if>
                <g:if test="${recipe?.subCategories}">
                    <li style="width: 500px;">Categories: ${recipe?.subCategories?.join(", ")}</li>
                </g:if>
                <g:if test="${recipe?.nutrients}">
                    <li><span id="showNutrientsTst" style="width:auto;">Nutritional Facts per serving: ${recipe?.nutrients?.join(", ")}</span></li>
                </g:if>
                <g:if test="${recipe?.items}">
                    <li><span id="showServeWithTst">Serve With:
                    <g:each in="${recipe?.items}" var="item" status="i">
                        <g:if test="${item?.instanceOf(Recipe)}">
                            <g:link class="recipeServeWithFT" action="show" controller="recipe" id="${item?.id}">${item}</g:link>
                            <g:if test="${i < recipe?.items?.size()-1}">,</g:if>
                        </g:if>
                        <g:else>
                            ${item}<g:if test="${i < recipe?.items?.size()-1}">,</g:if>
                        </g:else>
                    </g:each>
                    </span></li>
                </g:if>
                <li></li>
            </ul>
        </li>
        <li id="rightLiElements">
            <div id="photo200" class="scaleImageSize">
                <mp:image class="recipeImage" size="${imageSize}" id="${recipe?.image?.id}"/>
            </div>
            <br/>
            <g:link controller="user" action="alterFavorite" name="changeFavorite" id="${recipe?.id}">
                <span id="showFavorite" style="text-align:right;"><mp:showFavorite recipeId="${recipe?.id}"/></span>
            </g:link> &nbsp;&nbsp;
            <span id="showRecipeAbuse" style="text-align:right;"><mp:showRecipeAbuse recipeId="${recipe?.id}"/></span>
        </li>
    </ul>
    <g:if test="${isPrintable}">
        <mp:commentsForPrinting recipeId="${recipe?.id}"/>
    </g:if>
    <g:else>
        <mp:comments recipeId="${recipe?.id}"/>
    </g:else>
</div>

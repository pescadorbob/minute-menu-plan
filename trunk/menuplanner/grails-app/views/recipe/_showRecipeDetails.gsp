<div id="contectElement">
    <ul>
        <li id="leftLiElements">
            <ul>
                <li id="prepAndCookTimesTst">
                    <g:if test="${recipe?.preparationTime}">Prep - ${recipe.preparationTime.toReadableTimeString()}</g:if><br/>
                    <g:if test="${recipe?.cookingTime}">Cook - ${recipe.cookingTime.toReadableTimeString()}</g:if><br/>
                </li>
                <li><g:if test="${recipe?.difficulty}"><span>Difficulty Level: ${recipe?.difficulty}</span><br/></g:if>
                    <g:if test="${recipe?.servings}"><span>Servings: ${recipe?.servings}</span></g:if>
                </li>
                %{--<li>--}%
                    %{--${recipe?.isAlcoholic ? 'Recipe contains Alcoholic contents' : 'Recipe is Non-Alcoholic'}--}%
                %{--</li>--}%
                <g:if test="${recipe?.ingredients}">
                    <li id="showAllIngredientsHereTst">
                        <g:each in="${recipe?.ingredients}" var="ingredient">
                            <span><strong>${ingredient}</strong></span><br/>
                        </g:each>
                    </li>
                </g:if>
                <g:if test="${recipe?.directions}">
                    <li id="showAllStepsHereTst">${recipe?.directions*.toString().join(" ")}</li>
                </g:if>
                <g:if test="${recipe?.subCategories}">
                    <li style="width: 500px;">Categories: ${recipe?.subCategories?.join(", ")}</li>
                </g:if>
                <g:if test="${recipe?.nutrients}">
                    <li><span id="showNutrientsTst" style="width:auto;">Nutritional Facts per serving: ${recipe?.nutrients?.join(", ")}</span></li>
                </g:if>
                <g:if test="${recipe?.items}">
                    <li><span id="showServeWithTst">Serve With: ${recipe?.items?.join(", ")}</span></li>
                </g:if>
                <li></li>
            </ul>
        </li>
        <li id="rightLiElements">
            <mp:image id="${recipe?.image?.id}" height="160" width="160"/><br/>
            <g:link controller="user" action="alterFavorite" name="changeFavorite" id="${recipe?.id}">
                <span id="showFavorite" style="text-align:right;"><mp:showFavorite recipeId="${recipe?.id}"/></span>
            </g:link> &nbsp;&nbsp;
            <span id="showRecipeAbuse" style="text-align:right;"><mp:showRecipeAbuse recipeId="${recipe?.id}"/></span>
        </li>
    </ul>
    <mp:comments recipeId="${recipe?.id}"/>
</div>

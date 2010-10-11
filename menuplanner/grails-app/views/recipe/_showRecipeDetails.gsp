<%@ page import="com.mp.domain.LoginCredential; com.mp.domain.Recipe" %>
<div id="contectElement">
    <ul>
        <li id="leftLiElements">
            <ul>
                <li id="prepAndCookTimesTst">
                    <g:if test="${recipe?.preparationTime}">Prep - ${recipe.preparationTime.toReadableTimeString()}</g:if><br/>
                    <g:if test="${recipe?.cookingTime}">Cook - ${recipe.cookingTime.toReadableTimeString()}</g:if><br/>
                </li>
                <li><g:if test="${recipe?.difficulty}"><span>Difficulty Level: ${recipe?.difficulty}</span><br/></g:if>
                    <g:if test="${recipe?.servings}"><span>Servings: ${customServings ? customServings : recipe?.servings}</span></g:if>
                </li>
                <li><strong>${recipe?.isAlcoholic ? 'Contains Alcohol' : ''}</strong></li>
                <g:if test="${recipe?.ingredients}">
                    <li id="showAllIngredientsHereTst">
                        <mp:recipeIngredients recipeId="${recipe?.id}" customServings="${customServings}"/>
                    </li>
                </g:if>
                <g:if test="${recipe?.directions}">
                    <li id="showAllStepsHereTst" class="showAllStepsHere">Directions: ${recipe?.directions*.toString().join(" ")}</li>
                </g:if>
                <g:if test="${recipe?.subCategories}">
                    <li style="width: 500px;">Categories: ${recipe?.subCategories?.join(", ")}</li>
                </g:if>
                <g:if test="${recipe?.nutrients}">
                    <li><span id="showNutrientsTst" style="width:auto;">Nutritional Facts per serving: ${recipe?.nutrients?.join(", ")}</span></li>
                </g:if>
                <li>
                    <g:if test="${recipe?.items}">
                        <mp:serveWithItems recipeId="${recipe?.id}"/>
                    </g:if>
                </li>
                <li></li>
                <li>
                    <g:render template="/recipe/contributor" model="[recipe:recipe]"/>
                </li>
                <li></li>

            </ul>
        </li>
        <li id="rightLiElements">
            <g:if test="${printRecipe}">
                <div id="photo200" style="overflow:hidden;">
                    <table cellpadding="0" cellspacing="0" border="0">
                        <tr>
                            <td height="200" width="200" valign="middle" align="center">
                                <mp:printRecipeImage size="${imageSize}" id="${recipe?.image?.id}"/>
                            </td>
                        </tr>
                    </table>
                </div>
            </g:if>
            <g:else>
                <div id="photo200" class="scaleImageSize">
                    <mp:image class="recipeImage" size="${imageSize}" id="${recipe?.image?.id}"/>
                </div>
            </g:else>
            <br/>
            <g:if test="${LoginCredential.currentUser}">
                <g:link controller="user" action="alterFavorite" name="changeFavorite" id="${recipe?.id}">
                    <span id="showFavorite" style="text-align:right;"><mp:showFavorite recipeId="${recipe?.id}"/></span>
                </g:link> &nbsp;&nbsp;
                <span id="showRecipeAbuse" style="text-align:right;"><mp:showRecipeAbuse recipeId="${recipe?.id}"/></span>
            </g:if>
            <g:else>
                <g:link controller="user" action="createFreeUser" style="color:#fff"><img src="${resource(dir: 'images', file: 'click-FreeUserSignup.jpg')}"></g:link>
            </g:else>
        </li>
    </ul>
    <g:if test="${LoginCredential.currentUser}">
        <g:if test="${isPrintable}">
            <mp:commentsForPrinting recipeId="${recipe?.id}"/>
        </g:if>
        <g:else>
            <mp:comments recipeId="${recipe?.id}"/>
        </g:else>
    </g:if>
    <g:else>
        <div class="sharedLinkUserRecipeLink">
            <div class="boxDiv">The Minute Menu plan is an online community based software service offered to you with our compliments. Create a free account now and take it for a spin, and let us know what you think.</div>
            <g:link controller="user" action="createFreeUser"><img src="${resource(dir: 'images', file: 'click-FreeUserSignup.jpg')}"></g:link>
        </div>
    </g:else>
</div>

<%@ page import="com.mp.domain.Recipe" %>
<div id="right-panel">
    <div class="headbox">
        <h3>
            Recipe Preview
        </h3>
    </div>
    <div id="right-head">
        <label>
            <span id="displayName"></span>
        </label>

        <g:if test="${recipeCO?.id}">
            <rateable:ratings bean='${Recipe.get(recipeCO?.id?.toString()?.toLong())}' active="false"/>
        </g:if>
        %{--<g:render template="/recipe/rating"/>--}%
    </div>
    <div class="top-shadow">
        <label>&nbsp;</label>
    </div>

    <div class="leftbox clearfix">
        %{--<img id="displayRecipeImage" src="" width="80" height="80" align="right" style="float:right">--}%
        <div id="rightElement">
            <div style="float:right; width:150px;">
                <img id="displayRecipeImage" src="" width="150" height="150"/>
            </div>
            <span id="displayDescription"></span>
            <br/>
            <span id="displayPrepTime"></span>
            <br/>
            <span id="displayCookTime"></span>
            <p><span id="displayDifficulty"></span></p>
            <p><span id="displayMakeServing"></span></p>
            <p>&nbsp;</p>
            <p><strong><span id="displayIngredients"></span></strong></p>
            <ul>
                <li><span id="displayDirections"></span></li>
            </ul>
            <p><span id="displayCategory"></span></p>
            <p><span id="displayServeWith"></span></p>
            <p><span id="showNutrients"></span></p>
            <p><strong><a href="#"></a></strong></p>
        </div>
    </div>
    <div class="bottom-shadow">
        <label>&nbsp;</label>
    </div>
</div>

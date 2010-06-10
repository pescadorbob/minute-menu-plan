<%@ page import="com.mp.domain.*" %>
<%@ page import="com.mp.MenuConstants.*" %>
<html>
<head>
    <title>Show Recipe ${recipe?.name}</title>
    <meta name="layout" content="menu"/>
    <g:javascript src="jquery.printElement.min.js"/>
    <rateable:resources/>
    <script type="text/javascript">
        jQuery(function() {
            jQuery("#printButton").click(function() {
                window.open("${createLink(controller:'recipe',action:'printRecipes',params:[id:recipe?.id])}", 'print_Recipe','width=800, height=800')
                return false;
            });
        });
        function printElem(options) {
            jQuery(".printMe:first").printElement(options);
        }
    </script>
</head>
<body>
<div class="clearfix printMe" id="content-wrapper">
    <div id="left-panel">
        <g:if test="${flash.message}">
            <div class="flashMessage">${flash.message}</div>
        </g:if>
        <div id="right-head">
            <div id="leftpart">
                <label id="recipeNameTst">${recipe?.name}</label>
                <span id="spanRateable"><rateable:ratings bean='${recipe}'/></span>
            </div>
            <div id="rightpart">
                <span>
                    <mp:showEditRecipe recipeId="${recipe?.id}"/>
                </span>
                <span><img src="${resource(dir: 'images', file: 'printer.gif')}"/><a href="#" id="printButton">Print</a></span>
            </div>
        </div>
        <div class="top-shadow"><label></label></div>
        <div class="leftbox clearfix">
            <div id="contectElement">
                <ul>
                    <li>
                        <ul>
                            <li id="prepAndCookTimesTst">
                                <g:if test="${recipe?.preparationTime}">Prep - ${recipe?.preparationTime}</g:if><br/>
                                <g:if test="${recipe?.cookingTime}">Cook - ${recipe?.cookingTime}</g:if><br/>
                            </li>
                            <li><g:if test="${recipe?.difficulty}"><span>Difficulty Level: ${recipe?.difficulty}</span></g:if>
                                <g:if test="${recipe?.servings}"><span>Servings: ${recipe?.servings}</span></g:if>
                            </li>
                            <g:if test="${recipe?.ingredients}">
                                <li id="showAllIngredientsHereTst"><g:each in="${recipe?.ingredients}" var="ingredient"><span><strong>${ingredient}</strong></span></g:each></li>
                            </g:if>
                            <g:if test="${recipe?.directions}">
                                <li id="showAllStepsHereTst">${recipe?.directions*.toString().join(" ")}</li>
                            </g:if>
                            <g:if test="${recipe?.categories}"><li>Categories: ${recipe?.categories?.join(", ")}</li></g:if>
                            <g:if test="${recipe?.nutrients}">
                                <li><span id="showNutrientsTst" style="width:auto;">Nutritional Facts per serving: ${recipe?.nutrients?.join(", ")}</span></li>
                            </g:if>
                            <g:if test="${recipe?.items}">
                                <li><span id="showServeWithTst">Serve With: ${recipe?.items?.join(", ")}</span></li>
                            </g:if>
                            <li></li>
                        </ul>
                    </li>
                    <li style="text-align: right;">
                        <mp:recipeImage id="${recipe?.image?.id}" height="160" width="160" noImage="no-img.gif"/><br/>
                        <g:link controller="user" action="alterFavorite" name="changeFavorite" id="${recipe?.id}">
                            <span id="showFavorite" style="text-align:right;"><mp:showFavorite recipeId="${recipe?.id}"/></span>
                        </g:link> &nbsp;&nbsp;
                        <span id="showRecipeAbuse" style="text-align:right;"><mp:showRecipeAbuse recipe="${recipe}"/></span>
                    </li>                                       
                </ul>
                <mp:comments recipe="${recipe}"/>
            </div>
        </div>
        <div class="bottom-shadow"><label></label></div>
    </div>
    <div id="right-panel">
    </div>
</div>
<script type="text/javascript">
    window.onload = preventRatingClickEvent;
    function preventRatingClickEvent() {
        $(".rating a").click(function(e) {
            e.preventDefault()
        })
    }
</script>
</body>
</html> 
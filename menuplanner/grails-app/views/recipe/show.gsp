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
                window.open("${createLink(controller:'recipe',action:'printRecipes',params:[ids:recipe?.id])}", 'print_Recipe', 'width=800, height=800')
                return false;
            });
        });
        function printElem(options) {
            jQuery(".printMe:first").printElement(options);
        }
    </script>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'custom-ratings-inner.css')}"/>
    <style type="text/css">
    .tooltip {
        background: transparent url('${resource(dir: 'images', file: '/tooltip/white_arrow-small.png')}');
        height: 40px;
        width: 210px;
    }

    .tooltip.bottom {
        background: transparent url('${resource(dir: 'images', file: '/tooltip/white_arrow_inverse-small.png')}');
        height: 30px;
    }

    </style>
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
                <span><img src="${resource(dir: 'images', file: 'printer.gif')}"/><a href="#" id="printButton" title="${g.message(code: 'test.clueTip')}">Print</a></span>
            </div>
        </div>
        <div class="top-shadow"><label></label></div>
        <div class="leftbox clearfix">
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
                    <li  id="rightLiElements">
                        <mp:image id="${recipe?.image?.id}" height="160" width="160"/><br/>
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
    <div id="showPrintInstructions">
        <g:render template="/recipe/printInstructions"/>
    </div>
</div>
<script type="text/javascript">
    window.onload = preventRatingClickEvent;
    function preventRatingClickEvent() {
        $(".rating a").click(function(e) {
            e.preventDefault()
        })
    }
    jQuery(document).ready(function() {
        jQuery('#showPrintInstructions').hide();
        $("#printButton").tooltip({events: {
            input: "focus mouseenter"
        },
            effect:'slide',
            sticky:true
        }).dynamic({ bottom: { direction: 'down', bounce: false } })
    });
    function showPrintInstruction() {
        jQuery('#showPrintInstructions').show()
        return false;
    }
</script>
</body>
</html> 
<%@ page import="com.mp.domain.*" %>
<%@ page import="com.mp.MenuConstants.*" %>
<html>
<head>
    <title>${recipe?.name}</title>
    <meta name="layout" content="menu"/>
    <g:javascript src="jquery.printElement.min.js"/>
    <rateable:resources/>
    <script type="text/javascript">
        jQuery(function() {
            jQuery("#printButton").click(function() {
                printElem({ pageTitle: '${recipe?.name}' });
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
    <!--  start left-panel -->
    <div id="left-panel">
    <!--  start left-panel -->

        <g:if test="${flash.message}">
            <div class="flashMessage">${flash.message}</div>
        </g:if>


        <div id="right-head">
            <div id="leftpart">
                <label>${recipe?.name}</label>
                %{--<g:render template="/rating/rating"/>--}%
                <span id="spanRateable"><rateable:ratings bean='${recipe}'/></span>
            </div>
            <div id="rightpart">
                <span>
                    <mp:showEditRecipe recipeId="${recipe?.id}" userId="${session?.loggedUserId}"/>
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
                            <li>
                                <g:if test="${recipe?.preparationTime}">Prep - ${recipe?.preparationTime}</g:if><br/>
                                <g:if test="${recipe?.cookingTime}">Cook - ${recipe?.cookingTime}</g:if><br/>
                            </li>
                            <li>
                                <g:if test="${recipe?.difficulty}">
                                    <span>Difficulty Level: ${recipe?.difficulty}</span>
                                </g:if>
                                <g:if test="${recipe?.servings}">
                                    <span>Servings: ${recipe?.servings}</span>
                                </g:if>
                            </li>
                            <g:if test="${recipe?.ingredients}">
                                <li>
                                    <g:each in="${recipe?.ingredients}" var="ingredient">
                                        <span><strong>${ingredient}</strong></span>
                                    </g:each>
                                </li>
                            </g:if>
                            <g:if test="${recipe?.directions}">
                                <li>${recipe?.directions*.toString().join(" ")}</li>
                            </g:if>
                            <g:if test="${recipe?.categories}">
                                <li>Categories: ${recipe?.categories?.join(", ")}</li>
                            </g:if>
                            <g:if test="${recipe?.nutrients}">
                                <li><span>Nutritional Facts per serving:</span>
                                    <g:each in="${recipe?.nutrients}" var="nutrient">
                                        <span>${nutrient}</span>
                                    </g:each>
                                </li>
                            </g:if>
                            <g:if test="${recipe?.items}">
                                <li><span>Serve With: ${recipe?.items?.join(", ")}</span></li>
                            </g:if>
                            <li>
                            </li>
                        </ul>
                    </li>
                    <li style="text-align: right;">
                        <mp:recipeImage id="${recipe?.image?.id}" height="160" width="160" noImage="no-img.gif"/><br/>
                        <g:link controller="user" action="alterFavorite" name="changeFavorite" id="${recipe?.id}">
                            <span id="showFavorite" style="text-align:right;">
                                <mp:showFavorite recipeId="${recipe?.id}" userId="${session?.loggedUserId}"/>
                            </span>
                        </g:link> &nbsp; &nbsp;
                        <span id="showRecipeAbuse" style="text-align:right;">
                            <mp:showRecipeAbuse recipe="${recipe}" userId="${session?.loggedUserId}"/>
                        </span>
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
</body>
</html> 
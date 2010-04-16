<%@ page import="com.mp.domain.*" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Minute Menu Plan</title>
    <meta name="layout" content="menuPlanner"/>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'createRecipe.js')}"></script>
</head>
<body>

<div class="month-menu">
    <div class="add-recipe-rgt1">
        <div>
            <img src="${resource(dir: 'images', file: 'add-rgt-img4.jpg')}"/>
        </div>
        <div class="month-menu2">
            <div class="month-menu-container">
                <div class="add-rgt-hdr">
                    <div class="add-rgt-hdr1">
                        <img align="left" src="${resource(dir: 'images', file: 'add-rgt-hdr1.jpg')}"/>
                        <span id="displayName">
                            ${recipe?.name}
                        </span>
                    </div>
                    <img align="left" src="${resource(dir: 'images', file: 'add-rgt-hdr3.jpg')}"/>
                    <div class="rating">
                        Rating
                        <g:render template="/recipe/rateing"/>
                        <img src="${resource(dir: 'images', file: 'star2.jpg')}"/>
                    </div>
                    <div class="report-text">
                        Prep - ${recipe?.preparationTime}. Cook - ${recipe?.cookingTime}.
                    </div>
                </div>
                <div class="month-menu-container-hdr">
                    <ul>
                        <li><img src="${resource(dir: 'images', file: 'edit-icon.jpg')}"/></li>
                        <li><strong>
                            <g:link controller="recipe" action="edit" id="${recipe?.id}">
                                Edit
                            </g:link>
                        </strong>
                        </li>
                        <li><img src="${resource(dir: 'images', file: 'print-icon.jpg')}"/></li>
                        <li><strong>Print</strong></li>
                    </ul>
                </div>
                <div style="float: left; width: 413px;">
                    <p>
                        <span id="displayDifficulty">
                            Difficulty Level: ${recipe?.difficulty?.name}
                        </span>
                    </p>
                    <p>
                        <span id="displayMakeServing">
                            Servings: ${recipe?.servings}
                        </span>
                    </p>
                    <br>
                    <p>
                        <strong>
                            <span id="displayIngredients">
                                ${recipe?.categoriesString} <br> 
                                <g:each in="${recipe?.ingredients}">
                                    ${it}<br>
                                </g:each>
                            </span>
                        </strong>
                </div>
                <div style="float: right;">

                    <img height="130" width="148" src="${g.createLink(controller: 'recipe', action: 'showImage', id: recipe?.id)}"/>

                    <div class="rating1">
                        <img src="${resource(dir: 'images', file: 'star1.jpg')}"/> Rating<br/>
                        <a href="#">text link</a>
                    </div>
                </div>
                <p class="clr"/>
                <span class="month-menu-content" id="displayDirections">
                    <g:each in="${recipe?.directions}">
                        ${it}<br>
                    </g:each>
                </span>
                <p>&nbsp;</p>
                <p>
                    <span id="displayCategory">
                        Categories:
                        <g:each in="${recipe?.categories}" status="i" var="category">
                            ${category}<g:if test="${( i < (recipe?.categories?.size()-1 ))}">,</g:if>
                        </g:each>
                    </span>
                </p>
                <br>
                <p class="serve-link">Serve With<br/>
                    <g:each in="${recipe?.items}" status="i" var="item">
                        <a href="#" class="serve-link">${item}</a><g:if test="${( i < (recipe?.items?.size()-1 ))}">,</g:if>
                    </g:each>
                </p>
                <br>
                <p>Nutritional Facts per serving: &nbsp;
                <g:each in="${recipe?.nutrients}" status="i" var="nutrient">
                    ${nutrient?.nutrient?.name}
                    ${nutrient?.quantity}
                    <g:if test="${( i < (recipe?.nutrients?.size()-1 ))}">,</g:if>
                </g:each>
                </p>
                <div class="month-menu-list">
                    <g:each in="${(1..4)}">
                        <div class="month-menu-list-container">
                            <div class="month-menu-list1">
                                <g:render template="/recipe/rateing"/>
                            </div>
                            <div class="month-menu-list1">lorem ipsum lorem ipsum</div>
                            <div class="month-menu-list1"><a href="#">lorem ipsum</a></div>
                        </div>
                    </g:each>
                </div>
            </div>
        </div>
        <div>
            <img src="${resource(dir: 'images', file: 'add-rgt-img6.jpg')}"/>
        </div>
    </div>
</div>
</body>
</html>
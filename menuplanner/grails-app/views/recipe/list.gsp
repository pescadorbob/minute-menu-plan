<%@ page import="com.mp.domain.*" %>
<html>
<head>
    <meta name="layout" content="menu"/>
    <title>List Recipe</title>
    <rateable:resources/>
</head>
<body>
<div id="container">
    <div id="wrapper" class="clearfix">
        <div id="content-wrapper" class="clearfix">
            <g:formRemote
                    name="searchForm"
                    url="[controller:'recipe', action:'search']"
                    update="rightContainer">

                <span id="searchParams" style="display:none;"></span>
                <span style="display:none;"><input type="submit"/></span>

                <g:render template="/recipe/search" model="['categoryList':categoryList]"/>
            </g:formRemote>

            <div id="right-panel-product">

                <g:if test="${flash.message}">
                    <div class="flashMessage">${flash.message}</div>
                </g:if>

                <div class="top-shadow">
                    <label>&nbsp;</label>
                </div>
                <div id="rightContainer" class="leftbox clearfix">
                    <g:render template="/recipe/searchResultRecipe" model="['recipeList':recipeList]"/>
                </div>
                <div class="bottom-shadow">
                    <label>&nbsp;</label>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>

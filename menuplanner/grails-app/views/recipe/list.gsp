<%@ page import="com.mp.domain.*" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Minute Menu Plan</title>
    <meta name="layout" content="menuPlanner"/>
</head>
<body>

<div class="browse-recipes-container">
    <div class="browse-recipes">
        <div>
            <img src="${resource(dir: 'images', file: 'quick-recipe-search-img1.jpg')}" width="216"/>
        </div>
        <g:formRemote
                name="searchForm"
                url="[controller:'recipe', action:'search']"
                update="rightContainer">

            <g:render template="/recipe/searchPanel" model="['categoryList':categoryList]"/>

        </g:formRemote>
        <div>
            <img src="${resource(dir: 'images', file: 'add-rgt-img3.jpg')}" width="216">
        </div>
    </div>

</div>
<div id="rightContainer" class="browse-recipes-container-right">
    <g:render template="/recipe/searchResultPanel" model="['recipeList':recipeList]"/>
</div>
</body>
</html>
<%@ page import="com.mp.domain.*" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Minute Menu Plan</title>
    <meta name="layout" content="menuPlanner"/>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'createRecipe.js')}"></script>
    <script src="${resource(dir: 'js', file: 'jquery-1.4.2.min.js')}" type="text/javascript"></script>
</head>
<body>

<div class="browse-recipes-container">
    <div class="browse-recipes">
        <div>
            <img src="${resource(dir: 'images', file: 'quick-recipe-search-img1.jpg')}" width="216"/>
        </div>

        <div class="browse-recipes-container-content2">
            <div class="browse-recipes-search-input">
                <input name="" type="text"/>
            </div>
            <span class="header2">
                You’ve Selected
            </span>
            <div class="close-container">
                <span>
                    <img src="${resource(dir: 'images', file: 'delete-icon.jpg')}"/>
                </span>
                <span>
                    loremipsum
                </span>
                <div class="clr">

                </div>
            </div>
            <div class="narrow-container2">
                Narrow Your Search
            </div>
            <div>
                <select class="select4" name="">
                    <option>test1</option>
                    <option>test2</option>
                    <option>test3</option>
                    <option>test4</option>
                </select>
                <div class="narrow-text2">
                    <p>
                        <span class="narrow-text-header">
                            Calories
                        </span>
                        <br/>
                        <a href="#">0-500</a><br/>
                        <a href="#">501-1000</a><br/>
                        <a href="#">1000+</a></p>
                    <p>
                        <span class="narrow-text-header">Difficulty</span>
                        <br/>
                        <a href="#">Easy</a><br/>
                        <a href="#">Medium</a><br/>
                        <a href="#">Difficult</a></p>
                    <p><span class="narrow-text-header">Total Time</span><br/>
                        <a href="#">0-30 min.</a><br/>
                        <a href="#">31-60 min.</a><br/>
                        <a href="#">1-2 hrs.</a><br/>
                        <a href="#">2+ hrs.</a>
                    </p>
                </div>
            </div>
        </div>

        <div>
            <img src="${resource(dir: 'images', file: 'add-rgt-img3.jpg')}" width="216">
        </div>
    </div>

</div>

<div class="browse-recipes-container-right">

    <div>
        <img src="${resource(dir: 'images', file: 'add-rgt-img4.jpg')}" width="770"/>
    </div>

    <div class="browse-recipes-container-content1">
        <div class="min-height-container2">
            <g:each in="${(recipeList)}" var="recipe" status="i">
                <div class="youhave-selected-container2">
                    <div class="browse-recipes-content-thmb recipeThumb" style="width:214px; height:120px;cursor:pointer">
                        <a href="">
                        <div class="selected-data">
                            <a href="${createLink(controller:'recipe', action:'show', id:recipe?.id)}">
                                ${recipe?.name}
                            </a>
                            <br/>
                            <img height="80" width="80" src="${g.createLink(controller: 'recipe', action: 'showImage', id: recipe?.id)}" style="border:0px;"/>
                            %{--<img src="${resource(dir: 'images', file: 'img1.jpg')}"/>--}%
                        </div>

                        <div class="selected-data2">
                            <div class="star-container">
                                <img src="${resource(dir: 'images', file: 'star1.jpg')}" width="13" height="12"/>
                                <img src="${resource(dir: 'images', file: 'star1.jpg')}" width="13" height="12"/>
                                <img src="${resource(dir: 'images', file: 'star1.jpg')}" width="13" height="12"/>
                                <img src="${resource(dir: 'images', file: 'star1.jpg')}" width="13" height="12"/>
                                <img src="${resource(dir: 'images', file: 'star1.jpg')}" width="13" height="12"/>
                            </div><em>
                            ${recipe?.preparationTime}<br/>
                            ${recipe?.cookingTime}<br/>
                            ${recipe?.difficulty}</em><br/>
                            %{--Round Cut Beef<br/>--}%
                            %{--Broccoli<br/>--}%
                            %{--Onions...--}%
                        </div>
                        <div class="clr"></div>
                        </a>
                    </div>
                </div>
            </g:each>
            <div class="clr"></div>
        </div>
        <div>
            <img src="${resource(dir: 'images', file: 'left-container-img1.jpg')}" width="770" align="left">
        </div>
    </div>
</div>
<script type="text/javascript">
    jQuery(document).ready(function() {
        jQuery('.recipeThumb').hover(function(){
            jQuery(this).css('backgroundColor','#eee')
        }, function(){
            jQuery(this).css('backgroundColor','#fff')
        })

    })
</script>

</body>
</html>
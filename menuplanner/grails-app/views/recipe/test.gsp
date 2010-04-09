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
                            ${recipe?.name}</span></div>
                    <img align="left" src="${resource(dir: 'images', file: 'add-rgt-hdr3.jpg')}"/>
                    <div class="rating">Rating
                        <img src="${resource(dir: 'images', file: 'star1.jpg')}"/>
                        <img src="${resource(dir: 'images', file: 'star1.jpg')}"/>
                        <img src="${resource(dir: 'images', file: 'star1.jpg')}"/>
                        <img src="${resource(dir: 'images', file: 'star1.jpg')}"/>
                        <img src="${resource(dir: 'images', file: 'star2.jpg')}"/>
                    </div>
                    <div class="report-text">
                        Prep - ${recipe?.preparationTime}. Cook - ${recipe?.cookingTime}.
                    </div>
                </div>
                <div class="month-menu-container-hdr">
                    <ul>
                        <li><img src="${resource(dir: 'images', file: 'edit-icon.jpg')}"/></li>
                        <li><strong>test</strong></li>
                        <li><img src="${resource(dir: 'images', file: 'print-icon.jpg')}"/></li>
                        <li><strong>test</strong></li>
                    </ul>
                </div>
                <div style="float: left; width: 413px;"><strong>
                    <span id="displayIngredients">

                        <g:each in="${recipe?.ingredients}">
                            ${it}<br>
                        </g:each>
                    %{--1 lb. chicken, partially frozen--}%
                    %{--<br/>--}%
                    %{--3 lg. carrots, sliced diagonally--}%
                    %{--<br/>--}%
                    %{--2 c. string beans, fresh or--}%
                    %{--<br/>--}%
                    %{--frozen--}%
                    %{--<br/>--}%
                    %{--3/4 c. chicken broth--}%
                    %{--<br/>--}%
                    %{--1 can mushrooms--}%
                    </span></strong>
                </div>
                <div style="float: right;">
                    <img height="130" width="148" src="${resource(dir: 'images', file: 'img5.jpg')}"/>
                    <div class="rating1">
                        <img src="${resource(dir: 'images', file: 'star1.jpg')}"/> Rating<br/>
                        <a href="#">text link</a></div>
                </div>
                <p class="clr"/>
                <span class="month-menu-content" id="displayDirections">
                    <g:each in="${recipe?.directions}">
                        ${it}<br>
                    </g:each>
                %{--Slice meat diagonally with grain into thin--}%
                %{--strips.--}%
                %{--Pour terikate sauce over and marinate while--}%
                %{--preparing vegetables.--}%
                %{--<p></p>--}%
                %{--<p></p>--}%
                %{--<p>--}%
                %{--Place oil in pan, stir fry beans until--}%
                %{--tender, add carrots, onions and then put meat in a little at a time.--}%
                %{--Stir meat and vegetables together. Add mushrooms and beef broth. Stir--}%
                %{--until hot. Serve over rice.</p>--}%
                </span>
                <p></p><p></p>
                <p class="serve-link">Serve With<br/>

                    <a href="#" class="serve-link">Rice</a>, <a href="#" class="serve-link">Won Tons</a>, <a href="#" class="serve-link">Egg Rolls</a>, <a href="#" class="serve-link">Egg Drop Soup</a></p>

                <div class="month-menu-list">

                    <div class="month-menu-list-container">
                        <div class="month-menu-list1">
                            <img src="${resource(dir: 'images', file: 'star1.jpg')}"/>
                            <img src="${resource(dir: 'images', file: 'star1.jpg')}"/>
                            <img src="${resource(dir: 'images', file: 'star1.jpg')}"/>
                            <img src="${resource(dir: 'images', file: 'star1.jpg')}"/>
                            <img src="${resource(dir: 'images', file: 'star2.jpg')}"/>
                        </div>
                        <div class="month-menu-list1">lorem ipsum lorem ipsum</div>
                        <div class="month-menu-list1"><a href="#">lorem ipsum</a></div>
                    </div>

                    <div class="month-menu-list-container">
                        <div class="month-menu-list1">
                            <img src="${resource(dir: 'images', file: 'star1.jpg')}"/>
                            <img src="${resource(dir: 'images', file: 'star1.jpg')}"/>
                            <img src="${resource(dir: 'images', file: 'star1.jpg')}"/>
                            <img src="${resource(dir: 'images', file: 'star1.jpg')}"/>
                            <img src="${resource(dir: 'images', file: 'star2.jpg')}"/>
                        </div>
                        <div class="month-menu-list1">lorem ipsum lorem ipsum</div>
                        <div class="month-menu-list1"><a href="#">lorem ipsum</a></div>
                    </div>

                    <div class="month-menu-list-container">
                        <div class="month-menu-list1">
                            <img src="${resource(dir: 'images', file: 'star1.jpg')}"/>
                            <img src="${resource(dir: 'images', file: 'star1.jpg')}"/>
                            <img src="${resource(dir: 'images', file: 'star1.jpg')}"/>
                            <img src="${resource(dir: 'images', file: 'star1.jpg')}"/>
                            <img src="${resource(dir: 'images', file: 'star2.jpg')}"/>
                        </div>
                        <div class="month-menu-list1">lorem ipsum lorem ipsum</div>
                        <div class="month-menu-list1"><a href="#">lorem ipsum</a></div>
                    </div>

                    <div class="month-menu-list-container">
                        <div class="month-menu-list1">
                            <img src="${resource(dir: 'images', file: 'star1.jpg')}"/>
                            <img src="${resource(dir: 'images', file: 'star1.jpg')}"/>
                            <img src="${resource(dir: 'images', file: 'star1.jpg')}"/>
                            <img src="${resource(dir: 'images', file: 'star1.jpg')}"/>
                            <img src="${resource(dir: 'images', file: 'star2.jpg')}"/>
                        </div>
                        <div class="month-menu-list1">lorem ipsum lorem ipsum</div>
                        <div class="month-menu-list1"><a href="#">lorem ipsum</a></div>
                    </div>
                </div>
            </div>
        </div>
        <div>
            <img src="${resource(dir: 'images', file: 'add-rgt-img6.jpg')}"/></div>
    </div>
</div>
</body>
</html>
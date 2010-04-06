<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import="com.mp.domain.*" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Minute Menu Plan</title>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'global-style.css')}"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'token-input-facebook.css')}"/>
    <script src="${resource(dir: 'js', file: 'jquery-1.4.2.min.js')}" type="text/javascript"></script>
    <script src="${resource(dir: 'js', file: 'jquery.taginput.js')}" type="text/javascript"></script>
</head>
<body>
%{--***************************************** SAMPLE INGREDIENT ROW *****************************************--}%
<span id="sampleIngredientRow" style="display:none;">
    <g:render template="ingredientRow"/>
</span>
%{--***************************************** SAMPLE DIRECTION0 ROW *****************************************--}%
<span id="sampleDirectionRow" style="display:none;">
    <g:render template="directionRow"/>
</span>
<g:form name="formCreateRecipe" controller="recipe" action="saveRecipe" enctype="multipart/form-data">
    <div class="header-container">
        <div class="header">
            <div class="logo-container">
                <a href="http://qa.menuplanner.intelligrape.net/">
                    <img src="${resource(dir: 'images', file: 'logo.jpg')}" alt="Minute Menu Plan" border="0">
                </a>
            </div>
            <div class="logotext-container">
                Plan Your Month’s Menu in a Minute!
            </div>
            <div class="top-nav">
                <ul>
                    <li><img src="${resource(dir: 'images', file: 'menu-img.jpg')}" align="left" vspace="10"><a href="#">Menu Plans</a></li>
                    <li><img src="${resource(dir: 'images', file: 'search-icon.jpg')}" align="left" vspace="10"><a href="#">Browse Recipes</a>
                        <span class="top-nav-img">
                            <img src="${resource(dir: 'images', file: 'top-nav-img.jpg')}">
                        </span>
                    </li>
                    <li><a href="#">My Account</a> <span class="top-nav-img">
                        <img src="${resource(dir: 'images', file: 'top-nav-img.jpg')}">
                    </span>
                    </li>
                    <li><a href="#">Forums</a></li>
                    <li><a href="#">Conversions</a></li>
                    <li><a href="#">Help</a></li>
                    <li><a href="#">Admin</a> <span class="top-nav-img">
                        <img src="${resource(dir: 'images', file: 'top-nav-img.jpg')}"/>
                    </span>
                    </li>
                </ul>
            </div>
        </div>
    </div>

    <div class="wrapper">
        <div class="body-container">

            <div class="adrecipepage-left-container">
                <div class="add-recipe-header">
                    <div class="add-recipe-header1">
                        <img src="${resource(dir: 'images', file: 'add-recipe-header-img1.jpg')}">
                    </div>
                    <div class="add-recipe-header2">Add Recipe</div>
                    <div class="add-recipe-header1">
                        <img src="${resource(dir: 'images', file: 'add-recipe-header-img2.jpg')}">
                    </div>
                    <div class="clr">
                    </div>
                </div>
                <div class="menu-nav clr">
                    <ul>
                        <li><a class="tabs active" id="tabGeneralInfo" style="cursor:pointer;">General Info</a></li>
                        <li><a class="tabs" id="tabIngredients" style="cursor:pointer;">Ingredients</a></li>
                        <li><a class="tabs" id="tabCookingSteps" style="cursor:pointer;">Cooking Steps</a></li>
                        <li><a class="tabs" id="tabServeWith" style="cursor:pointer;">Serve With</a></li>
                        <li><a class="tabs" id="tabNutritionFacts" style="cursor:pointer;">Nutrition Facts</a></li>
                    </ul>
                </div>

                %{------------------------------PANEL: GENERAL-INFO------------------------------------}%
                <div id="panelGeneralInfo" class="left-container2">
                    <div class="min-height-container">
                        <div class="add-recipe-form">

                            <ul class="add-recipe-form-container">
                                <li class="add-recipe-form-content"><strong>Name</strong></li>
                                <li class="add-recipe-form-input">
                                    <g:textField class="input1" name="name" value="${recipeCO?.name}"/>
                                </li>
                            </ul>

                            <ul class="add-recipe-form-container">
                                <li class="add-recipe-form-content"><strong>Categories</strong></li>
                                <li class="add-recipe-form-input">
                                    <mp:tagInput name="categoryIds" controller="recipe" action="getMatchingCategories"/>
                                </li>
                            </ul>

                            <ul class="add-recipe-form-container">
                                <li class="add-recipe-form-content"><strong>Prep Time</strong></li>
                                <li class="add-recipe-form-input">
                                    <g:textField class="input4" name="preparationTime" value="${recipeCO?.preparationTime}"/>
                                    <g:select class="select2" name="preparationUnitId" from="${timeUnits}" optionKey="id" value="${recipeCO?.preparationUnitId}"/>
                                </li>
                            </ul>

                            <ul class="add-recipe-form-container">
                                <li class="add-recipe-form-content"><strong>Cook Time</strong></li>
                                <li class="add-recipe-form-input">
                                    <g:textField class="input4" name="cookTime" value="${recipeCO?.cookTime}"/>
                                    <g:select class="select2" name="cookUnitId" from="${timeUnits}" optionKey="id" value="${recipeCO?.cookUnitId}"/>
                                </li>
                            </ul>

                            <ul class="add-recipe-form-container">
                                <li class="add-recipe-form-content"><strong>Difficulty</strong></li>
                                <g:radioGroup name="difficulty" values="${RecipeDifficulty.list()*.name()}" value="${recipeCO?.difficulty}" labels="${RecipeDifficulty.list()*.name}">
                                    ${it.radio} <strong><g:message code="${it.label}"/></strong>
                                </g:radioGroup>
                            </ul>

                            <ul class="add-recipe-form-container">
                                <li class="add-recipe-form-content"><strong>Makes</strong></li>
                                <li class="add-recipe-form-input">
                                    <div class="clr">
                                        <div class="add-recipe-form-input2">
                                            <g:textField class="input4" name="makesServing" value="${recipeCO?.makesServing}" style=""/>
                                        </div>
                                        <div class="add-recipe-form-input2"><strong>Servings</strong></div>
                                    </div>
                                </li>
                                <li class="add-recipe-form-input" style="margin-left:100px;">
                                    <g:checkBox name="shareWithCommunity" value="${recipeCO?.shareWithCommunity}"/>
                                    <strong>Share with Community</strong>
                                </li>
                            </ul>

                            <div class="clr"></div>
                        </div>

                        <div class="add-recipe-form2">
                            <ul class="add-recipe-form-container">
                                <li class="add-recipe-form-input2">
                                    <strong>images</strong>
                                    <input id="selectRecipeImage" size="1" name="selectRecipeImage" class="input3" type="file" onchange="changeRecipeImage(this)"/>
                                    %{--<img src="${resource(dir: 'images', file: 'browser.jpg')}" alt="Browse" border="0"/>--}%
                                    <br>
                                    <img id="removeRecipeImage" src="${resource(dir: 'images', file: 'remove.jpg')}" alt="Remove" border="0" height="28px;" style="cursor:pointer"/>
                                </li>
                            </ul>
                            <div class="clr">
                                <img id="recipeImage" src="${resource(dir: 'images', file: '')}" border="0" width="195" height="171" style="visibility:hidden;"/>
                            </div>
                        </div>

                    </div>

                    <div>
                        <img src="${resource(dir: 'images', file: 'left-container-img1.jpg')}"/>
                    </div>
                </div>

                %{------------------------------PANEL: INGREDIENTS------------------------------------}%
                <div id="panelIngredients" class="left-container2" style="display:none;">

                    <div class="min-height-container">

                        <ul class="ingredients">
                            <li>
                                <div style="background-color:#ddd; padding:5px;">
                                    <table style="border:0px;">
                                        <tr>
                                            <td style="width:50px; text-align:left;"></td>
                                            <td style="width:50px; text-align:left;"><strong>Amount</strong></td>
                                            <td style="width:50px; text-align:left;"><strong>Unit</strong></td>
                                            <td style="width:100px; text-align:left;"><strong>Ingredient</strong></td>
                                            <td></td>
                                        </tr>
                                    </table>
                                </div>
                            </li>

                            <li>

                                <span id="IngredientAdded">

                                    <g:each status="i" in="${recipeCO?.hiddenIngredients}" var="X">
                                        <g:render template="ingredientRowWithParams" model="[hiddenIngredient:recipeCO?.hiddenIngredients[i], ingredientQuantity:recipeCO.ingredientQuantities[i],ingredientUnitId:recipeCO.ingredientUnitIds[i],ingredientProductId:recipeCO.ingredientProductIds[i]]"/>
                                    </g:each>

                                    <!-- Show Ingredients Here -->

                                </span>
                            </li>

                            <li class="clr">
                                <span id="AddIngredientToolBox" style="float:left; padding-left:30px;padding-top:20px;">
                                    <img id="btnAddIngredient" src="${resource(dir: 'images', file: 'plus-add.jpg')}" hspace="4" align="left" border="0" style="cursor:pointer; margin:0px;"/>
                                    <span id="ingredientToBeAdded" style="display:block; float:left;padding-left:10px;">
                                        <g:textField class="input2" id='optionIngredientQuantities' name="optionIngredientQuantities" value=""/>
                                        <g:select class="select2" id='optionIngredientUnitIds' name="optionIngredientUnitIds" from="${metricUnits}" optionKey="id"/>
                                        <div style="padding-top:2px; float:left;">
                                            <mp:tagInput name="optionIngredientProductIds" controller="recipe" action="getMatchingProducts" multiselect="false"/>
                                        </div>
                                    </span>
                                </span>
                            </li>
                        </ul>
                    </div>
                    <div style="clear:both;">
                        <img src="${resource(dir: 'images', file: 'left-container-img1.jpg')}"/>
                    </div>
                </div>


                %{------------------------------PANEL: COOKING STEPS------------------------------------}%
                <div id="panelCookingSteps" class="left-container2" style="display:none;">

                    <div class="min-height-container">

                        <ul class="ingredients">
                            <li>
                                <div style="background-color:#ddd; padding:5px;">
                                    <table style="border:0px;">
                                        <tr>
                                            <td style="width:60px; text-align:left;"></td>
                                            <td style="width:40px; text-align:left;"><strong>Image</strong></td>
                                            <td style="width:150px; text-align:left;"><strong>Step Text</strong></td>
                                            <td></td>
                                        </tr>
                                    </table>
                                </div>
                            </li>

                            <li>
                                <span id="DirectionsAdded">

                                    <g:each in="${recipeCO?.hiddenDirections}">
                                        <g:render template="directionRowWithParams" model="[hiddenDirection:it]"/>
                                    </g:each>

                                    <!-- Show Directions Here -->
                                </span>
                            </li>

                            <li class="clr">
                                <span id="AddDirectionToolBox" style="float:left; padding-left:30px;padding-top:20px;">
                                    <img id="btnAddDirection" src="${resource(dir: 'images', file: 'plus-add.jpg')}" hspace="4" align="left" border="0" style="cursor:pointer; margin:0px;"/>
                                    <span id="directionToBeAdded" style="display:block; float:left;padding-left:10px;">
                                        <g:textField class="input1" id="optionDirections" name="optionDirections" value=""/>
                                    </span>

                                </span>
                            </li>
                        </ul>

                    </div>
                    <div style="clear:both;">
                        <img src="${resource(dir: 'images', file: 'left-container-img1.jpg')}"/>
                    </div>
                </div>


                %{------------------------------PANEL: SERVE-WITH------------------------------------}%
                <div id="panelServeWith" class="left-container2" style="display:none;">

                    <div class="min-height-container">

                        <ul class="ingredients">
                            <li>
                                <div style="background-color:#ddd; padding:5px;">
                                    <table style="border:0px;">
                                        <tr>
                                            <td style="width:50px; text-align:left;"></td>
                                            <td style="width:150px; text-align:left;"><strong>Recipe/Food Name</strong></td>
                                            <td></td>
                                        </tr>
                                    </table>
                                </div>
                            </li>

                            <li>
                                <span id="ItemsAdded">

                                    <g:each in="${recipeCO?.hiddenDirections}">
                                        <br>
                                    </g:each>

                                <!-- Show Items Here -->
                                </span>
                            </li>

                            <li class="clr">
                                <span id="AddItemToolBox" style="float:left; padding-left:30px;padding-top:20px;">
                                    <br>
                                    <img id="btnAddItem" src="${resource(dir: 'images', file: 'plus-add.jpg')}" hspace="4" align="left" border="0" style="cursor:pointer; margin:0px;"/>
                                    <span id="itemToBeAdded" style="display:block; float:left;padding-left:10px;">
                                        <g:textField class="input1" name="someName" value=""/>
                                    </span>

                                </span>
                            </li>
                        </ul>

                    </div>
                    <div style="clear:both;">
                        <img src="${resource(dir: 'images', file: 'left-container-img1.jpg')}"/>
                    </div>
                </div>


                %{------------------------------PANEL: NUTRIENTS-FACTS------------------------------------}%
                <div id="panelNutritionFacts" class="left-container2" style="display:none;">

                    <div class="min-height-container">

                        <ul class="ingredients">
                            <li>
                                <div style="background-color:#ddd; padding:5px;">
                                    <table style="border:0px;">
                                        <tr>
                                            <td style="width:50px; text-align:left;"></td>
                                            <td style="width:40px; text-align:left;"><strong>Amount</strong></td>
                                            <td style="width:150px; text-align:left;"><strong>Nutrients per Serving</strong></td>
                                            <td></td>
                                        </tr>
                                    </table>
                                </div>
                            </li>
                            <li>
                                <g:each in="${nutrients}" var="nutrient" status="i">

                                    <div class="addrecipe${(i % 2 == 0) ? '' : '2'}">
                                        <input type="hidden" value="${nutrient?.id}" name="nutrientIds"/>
                                        <input class="input2" type="text" name="nutrientQuantities" id="text1" value="${((recipeCO) ? recipeCO.nutrientQuantities[i] : '')}"/> ${nutrient?.preferredUnit?.symbol}  ${nutrient?.name}
                                    </div>

                                </g:each>
                            </li>
                        </ul>
                    </div>
                    <div style="clear:both;">
                        <img src="${resource(dir: 'images', file: 'left-container-img1.jpg')}"/>
                    </div>
                </div>

            </div>

            %{--********************************** ONLY PREVIEW ********************************--}%
            <div class="add-recipe-rgt-con">
                <div class="add-recipe-header">
                    <div class="add-recipe-header1">
                        <img src="${resource(dir: 'images', file: 'add-recipe-header-img1.jpg')}"/>
                    </div>
                    <div class="add-recipe-header3">Recipe Preview</div>
                    <div class="add-recipe-header1">
                        <img src="${resource(dir: 'images', file: 'add-recipe-header-img2.jpg')}"/>
                    </div>
                </div>
                <div class="clr"></div>
                <div class="add-recipe-rgt1">
                    <div>
                        <img src="${resource(dir: 'images', file: 'add-rgt-img1.jpg')}"/>
                    </div>
                    <div class="add-recipe-rgt2">
                        <div class="add-rgt-hdr">
                            <img src="${resource(dir: 'images', file: 'add-rgt-hdr1.jpg')}" align="left"/>
                            <div class="add-rgt-hdr1">
                                <span id="displayName">
                                    Beef Broccoil
                                </span>
                            </div>
                            <img src="${resource(dir: 'images', file: 'add-rgt-hdr3.jpg')}" align="left"/>

                            <div class="rating">Rating
                                <img src="${resource(dir: 'images', file: 'star1.jpg')}"/>
                                <img src="${resource(dir: 'images', file: 'star1.jpg')}"/>
                                <img src="${resource(dir: 'images', file: 'star1.jpg')}"/>
                                <img src="${resource(dir: 'images', file: 'star1.jpg')}"/>
                            </div>
                        </div>
                        <img src="${resource(dir: 'images', file: 'img5.jpg')}" align="right" width="148" height="130"/>
                        <p>
                            Prep - <span id="displayPrepTime">30 min</span> .
                            <br/>
                            Cook - <span id="displayCookTime">30 min</span> .
                            <br/>
                            <span id="displayIngredients">
                                1 lb. chicken, partially frozen
                                <br/>
                                3 lg. carrots, sliced diagonally
                                <br/>
                                2 c. string beans, fresh or
                                <br/>
                                frozen
                                <br/>
                                3/4 c. chicken broth
                                <br/>
                                1 can mushrooms
                            </span>
                        </p>
                        <p>&nbsp;</p><p>&nbsp;</p>
                        <p class="clr">
                        </p>
                        <span id="displayDirections">
                            Slice meat diagonally with grain into thin strips.
                            Pour terikate sauce over and marinate while preparing vegetables.
                            <p>&nbsp;</p>
                            <p>&nbsp;</p>
                            <p>
                                Place oil in pan, stir fry beans until tender, add carrots, onions and then put meat in a little at a time. Stir meat and vegetables together. Add mushrooms and beef broth. Stir until hot. Serve over rice.
                            </p>

                        </span>
                        <p>&nbsp;</p><p>&nbsp;</p>
                        <p class="serve-link">Serve With<br/>
                            <a class="serve-link" href="#">Rice</a>, <a class="serve-link" href="#">Won Tons</a>, <a class="serve-link" href="#">Egg Rolls</a>, <a class="serve-link" href="#">Egg Drop Soup</a></p>
                        <p>&nbsp;</p><p>&nbsp;</p>

                        <p>By - <span class="blue-text">Cheryl - Bakersfield, CA</span></p><p>&nbsp;</p><p>&nbsp;</p>

                        <p class="font-10">Nutritional Facts per serving: 204 cal., 7g total fat, 4 mg chol. 533 mg sodium, 33 g. carbo., 2 g fiber, 6 g. pro.</p>

                    </div>
                    <div>
                        <img src="${resource(dir: 'images', file: 'add-rgt-img3.jpg')}"/>
                    </div>
                </div>

            </div>
            %{--********************************** ONLY PREVIEW ********************************--}%

            <div class="save-recips">
                <a href="#">
                    <g:submitButton name="btnSave" class="save" value="Save Recipe" style="font-size:14px;color:white;background-color:orange;width:120px;height:35px;cursor:pointer;"/>
                    <input type="button" name="preview" id="preview" value="Preview" style="font-size:14px;color:white;background-color:orange;width:120px;height:35px;cursor:pointer;"/>
                    <input type="button" name="delete" id="delete" value="Delete" style="font-size:14px;color:white;background-color:orange;width:120px;height:35px;cursor:pointer;"/>
                    %{--<img src="${resource(dir: 'images', file: 'save-recipe1.jpg')}" alt="Save Recipe" border="0"/>--}%
                </a>
            </div>
        </div>
        <div class="clr"></div>
    </div>
    </div>

    <div class="footer-bg clr">
        <img src="${resource(dir: 'images', file: 'footer-body-img.jpg')}"/>
    </div>
</g:form>

<script type="text/javascript">
    var sampleIngredientRowHTML = jQuery('#sampleIngredientRow').html();
    var sampleDirectionRowHTML = jQuery('#sampleDirectionRow').html();
    bindEventsFor("IngredientAdded", "ingredientRow");
    bindEventsFor("DirectionsAdded", "directionRow")

    jQuery(document).ready(function() {
        jQuery('#tabGeneralInfo').click(function() {
            jQuery('.left-container2').css('display', 'none');
            jQuery('.tabs').attr('class', 'tabs');
            jQuery('#tabGeneralInfo').attr('class', 'tabs active');
            jQuery('#panelGeneralInfo').show()
        })
        jQuery('#tabIngredients').click(function() {
            jQuery('.left-container2').css('display', 'none');
            jQuery('.tabs').attr('class', 'tabs');
            jQuery('#tabIngredients').attr('class', 'tabs active');
            jQuery('#panelIngredients').show()
            colorRowAlternate()
        })
        jQuery('#tabCookingSteps').click(function() {
            jQuery('.left-container2').css('display', 'none');
            jQuery('.tabs').attr('class', 'tabs');
            jQuery('#tabCookingSteps').attr('class', 'tabs active');
            jQuery('#panelCookingSteps').show()
            colorRowAlternate()
        })
        jQuery('#tabServeWith').click(function() {
            jQuery('.left-container2').css('display', 'none');
            jQuery('.tabs').attr('class', 'tabs');
            jQuery('#tabServeWith').attr('class', 'tabs active');
            jQuery('#panelServeWith').show()
        })
        jQuery('#tabNutritionFacts').click(function() {
            jQuery('.left-container2').css('display', 'none');
            jQuery('.tabs').attr('class', 'tabs');
            jQuery('#tabNutritionFacts').attr('class', 'tabs active');
            jQuery('#panelNutritionFacts').show()
        })
        /* REMOVE IMAGE: function to be executed when removeRecipeImage is Clicked... */
        jQuery('#removeRecipeImage').click(function() {
            jQuery('#selectRecipeImage').attr('value', '')
            jQuery('#recipeImage').attr('src', '')
            jQuery('#recipeImage').css('visibility', 'hidden')
        })
        /* ADD INGREDIENT:  function to be executed when btnAddIngredient is Clicked... */
        jQuery('#btnAddIngredient').click(function() {
            var quantity = jQuery('#optionIngredientQuantities').attr('value')
            var unitId = jQuery('#optionIngredientUnitIds').attr('value')
            var productId = jQuery('#AddIngredientToolBox input[name=optionIngredientProductIds][value!=""]').attr('value')
            var ingredientText = quantity +
                                 ' &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; ' +
                                 jQuery('#optionIngredientUnitIds :selected').text() +
                                 '&nbsp; &nbsp; ' +
                                 jQuery('#AddIngredientToolBox p').html()

            if ((quantity.length > 0) && (unitId.length > 0) && (productId.length > 0)) {
                AddIngredient(quantity, unitId, productId, ingredientText)
                /* Reset Add Ingredient ToolBox.... */
                jQuery('#optionIngredientQuantities').attr('value', '')
                jQuery('#optionIngredientUnitIds').attr('value', '1')
                jQuery('#AddIngredientToolBox .token-input-delete-token-facebook').click()
            }
            bindEventsFor("IngredientAdded", "ingredientRow");
        })

        /* ADD DIRECTION:  function to be executed when btnAddDirection is Clicked... */
        jQuery('#btnAddDirection').click(function() {
            var direction = jQuery('#optionDirections').attr('value')
            if (direction.length > 0) {
                AddDirection(direction)
                /* Reset Add Direction ToolBox.... */
                jQuery('#optionDirections').attr('value', '')
            }
            bindEventsFor("DirectionsAdded", "directionRow")
        })
        jQuery('#preview').click(function() {
            reflectInPreviewPanel()
        })
    })

    function AddIngredient(quantity, unitId, productId, ingredientText) {
        var addIngredient = sampleIngredientRowHTML;
        jQuery('#IngredientAdded').append(addIngredient)
        jQuery('.ingredientRowNew .showIngredient').html(ingredientText)
        jQuery('.ingredientRowNew .Q').val(quantity);
        jQuery('.ingredientRowNew .U').val(unitId);
        jQuery('.ingredientRowNew .P').val(productId);
        jQuery('.ingredientRowNew .H').val(ingredientText);
        jQuery('.ingredientRowNew').attr('class', 'ingredientRow')
    }

    function AddDirection(direction) {
        var addDirection = sampleDirectionRowHTML;
        jQuery('#DirectionsAdded').append(addDirection)
        jQuery('.directionRowNew .showDirection').html(direction)
        jQuery('.directionRowNew .D').val(direction);
        jQuery('.directionRowNew .H').val(direction);
        jQuery('.directionRowNew').attr('class', 'directionRow')
    }

    function bindEventsFor(DOM_ID, DOM_CLASS) {
        colorRowAlternate()
        domId = "#" + DOM_ID
        domClass = "." + DOM_CLASS
        jQuery(domId + ' .btnUp').css('visibility', 'visible');
        jQuery(domId + ' .btnUp:first').css('visibility', 'hidden');
        jQuery(domId + ' .btnDown').css('visibility', 'visible');
        jQuery(domId + ' .btnDown:last').css('visibility', 'hidden');
        jQuery(domId + ' .optionImages .btnDelete').unbind();
        jQuery(domId + ' .btnDown').unbind();
        jQuery(domId + ' .btnUp').unbind();
        jQuery.each(jQuery(domId + ' .optionImages .btnDelete'), function() {
            jQuery(this).click(function() {
                jQuery(this).parents(domClass).remove();
                bindEventsFor(DOM_ID, DOM_CLASS);
            })
        })
        jQuery.each(jQuery(domId + ' .btnDown'), function() {
            jQuery(this).click(function() {
                var index = jQuery(this).index(domId + ' .btnDown')
                var a = jQuery(domId + ' ' + domClass + ':eq(' + index + ')')
                var b = jQuery(domId + ' ' + domClass + ':eq(' + (index + 1) + ')')
                var temp = a.html()
                a.html(b.html())
                b.html(temp)
                bindEventsFor(DOM_ID, DOM_CLASS);
            })
        })
        jQuery.each(jQuery(domId + ' .btnUp'), function() {
            jQuery(this).click(function() {
                var index = jQuery(this).index(domId + ' .btnUp')
                var a = jQuery(domId + ' ' + domClass + ':eq(' + index + ')')
                var b = jQuery(domId + ' ' + domClass + ':eq(' + (index - 1) + ')')
                var temp = a.html()
                a.html(b.html())
                b.html(temp)
                bindEventsFor(DOM_ID, DOM_CLASS);
            })
        })
    }
    %{--*************** A JAVA-SCRIPT FUNCTION TO DISPLAY CHANGED IMAGE OF RECIPE ****************--}%
    function changeRecipeImage(attachment) {
        previewImage = document.getElementById('recipeImage');
        if (attachment.files)   previewImage.src = attachment.files.item(0).getAsDataURL();
        else    previewImage.src = attachment.value;
        jQuery('#recipeImage').css('visibility', 'visible')
        
    }
    function colorRowAlternate() {
        jQuery('.ingredientRow:visible:odd').css('backgroundColor', '#eee')
        jQuery('.directionRow:visible:odd').css('backgroundColor', '#eee')
        jQuery('.ingredientRow:visible:even').css('backgroundColor', '#fff')
        jQuery('.directionRow:visible:even').css('backgroundColor', '#fff')
    }
    function reflectInPreviewPanel() {
        //        jQuery('#displayName').html(jQuery('#name').attr('value'))
        //        jQuery('#displayPrepTime').html(jQuery('#preparationTime').attr('value') + ' ' + jQuery('#preparationUnitId :selected').text())
        //        jQuery('#displayCookTime').html(jQuery('#cookTime').attr('value') + ' ' + jQuery('#cookUnitId :selected').text())
        //        var i;
        //        jQuery('#displayIngredients').html('')
        //        for(i=1;i<jQuery('input[name="hiddenIngredients"]').size();i++){
        //            jQuery('#displayIngredients').append(jQuery('input[name="hiddenIngredients"]:eq('+i+')').attr('value') + '<br>')
        //        }
        //        jQuery('#displayDirections').html('')
        //        for(i=1;i<jQuery('input[name="hiddenDirections"]').size();i++){
        //            jQuery('#displayDirections').append(jQuery('input[name="hiddenDirections"]:eq('+i+')').attr('value') + ', ')
        //        }
    }

</script>
</body>
</html>
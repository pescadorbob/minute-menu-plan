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

    <script type="text/javascript" src="${resource(dir: 'jquery.uploadify-v2.1.0', file: 'swfobject.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'jquery.uploadify-v2.1.0', file: 'jquery.uploadify.v2.1.0.min.js')}"></script>

</head>
<body>
%{--***************************************** SAMPLE INGREDIENT ROW *****************************************--}%
<table id="sampleIngredientRow" style="display:none;">
    <g:render template="ingredientRow"/>
</table>
%{--***************************************** SAMPLE DIRECTION0 ROW *****************************************--}%
<table id="sampleDirectionRow" style="display:none;">
    <g:render template="directionRow"/>
</table>
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
                    <li><span class="top-nav-img1"><img src="${resource(dir: 'images', file: 'menu-img.jpg')}"/></span><a href="#">Menu Plans</a></li>
                    <li><span class="top-nav-img1"><img src="${resource(dir: 'images', file: 'search-icon.jpg')}"/></span><a href="#">Browse Recipes</a>
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

                    <div class="clr"></div>
                </div>

                <div>
                    <g:hasErrors bean="${recipeCO}">
                        <div class="errors">
                            <g:renderErrors bean="${recipeCO}"/>
                        </div>
                    </g:hasErrors>
                </div>

                <div class="menu-nav clr">
                    <ul>
                        <li><a class="tabs active" id="tabGeneralInfo" style="cursor:pointer; ${mp.checkGeneralInfoTabError(bean: recipeCO, fields: ['name', 'makesServing', 'preparationTime', 'cookTime', 'difficulty', 'categoryIds'])}">General Info</a></li>
                        <li><a class="tabs" id="tabIngredients" style="cursor:pointer;${hasErrors(bean: recipeCO, field: 'ingredientQuantities', 'color:red;')}">Ingredients</a></li>
                        <li><a class="tabs" id="tabCookingSteps" style="cursor:pointer; ${hasErrors(bean: recipeCO, field: 'directions', 'color:red;')}">Cooking Steps</a></li>
                        <li><a class="tabs" id="tabServeWith" style="cursor:pointer;">Serve With</a></li>
                        <li><a class="tabs" id="tabNutritionFacts" style="cursor:pointer;${hasErrors(bean: recipeCO, field: 'nutrientQuantities', 'color:red;')}">Nutrition Facts</a></li>
                    </ul>
                </div>

                %{------------------------------PANEL: GENERAL-INFO------------------------------------}%
                <g:render template="/recipe/generalInfo" model="[recipeCO: recipeCO, timeUnits: timeUnits]"/>
                %{------------------------------PANEL: INGREDIENTS------------------------------------}%
                <div id="panelIngredients" class="left-container2" style="display:none;">

                    <div class="min-height-container">

                        <ul class="ingredients">
                            <li>
                                <div class="showIngredientsHere" style="">
                                    <table id="tableIngredients" cellspacing="0px" cellpadding="0px">
                                        <tr id="tableIngredientsHeader" class="mnuTableHeader">
                                            <td style="width:33px;"></td>
                                            <td style="width:33px;"></td>
                                            <td style="width:33px;"></td>
                                            <td style="width:50px;"><strong>Amount</strong></td>
                                            <td style="width:80px;"><strong>Unit</strong></td>
                                            <td style="width:200px;"><strong>Ingredient</strong></td>
                                        </tr>
                                        <!-- Show Ingredients Here -->
                                        <g:each status="i" in="${recipeCO?.hiddenIngredientUnitNames}" var="X">
                                            <g:render template="ingredientRowWithParams" model="[hiddenIngredientUnitNames:recipeCO?.hiddenIngredientUnitNames[i],hiddenIngredientProductNames:recipeCO.hiddenIngredientProductNames[i], ingredientQuantity:recipeCO.ingredientQuantities[i],ingredientUnitId:recipeCO.ingredientUnitIds[i],ingredientProductId:recipeCO.ingredientProductIds[i]]"/>
                                        </g:each>
                                    </table>
                                </div>
                            </li>
                            <li class="clr">
                                <span id="AddIngredientToolBox" style="float:left; margin-top:10px; padding-left:115px;padding-top:10px;padding-bottom:10px; width:470px;; border:1px solid #ddd;">
                                    <img id="btnAddIngredient" src="${resource(dir: 'images', file: 'plus-add.jpg')}" hspace="4" align="left" border="0" style="cursor:pointer; margin:0px;"/>
                                    <span id="ingredientToBeAdded" style="display:block; float:left;padding-left:10px;">
                                        <g:textField class="input2" id='optionIngredientQuantities' name="optionIngredientQuantities" value=""/>
                                        <g:select class="select2" id='optionIngredientUnitIds' name="optionIngredientUnitIds" from="${metricUnits}" optionKey="id" style="width:105px;"/>
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
                                <div class="showDirectionsHere">
                                    <table id="tableDirections" cellspacing="0px" cellpadding="0px">
                                        <tr id="tableDirectionsHeader" class="mnuTableHeader">
                                            <td style="width:33px;"></td>
                                            <td style="width:33px;"></td>
                                            <td style="width:33px;"></td>
                                            %{--<td style="width:40px; text-align:left;"><strong>Image</strong></td>--}%
                                            <td><strong>Step Text</strong></td>
                                            <g:each in="${recipeCO?.directions}">
                                                <g:render template="directionRowWithParams" model="[direction:it]"/>
                                            </g:each>
                                        </tr>
                                    </table>
                                </div>
                            </li>
                            <li class="clr">
                                <span id="AddDirectionToolBox" style="float:left; margin-top:10px; padding-left:90px;padding-top:10px;padding-bottom:10px;border:1px solid #ddd;width:485px;">
                                    <img id="btnAddDirection" src="${resource(dir: 'images', file: 'plus-add.jpg')}" hspace="4" align="left" border="0" style="cursor:pointer; margin:0px;"/>
                                    <span id="directionToBeAdded" style="display:block; float:left;padding-left:20px;">
                                        <g:textArea class="inputTextArea" id="optionDirections" name="optionDirections" value="" rows="5"/>
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
                                <div>
                                    <table class="mnuTableHeader" cellspacing="0px" cellpadding="0px">
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

                                    <!-- Show Items Here -->
                                </span>
                            </li>

                            <li class="clr">
                                <span id="AddItemToolBox" style="float:left; margin-top:10px; padding-left:30px;padding-top:10px;padding-bottom:10px; width:545px; border:1px solid #ddd;">
                                    %{--<img id="btnAddItem" src="${resource(dir: 'images', file: 'plus-add.jpg')}" hspace="4" align="left" border="0" style="cursor:pointer; margin:0px;"/>--}%
                                    <span id="itemToBeAdded" style="display:block; float:left;padding-left:42px;">
                                        <mp:tagInput name="serveWithItem" controller="recipe" action="getMatchingProducts"/>
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
                                            <td style="width:53px; text-align:left;"><strong>Amount</strong></td>
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
                                        <input class="input2" type="text" name="nutrientQuantities" id="text1" value="${((recipeCO) ? recipeCO.nutrientQuantities[i] : '')}"/>
                                        ${nutrient?.preferredUnit?.symbol}  ${nutrient?.name}
                                        %{--IMP for Preview--}%
                                        <input type="hidden" value="${nutrient?.name}" name="nutrientNames"/>
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
                                    %{--Beef Broccoil--}%
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

                        <img id="showPreviewRecipeImage" src="${resource(dir: 'images', file: '')}" align="right" width="148" height="130"/>

                        <p>
                            <br>
                            <span id="displayPrepTime">
                                %{--Prep - 30 min--}%
                            </span>
                            <br/>
                            <span id="displayCookTime">
                                %{--Cook - 30 min--}%
                            </span>
                            <br/><br/>

                            <span id="displayIngredients" style="font-weight:bold;">
                                %{--<strong>--}%
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
                                %{--</strong>--}%
                            </span>
                        </p>
                        <p>&nbsp;</p><p>&nbsp;</p>
                        <p class="clr">
                        </p>
                        <span id="displayDirections">
                            %{--Slice meat diagonally with grain into thin strips.--}%
                            %{--Pour terikate sauce over and marinate while preparing vegetables.--}%
                            %{--<p>&nbsp;</p>--}%
                            %{--<p>&nbsp;</p>--}%
                            %{--<p>--}%
                                %{--Place oil in pan, stir fry beans until tender, add carrots, onions and then put meat in a little at a time. Stir meat and vegetables together. Add mushrooms and beef broth. Stir until hot. Serve over rice.--}%
                            %{--</p>--}%

                        </span>
                        <p>&nbsp;</p><p>&nbsp;</p>
                        <p class="serve-link">
                            %{--Serve With--}%
                            <br/>
                            %{--<a class="serve-link" href="#">Rice</a>, <a class="serve-link" href="#">Won Tons</a>, <a class="serve-link" href="#">Egg Rolls</a>, <a class="serve-link" href="#">Egg Drop Soup</a></p>--}%
                        <p>&nbsp;</p><p>&nbsp;</p>

                        <p>
                            %{--By - <span class="blue-text">Cheryl - Bakersfield, CA--}%
                        </span></p><p>&nbsp;</p><p>&nbsp;</p>

                        <p class="font-10">
                            <span id="showNutrients">
                                %{--Nutritional Facts per serving:--}%
                                %{--204 cal., 7g total fat, 4 mg chol. 533 mg sodium, 33 g. carbo., 2 g fiber, 6 g. pro.--}%
                            </span>
                        </p>

                    </div>
                    <div>
                        <img src="${resource(dir: 'images', file: 'add-rgt-img3.jpg')}"/>
                    </div>
                </div>

            </div>
            %{--********************************** ONLY PREVIEW ********************************--}%

            <div class="save-recips">
                <g:submitButton name="btnSave" class="save" value="Save Recipe" style="font-size:14px;color:white;background-color:orange;width:120px;height:35px;cursor:pointer;"/>
                <input type="button" name="preview" id="preview" value="Preview" style="font-size:14px;color:white;background-color:orange;width:120px;height:35px;cursor:pointer;"/>
                <input type="button" name="delete" id="delete" value="Delete" style="font-size:14px;color:white;background-color:orange;width:120px;height:35px;cursor:pointer;"/>
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
    var sampleIngredientRowHTML = jQuery('#sampleIngredientRow>tbody').html();
    var sampleDirectionRowHTML = jQuery('#sampleDirectionRow>tbody').html();

    jQuery(document).ready(function() {
        jQuery('#tabGeneralInfo').click(function() {
            jQuery('.left-container2').css('display', 'none');
            jQuery('.tabs').removeClass('active');
            jQuery('#tabGeneralInfo').addClass('active');
            jQuery('#panelGeneralInfo').show()
        })
        jQuery('#tabIngredients').click(function() {
            jQuery('.left-container2').css('display', 'none');
            jQuery('.tabs').removeClass('active');
            jQuery('#tabIngredients').addClass('active');
            jQuery('#panelIngredients').show()
            bindEventsFor("tableIngredients", "ingredientRow");
            bindEventsFor("tableDirections", "directionRow")
        })
        jQuery('#tabCookingSteps').click(function() {
            jQuery('.left-container2').css('display', 'none');
            jQuery('.tabs').removeClass('active');
            jQuery('#tabCookingSteps').addClass('active');
            jQuery('#panelCookingSteps').show()
            bindEventsFor("tableIngredients", "ingredientRow");
            bindEventsFor("tableDirections", "directionRow")
        })
        jQuery('#tabServeWith').click(function() {
            jQuery('.left-container2').css('display', 'none');
            jQuery('.tabs').removeClass('active');
            jQuery('#tabServeWith').addClass('active');
            jQuery('#panelServeWith').show()
        })
        jQuery('#tabNutritionFacts').click(function() {
            jQuery('.left-container2').css('display', 'none');
            jQuery('.tabs').removeClass('active');
            jQuery('#tabNutritionFacts').addClass('active');
            jQuery('#panelNutritionFacts').show()
        })
        /* REMOVE IMAGE: function to be executed when removeRecipeImage is Clicked... */
        jQuery('#removeRecipeImage').click(function() {
            //            jQuery('#selectRecipeImage').attr('value', '')
            jQuery('#recipeImage').attr('src', '')
            jQuery('#recipeImage').css('visibility', 'hidden')
            jQuery('#selectRecipeImagePath').val("");
        })
        /* ADD INGREDIENT:  function to be executed when btnAddIngredient is Clicked... */
        jQuery('#btnAddIngredient').click(function() {
            var quantity = jQuery('#optionIngredientQuantities').attr('value')
            var unitId = jQuery('#optionIngredientUnitIds').attr('value')
            var productId = jQuery('#AddIngredientToolBox input[name=optionIngredientProductIds][value!=""]').attr('value')
            var unitName = jQuery('#optionIngredientUnitIds :selected').text()
            var prodName = jQuery('#AddIngredientToolBox p').html()

            if ((quantity.length > 0) && (unitId.length > 0) && (productId.length > 0) && !isNaN(quantity)) {

                AddIngredient(quantity, unitId, productId, unitName, prodName)
                /* Reset Add Ingredient ToolBox.... */
                jQuery('#optionIngredientQuantities').attr('value', '')
                jQuery('#optionIngredientUnitIds').attr('value', '1')
                jQuery('#AddIngredientToolBox .token-input-delete-token-facebook').click()
            }
            bindEventsFor("tableIngredients", "ingredientRow");
        })

        /* ADD DIRECTION:  function to be executed when btnAddDirection is Clicked... */
        jQuery('#btnAddDirection').click(function() {
            var direction = jQuery('#optionDirections').attr('value')
            if (direction.length > 0) {
                AddDirection(direction)
                /* Reset Add Direction ToolBox.... */
                jQuery('#optionDirections').attr('value', '')
            }
            bindEventsFor("tableDirections", "directionRow")
        })
        jQuery('#preview').click(function() {
            reflectInPreviewPanel()
        })

        jQuery('#selectRecipeImage').uploadify({
            'uploader': "${resource(dir:'jquery.uploadify-v2.1.0', file:'uploadify.swf')}",
            'script':    "${createLink(controller:'recipe', action:'uploadImage')}",
            'auto':true,
            'buttonImg':"${resource(dir:'jquery.uploadify-v2.1.0', file:'browser.jpg')}",
            'width':130,
            onComplete: function(event, queId, fileObj, response, data) {
                jQuery('#selectRecipeImagePath').val(response);
                jQuery('#myImageDiv').html('<img id="recipeImage" border="0" width="195" src="${createLink(action:'showImage', controller:'recipe')}?selectRecipeImagePath=' + response + '"/>')
            }

        });

    })

    function AddIngredient(quantity, unitId, productId, unitName, prodName) {
        var addIngredient = sampleIngredientRowHTML;
        jQuery('#tableIngredients tbody').append(addIngredient)
        jQuery('.ingredientRowNew .Q').val(quantity);
        jQuery('.ingredientRowNew .U').val(unitId);
        jQuery('.ingredientRowNew .P').val(productId);
        jQuery('.ingredientRowNew .UN').val(unitName);
        jQuery('.ingredientRowNew .PN').val(prodName);
        jQuery('.ingredientRowNew .quantity').html(quantity)
        jQuery('.ingredientRowNew .unit').html(unitName)
        jQuery('.ingredientRowNew .product').html(prodName)
        jQuery('.ingredientRowNew').attr('class', 'ingredientRow')
    }

    function AddDirection(direction) {
        var addDirection = sampleDirectionRowHTML;
        jQuery('#tableDirections tbody').append(addDirection)
        jQuery('.directionRowNew .D').val(direction);
        jQuery('.directionRowNew .direction').html(direction)
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
        jQuery(domId + ' .btnDelete').unbind();
        jQuery(domId + ' .btnDown').unbind();
        jQuery(domId + ' .btnUp').unbind();
        jQuery.each(jQuery(domId + ' .btnDelete'), function() {
            jQuery(this).click(function() {
                jQuery(this).parents('tr').remove();
                bindEventsFor(DOM_ID, DOM_CLASS);
            })
        })
        jQuery.each(jQuery(domId + ' .btnDown'), function() {
            jQuery(this).click(function() {
                var a = jQuery(this).parents('tr').html()
                var b = jQuery(this).parents('tr').next().html()
                var temp = a
                jQuery(this).parents('tr').next().html(temp)
                jQuery(this).parents('tr').html(b)
                bindEventsFor(DOM_ID, DOM_CLASS);
            })
        })
        jQuery.each(jQuery(domId + ' .btnUp'), function() {
            jQuery(this).click(function() {
                var a = jQuery(this).parents('tr').html()
                var b = jQuery(this).parents('tr').prev().html()
                var temp = a
                jQuery(this).parents('tr').prev().html(temp)
                jQuery(this).parents('tr').html(b)
                bindEventsFor(DOM_ID, DOM_CLASS);
            })
        })
    }
    function colorRowAlternate() {
        jQuery('#tableIngredients .ingredientRow:odd').css('backgroundColor', '#eee')
        jQuery('#tableDirections .directionRow:odd').css('backgroundColor', '#eee')
        jQuery('#tableIngredients .ingredientRow:even').css('backgroundColor', '#fff')
        jQuery('#tableDirections .directionRow:even').css('backgroundColor', '#fff')
    }
    function reflectInPreviewPanel() {
        jQuery('#displayName').html(jQuery('#name').attr('value'))
        var showPrep = ''
        if(jQuery('#preparationTime').attr('value').length){
            showPrep = 'prep - ' + jQuery('#preparationTime').attr('value') + ' ' + jQuery('#preparationUnitId :selected').text() + '.'
        }
        jQuery('#displayPrepTime').html(showPrep)
        var showCook = ''
        if(jQuery('#cookTime').attr('value').length){
            showCook = 'cook - ' + jQuery('#cookTime').attr('value') + ' ' + jQuery('#cookUnitId :selected').text() + '.'
        }
        jQuery('#displayCookTime').html(showCook)
        var i;
        jQuery('#displayIngredients').html('')
        for (i = 1; i < jQuery('input[name="ingredientQuantities"]').size(); i++) {
            var myIngredients = jQuery('input[name="ingredientQuantities"]:eq(' + i + ')').attr('value') +
                                ' ' + jQuery('input[name="hiddenIngredientUnitNames"]:eq(' + i + ')').attr('value') +
                                ' ' + jQuery('input[name="hiddenIngredientProductNames"]:eq(' + i + ')').attr('value') + '<br>'
            jQuery('#displayIngredients').append(myIngredients)
        }
        jQuery('#displayDirections').html('')
        for (i = 1; i < jQuery('input[name="directions"]').size(); i++) {
            jQuery('#displayDirections').append(jQuery('input[name="directions"]:eq(' + i + ')').attr('value') + '. ')
        }
        jQuery('#showPreviewRecipeImage').attr('src', jQuery('#recipeImage').attr('src'))
        var myNutrients = ''
        for (i = 0; i < jQuery('input[name="nutrientIds"]').size(); i++) {
            if (jQuery('input[name="nutrientQuantities"]:eq(' + i + ')').attr('value') != '') {

                myNutrients += jQuery('input[name="nutrientQuantities"]:eq(' + i + ')').attr('value') +
                               ' ' + jQuery('input[name="nutrientNames"]:eq(' + i + ')').attr('value') +
                               '. '
            }
        }
        if(myNutrients.length>=1){
            myNutrients ='Nutritional Facts per serving: ' + myNutrients
        }
        jQuery('#showNutrients').html(myNutrients)
    }

</script>
</body>
</html>
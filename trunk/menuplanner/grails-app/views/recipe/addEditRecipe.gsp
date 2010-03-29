<%@ page import="com.mp.domain.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
%{--***************************************** SAMPLE DIRECTION0 ROW *****************************************--}%
<span id="sampleNutrientsRow" style="display:none;">
    <g:render template="nutrientRow"/>
</span>
%{--**********************************************************************************************************--}%

<div class="header-container">
    <div class="header">
        <div class="logo-container">
            <a href="${createLink(uri: '/')}"><img id="imgLogo" src="${resource(dir: 'images', file: 'logo.jpg')}" alt="Minute Menu Plan" border="0"/></a>
        </div>
        <div class="logotext-container">Plan Your Month’s Menu in a Minute!</div>
        <div class="top-nav">
            <ul>
                <li><img src="${resource(dir: 'images', file: 'menu-img.jpg')}" vspace="10" align="left"/><a href="#">Menu Plans</a></li>
                <li><img src="${resource(dir: 'images', file: 'search-icon.jpg')}" vspace="10" align="left"/><a href="#">Browse Recipes</a>
                    <span class="top-nav-img"><img src="${resource(dir: 'images', file: 'top-nav-img.jpg')}"/></span></li>
                <li><a href="#">My Account</a> <span class="top-nav-img"><img src="${resource(dir: 'images', file: 'top-nav-img.jpg')}"/></span></li>
                <li><a href="#">Forums</a></li>
                <li><a href="#">Conversions</a></li>
                <li><a href="#">Help</a></li>
                <li><a href="#">Admin</a> <span class="top-nav-img"><img src="${resource(dir: 'images', file: 'top-nav-img.jpg')}"/></span></li>
            </ul>
        </div>
    </div>
</div>

<div class="wrapper">
    <div class="body-container">
        <div class="left-container" style="">
            <g:form name="formCreateRecipe" controller="recipe" action="saveRecipe" enctype="multipart/form-data">
                <h1>Add Recipe</h1>
                <!-- left-sub-container1 -->
                <div class="left-sub-container1" style="">
                    <ul class="add-recipe-form-container">
                        <li class="add-recipe-form-content"><strong>Name :</strong></li>
                        <li class="add-recipe-form-input">
                            <g:textField class="input1" name="name" value="${recipeCO?.name}"/>
                        </li>
                    </ul>
                    <ul class="add-recipe-form-container">
                        <li class="add-recipe-form-content"><strong>Category :</strong></li>
                        <li class="add-recipe-form-input">
                            %{--*************************************************************************************************************************************--}%
                            <mp:tagInput name="categoryIds" controller="recipe" action="getMatchingCategories"/>
                            %{--*************************************************************************************************************************************--}%
                        </li>
                    </ul>
                    <ul class="add-recipe-form-container">
                        <li class="add-recipe-form-content"><strong>Prep Time:</strong></li>
                        <li class="add-recipe-form-input">
                            <g:textField class="input2" name="preparationTime" value="${recipeCO?.preparationTime}"/>
                            <g:select class="select2" name="preparationUnitId" from="${TimeUnit.list()}" optionKey="id" value="${recipeCO?.preparationUnitId}"/>
                        </li>
                    </ul>
                    <ul class="add-recipe-form-container">
                        <li class="add-recipe-form-content"><strong>Cook Time :</strong></li>
                        <li class="add-recipe-form-input">
                            <g:textField class="input2" name="cookTime" value="${recipeCO?.cookTime}"/>
                            <g:select class="select2" name="cookUnitId" from="${TimeUnit.list()}" optionKey="id" value="${recipeCO?.cookUnitId}"/>
                        </li>
                    </ul>
                    <ul class="add-recipe-form-container">
                        <li class="add-recipe-form-content"><strong>Difficulty :</strong></li>
                        <g:radioGroup name="difficulty" values="${RecipeDifficulty.list()*.name()}" value="${recipeCO?.difficulty}" labels="${RecipeDifficulty.list()*.name}">
                            ${it.radio} <g:message code="${it.label}"/>
                        </g:radioGroup>
                    </ul>

                    <ul class="add-recipe-form-container">
                        <li class="add-recipe-form-content"><strong>Makes :</strong></li>
                        <li class="add-recipe-form-input">
                            <div class="clr">
                                <div class="add-recipe-form-input2">
                                    <g:textField class="input2" name="makesServing" value="${recipeCO?.makesServing}" style=""/>
                                </div>
                                <div class="add-recipe-form-input2">
                                    Servings
                                </div>
                            </div>
                            <div class="clr">
                                <div class="add-recipe-form-input2">
                                    <g:checkBox name="shareWithCommunity" value="${recipeCO?.shareWithCommunity}"/>
                                </div>
                                <div class="add-recipe-form-input2">
                                    Share with Community
                                </div>
                            </div>
                        </li>
                    </ul>
                    <div class="clr"></div>
                    <div>
                        <h2>Ingredients</h2>
                        <ul class="ingredients">
                            <span id="IngredientAdded">

                                <g:each status="i" in="${recipeCO?.hiddenIngredients}" var="X">
                                    <g:render template="ingredientRowWithParams" model="[hiddenIngredient:recipeCO?.hiddenIngredients[i], ingredientQuantity:recipeCO.ingredientQuantities[i],ingredientUnitId:recipeCO.ingredientUnitIds[i],ingredientProductId:recipeCO.ingredientProductIds[i]]"/>
                                </g:each>

                                <!-- Show Ingredients Here -->

                            </span>

                            <span id="AddIngredientToolBox">
                                <img id="btnAddIngredient" src="${resource(dir: 'images', file: 'add.jpg')}" hspace="4" align="left" vspace="4" border="0" style="cursor:pointer;"/>
                                <span id="ingredientToBeAdded">

                                    <g:textField class="input2" id='optionIngredientQuantities' name="optionIngredientQuantities" value=""/>
                                    <g:select class="select2" id='optionIngredientUnitIds' name="optionIngredientUnitIds" from="${Unit.list()}" optionKey="id" style="width:60px;"/>
                                    %{--*************************************************************************************************************************************--}%
                                    <mp:tagInput name="optionIngredientProductIds" controller="recipe" action="getMatchingProducts" multiselect="false"/>
                                    %{--*************************************************************************************************************************************--}%

                                </span>
                            </span>
                        </ul>
                        <div class="clr"></div>
                    </div>
                    <div><br/>
                        <h2>Cooking Steps:</h2>
                        <ul class="ingredients">
                            <span id="DirectionsAdded">

                                <g:each in="${recipeCO?.hiddenDirections}">
                                    <g:render template="directionRowWithParams" model="[hiddenDirection:it]"/>
                                </g:each>

                                <!-- Show Directions Here -->
                            </span>

                            <span id="AddDirectionToolBox">

                                <img id="btnAddDirection" src="${resource(dir: 'images', file: 'add.jpg')}" hspace="4" align="left" vspace="4" border="0" style="cursor:pointer;"/>
                                <span id="directionToBeAdded">
                                    <g:textField class="input1" name="optionDirections" value=""/>
                                </span>

                            </span>
                        </ul>
                        <div class="clr"></div>
                    </div>
                    %{--<ul class="add-recipe-form-container">--}%
                    %{--<li class="add-recipe-form-input2">images :</li>--}%
                    %{--<li class="add-recipe-form-input2"><img src="${resource(dir: 'images', file: 'arrow-left.jpg')}" hspace="4" vspace="4" border="0"/></li>--}%
                    %{--<li class="add-recipe-form-input2"><img vspace="4" src="${resource(dir: 'images', file: 'arrow-right.jpg')}" hspace="4" border="0"/></li>--}%
                    %{--<li class="add-recipe-form-input2"><input name="" class="input3" type="file"/></li>--}%
                    %{--<li class="clr"></li>--}%
                    %{--</ul>--}%
                    %{--<div>--}%
                    %{--<h1 class="sub-h1">Serve With:</h1>--}%
                    %{--<ul class="ingredients">--}%
                    %{--<g:each in="${(1..3)}">--}%
                    %{--<li>--}%
                    %{--<img src="${resource(dir: 'images', file: 'delete.jpg')}" width="16" height="16" align="left" hspace="2" vspace="2" border="0"/>--}%
                    %{--<img src="${resource(dir: 'images', file: 'arrow-up.jpg')}" width="16" height="16" align="left" hspace="2" vspace="2" border="0"/>--}%
                    %{--<img src="${resource(dir: 'images', file: 'arrow-dwn.jpg')}" width="16" height="16" vspace="2" hspace="2" align="left" border="0"/>--}%
                    %{--lorem ipsum lorem--}%
                    %{--</li>--}%
                    %{--</g:each>--}%
                    %{--<li><img src="${resource(dir: 'images', file: 'add.jpg')}" hspace="4" align="left" vspace="4" border="0"/>--}%
                    %{--<input class="input2" type="text" name="textfield" id="textfield"/>--}%
                    %{--<select class="select2" name="">--}%
                    %{--<option>test</option>--}%
                    %{--<option>test</option>--}%
                    %{--<option>test</option>--}%
                    %{--</select>--}%
                    %{--<input class="input3" type="text" name="textfield" id="textfield"/>--}%
                    %{--</li>--}%
                    %{--</ul>--}%
                    %{--<div class="clr"></div>--}%
                    %{--</div>--}%
                    <div align="center">
                        <br> <br>
                        <span class="button" style="">
                            <g:submitButton name="btnSave" class="save" value="Save Recipe" style="font-size:14px;color:white;background-color:orange;width:120px;height:35px;cursor:pointer;"/>
                        </span>
                        %{--<span class="button"><g:submitButton name="btnDelete" class="delete" value="Delete Recipe" style="font-size:14px;color:white;background-color:orange;width:120px;height:35px;cursor:pointer;"/></span>--}%
                        %{--<img src="${resource(dir: 'images', file: 'delete-recipe.gif')}" hspace="4" vspace="10" border="0"/>--}%
                    </div>
                </div>
                <!-- end -->
                <!-- left-sub-container2 -->
                <div class="left-sub-container2" style="">
                    <ul class="add-recipe-form-container">
                        <li class="add-recipe-form-input2">
                            image :
                        </li>
                        <li class="add-recipe-form-input2">
                            <input id="selectRecipeImage" name="selectRecipeImage" class="input3" type="file" onchange="changeRecipeImage(this)"/>
                        </li>
                        <li class="clr"></li>
                    </ul>
                    <div class="clr">
                        <div align="right">
                            <img id="removeRecipeImage" src="${resource(dir: 'images', file: 'delete-icon.jpg')}" hspace="4" border="0" style="cursor:pointer"/><br/>
                        </div>
                        <div align="center">
                            <img id="recipeImage" src="${resource(dir: 'images', file: 'img2.jpg')}" border="0"/>
                        </div>
                    </div>
                    <div><br/>
                        <h2>Nutrition Facts pre serving:</h2>

                        <span id="NutrientsAdded">

                            <g:each status="i" in="${recipeCO?.hiddenNutrients}" var="X">
                                <g:render template="nutrientRowWithParams" model="[hiddenNutrients:recipeCO?.hiddenNutrients[i], nutrientQuantity:recipeCO.nutrientQuantities[i],nutrientId:recipeCO.nutrientIds[i]]"/>
                            </g:each>

                            <!-- Show Nutrients Here -->

                        </span>

                        <span id="AddNutrientToolBox">
                            <img id="btnAddNutrient" src="${resource(dir: 'images', file: 'add.jpg')}" hspace="4" align="left" vspace="4" border="0" style="cursor:pointer;"/>
                            <span id="nutrientToBeAdded">

                                <g:textField class="input2" id='optionNutrientQuantities' name="optionNutrientQuantities" value=""/>
                                <g:select class="select2" id='optionNutrientIds' name="optionNutrientIds" from="${Nutrient.list()}" optionKey="id"/>

                            </span>
                        </span>


                        %{--<div class="clr add-recipe-form-input4">--}%
                            %{--<input class="input2" type="text" name="textfield" id="textfield"/> Lorem ipsum Lorem ipsum--}%
                        %{--</div>--}%
                        %{--<div class="clr add-recipe-form-input5">--}%
                            %{--<input class="input2" type="text" name="textfield" id="textfield"/> Lorem ipsum Lorem ipsum--}%
                        %{--</div>--}%
                        %{--<div class="clr add-recipe-form-input4">--}%
                            %{--<input class="input2" type="text" name="textfield" id="textfield"/> Lorem ipsum Lorem ipsum--}%
                        %{--</div>--}%
                        %{--<div class="clr add-recipe-form-input5">--}%
                            %{--<input class="input2" type="text" name="textfield" id="textfield"/> Lorem ipsum Lorem ipsum--}%
                        %{--</div>--}%
                        %{--<div class="clr add-recipe-form-input4">--}%
                            %{--<input class="input2" type="text" name="textfield" id="textfield"/> Lorem ipsum Lorem ipsum--}%
                        %{--</div>--}%


                    </div>
                </div>
            </g:form>
        </div><!-- end  left-Container!!!-->
    %{--<div class="right-container" style="">--}%
    %{--<h1 class="sub-h1">Preview</h1>--}%
    %{--<div class="preview-container">--}%
    %{--<div class="preview-container-header">lorem ipsum--}%
    %{--</div>--}%
    %{--<div>--}%
    %{--<img src="${resource(dir: 'images', file: 'img3.jpg')}" width="192" align="right" border="0"/>--}%
    %{--lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum--}%
    %{--lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum--}%
    %{--lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum--}%
    %{--<div class="clr">--}%
    %{--</div>--}%
    %{--</div>--}%
    %{--<div>l<strong>orem ipsum lorem ipsum</strong> lorem ipsum lorem ipsum lorem ipsum--}%
    %{--lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum--}%
    %{--lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum<br/>--}%
    %{--<a href="#">lorem ipsum lorem ipsum lorem</a>  <a href="#">lorem ipsum lorem ipsum lorem</a><br/><br/>--}%
    %{--</div>--}%
    %{--</div>--}%
    %{--</div>--}%
        <div class="clr">
        </div>
    </div>
</div>
<div class="footer-bg"><img src="${resource(dir: 'images', file: 'footer-body-img.jpg')}"/></div>


<script type="text/javascript">
    var sampleIngredientRowHTML = jQuery('#sampleIngredientRow').html();
    var sampleDirectionRowHTML = jQuery('#sampleDirectionRow').html();
    var sampleNutrientRowHTML = jQuery('#sampleNutrientsRow').html();

    function bindEventsForIngredient() {
        jQuery('#IngredientAdded .btnUp').css('visibility', 'visible');
        jQuery('#IngredientAdded .btnUp:first').css('visibility', 'hidden');
        jQuery('#IngredientAdded .btnDown').css('visibility', 'visible');
        jQuery('#IngredientAdded .btnDown:last').css('visibility', 'hidden');

        jQuery("#IngredientAdded .optionImages .btnDelete").unbind();
        jQuery("#IngredientAdded .btnDown").unbind();
        jQuery("#IngredientAdded .btnUp").unbind();

        jQuery.each(jQuery("#IngredientAdded .optionImages .btnDelete"), function() {
            jQuery(this).click(function() {
                jQuery(this).parents('.ingredientRow').remove();
                bindEventsForIngredient();
            })
        })

        jQuery.each(jQuery('#IngredientAdded .btnDown'), function() {
            jQuery(this).click(function() {
                var index = jQuery(this).index('#IngredientAdded .btnDown')
                var a = jQuery('#IngredientAdded .ingredientRow:eq(' + index + ')')
                var b = jQuery('#IngredientAdded .ingredientRow:eq(' + (index + 1) + ')')
                var temp = a.html()
                a.html(b.html())
                b.html(temp)
                bindEventsForIngredient();
            })
        })

        jQuery.each(jQuery('#IngredientAdded .btnUp'), function() {
            jQuery(this).click(function() {
                var index = jQuery(this).index('#IngredientAdded .btnUp')
                var a = jQuery('#IngredientAdded .ingredientRow:eq(' + index + ')')
                var b = jQuery('#IngredientAdded .ingredientRow:eq(' + (index - 1) + ')')
                var temp = a.html()
                a.html(b.html())
                b.html(temp)
                bindEventsForIngredient();
            })
        })
    }
    function bindEventsForDirection() {
        jQuery('#DirectionsAdded .btnUp').css('visibility', 'visible');
        jQuery('#DirectionsAdded .btnUp:first').css('visibility', 'hidden');
        jQuery('#DirectionsAdded .btnDown').css('visibility', 'visible');
        jQuery('#DirectionsAdded .btnDown:last').css('visibility', 'hidden');

        jQuery("#DirectionsAdded .optionImages .btnDelete").unbind();
        jQuery("#DirectionsAdded .btnDown").unbind();
        jQuery("#DirectionsAdded .btnUp").unbind();

        jQuery.each(jQuery("#DirectionsAdded .optionImages .btnDelete"), function() {
            jQuery(this).click(function() {
                jQuery(this).parents('.directionRow').remove();
                bindEventsForDirection();
            })
        })

        jQuery.each(jQuery('#DirectionsAdded .btnDown'), function() {
            jQuery(this).click(function() {
                var index = jQuery(this).index('#DirectionsAdded .btnDown')
                var a = jQuery('#DirectionsAdded .directionRow:eq(' + index + ')')
                var b = jQuery('#DirectionsAdded .directionRow:eq(' + (index + 1) + ')')
                var temp = a.html()
                a.html(b.html())
                b.html(temp)
                bindEventsForDirection()
            })
        })

        jQuery.each(jQuery('#DirectionsAdded .btnUp'), function() {
            jQuery(this).click(function() {
                var index = jQuery(this).index('#DirectionsAdded .btnUp')
                var a = jQuery('#DirectionsAdded .directionRow:eq(' + index + ')')
                var b = jQuery('#DirectionsAdded .directionRow:eq(' + (index - 1) + ')')
                var temp = a.html()
                a.html(b.html())
                b.html(temp)
                bindEventsForDirection()
            })
        })
    }

    jQuery(document).ready(function() {


        /* ADD INGREDIENT:  function to be executed when btnAddIngredient is Clicked... */

        jQuery('#btnAddIngredient').click(function() {
            var quantity = jQuery('#optionIngredientQuantities').attr('value')
            var unitId = jQuery('#optionIngredientUnitIds').attr('value')
            var productId = jQuery('#AddIngredientToolBox input[name=optionIngredientProductIds][value!=""]').attr('value')
            var ingredientText = quantity + ' ' + jQuery('#optionIngredientUnitIds :selected').text() + ' ' + jQuery('#AddIngredientToolBox p').html()

            if ((quantity.length > 0) && (unitId.length > 0) && (productId.length > 0)) {
                AddIngredient(quantity, unitId, productId, ingredientText)

                /* Reset Add Ingredient ToolBox.... */
                jQuery('#optionIngredientQuantities').attr('value', '')
                jQuery('#optionIngredientUnitIds').attr('value', '1')
                jQuery('#AddIngredientToolBox .token-input-delete-token-facebook').click()
            }

            bindEventsForIngredient();
        })

        /* ADD DIRECTION:  function to be executed when btnAddDirection is Clicked... */

        jQuery('#btnAddDirection').click(function() {
            var direction = jQuery('#optionDirections').attr('value')
            if (direction.length > 0) {
                AddDirection(direction)
                /* Reset Add Direction ToolBox.... */
                jQuery('#optionDirections').attr('value', '')
            }
            bindEventsForDirection()
        })

        jQuery('#btnAddNutrient').click(function(){
            var quantity = jQuery('#optionNutrientQuantities').attr('value')
            var nutrientId = jQuery('#optionNutrientIds').attr('value')
            var nutrientText = quantity + ' ' + jQuery('#optionNutrientIds :selected').text()

            if ((quantity.length > 0) && (nutrientId.length > 0)) {
                AddNutrient(quantity, nutrientId, nutrientText)

                /* Reset Add Nutrient ToolBox.... */
                jQuery('#optionNutrientQuantities').attr('value', '')
                jQuery('#optionNutrientIds').attr('value', '1')
            }

//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$   bindEventsForNutrients(); $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
        })

        jQuery('#removeRecipeImage').click(function(){
            jQuery('#selectRecipeImage').attr('value','')
            jQuery('#recipeImage').attr('src','')
        })

        bindEventsForIngredient();
        bindEventsForDirection();
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

    function AddNutrient(quantity, nutrientId, nutrientText){
        var addNutrient = sampleNutrientRowHTML
        jQuery('#NutrientsAdded').append(addNutrient)
        jQuery('.nutrientsRowNew .showNutrient').html(nutrientText)
        jQuery('.nutrientsRowNew .Q').val(quantity);
        jQuery('.nutrientsRowNew .T').val(nutrientId);
        jQuery('.nutrientsRowNew .H').val(nutrientText);
        jQuery('.nutrientsRowNew').attr('class', 'nutrientsRow')
    }

    %{--*************** A JAVA-SCRIPT FUNCTION TO DISPLAY CHANGED IMAGE OF RECIPE ****************--}%
    function changeRecipeImage(attachment) {
        previewImage = document.getElementById('recipeImage');
        if (attachment.files)   previewImage.src = attachment.files.item(0).getAsDataURL();
        else    previewImage.src = attachment.value;
    }
</script>
</body>
</html>

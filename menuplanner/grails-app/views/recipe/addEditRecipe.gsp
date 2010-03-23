<%@ page import="com.mp.domain.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Minute Menu Plan</title>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'global-style.css')}"/>
    <script src="${resource(dir: 'js', file: 'jquery-1.4.2.min.js')}" type="text/javascript"></script>
    <ui:resources/>
</head>

<body>
<div class="header-container">
    <div class="header">
        <div class="logo-container">
            <a href="#"><img id="imgLogo" src="${resource(dir: 'images', file: 'logo.jpg')}" alt="Minute Menu Plan" border="0"/></a>
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
    <div><img src="${resource(dir: 'images', file: 'wrapper-header-img.jpg')}"/></div>
    <div class="body-container">
        <div class="left-container" style="">
            <g:form name="formCreateRecipe" controller="recipe" action="saveRecipe">
                <h1 class="sub-h1">Add Recipe</h1>
                <!-- left-sub-container1 -->
                <div class="left-sub-container1" style="">
                    <ul class="add-recipe-form-container">
                        <li class="add-recipe-form-content"><strong>Name :</strong></li>
                        <li class="add-recipe-form-input">
                            <g:textField class="input1" name="name" value=""/>
                        </li>
                    </ul>
                    <ul class="add-recipe-form-container">
                        <li class="add-recipe-form-content"><strong>Category :</strong></li>
                        <li class="add-recipe-form-input">
                            <ui:multiSelect
                                    name="categoryIds"
                                    multiple="true"
                                    from="${Category.list()}"
                                    value=""
                                    isLeftAligned="true"
                                    optionKey="id"
                                    noSelection="['':'(Select One)']"/>
                        </li>
                    </ul>
                    <ul class="add-recipe-form-container">
                        <li class="add-recipe-form-content"><strong>Prep Time:</strong></li>
                        <li class="add-recipe-form-input">
                            <g:textField class="input2" name="preparationTime" value=""/>
                            <g:select class="select2" name="prepUnit" from="${['min','hrs']}"/>
                        </li>
                    </ul>
                    <ul class="add-recipe-form-container">
                        <li class="add-recipe-form-content"><strong>Cook Time :</strong></li>
                        <li class="add-recipe-form-input">
                            <g:textField class="input2" name="cookTime" value=""/>
                            <g:select class="select2" name="cookUnit" from="${['min','hrs']}"/>
                        </li>
                    </ul>
                    <ul class="add-recipe-form-container">
                        <li class="add-recipe-form-content"><strong>Difficulty :</strong></li>
                        <g:radioGroup name="difficulty" values="${RecipeDifficulty.list()*.name()}"
                                value="1" labels="${RecipeDifficulty.list()*.name}">
                            ${it.radio} <g:message code="${it.label}"/>
                        </g:radioGroup>
                    </ul>

                    <ul class="add-recipe-form-container">
                        <li class="add-recipe-form-content"><strong>Makes :</strong></li>
                        <li class="add-recipe-form-input">
                            <div class="clr">
                                <div class="add-recipe-form-input2">
                                    <g:textField class="input2" name="makesServing" value="" style=""/>
                                </div>
                                <div class="add-recipe-form-input2">
                                    Servings
                                </div>
                            </div>
                            <div class="clr">
                                <div class="add-recipe-form-input2">
                                    <g:checkBox name="shareWithCommunity" value="${false}"/>
                                </div>
                                <div class="add-recipe-form-input2">
                                    Share with Community
                                </div>
                            </div>
                        </li>
                    </ul>
                    <div class="clr"></div>
                    <div>
                        <h1 class="sub-h1">ingredients</h1>
                        --------------------------------------------------------------------------------------
                        <ul class="ingredients">
                            <span id="IngredientAdded">

                                <!-- Show Ingredients Here -->
                            </span>

                            <span id="AddIngredientToolBox">
                                <li>
                                    <img id="btnAddIngredient" src="${resource(dir: 'images', file: 'add.jpg')}" hspace="4" align="left" vspace="4" border="0" style="cursor:pointer;"/>
                                    <span id="ingredientToBeAdded">
                                        <g:textField class="input2" id='optionIngredientQuantities' name="optionIngredientQuantities" value=""/>
                                        <g:select class="select2" id='optionIngredientUnitIds' name="optionIngredientUnitIds" from="${Unit.list()}" optionKey="id" style="width:60px;" param="Unit"/>
                                        <g:select class="select2" id='optionIngredientProductIds' name="optionIngredientProductIds" from="${Product.list()}" optionKey="id" style="width:100px;"/>
                                    </span>
                                </li>
                            </span>
                        </ul>
                        <div class="clr"></div>
                        --------------------------------------------------------------------------------------
                    </div>
                    <div><br/>
                        <h1 class="sub-h1">cooking steps:</h1>
                        <ul class="ingredients">
                            <span id="DirectionsAdded">

                                <!-- Show Directions Here -->
                            </span>
                            <span id="AddDirectionToolBox">
                                <li>
                                    <img id="btnAddDirection" src="${resource(dir: 'images', file: 'add.jpg')}" hspace="4" align="left" vspace="4" border="0" style="cursor:pointer;"/>
                                    <span id="directionToBeAdded">
                                        <g:textField class="input1" name="optionDirections" value=""/>
                                    </span>
                                </li>
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
                        <span class="button" style=""><g:submitButton name="btnSave" class="save" value="Save Recipe" style="font-size:14px;color:white;background-color:orange;width:120px;height:35px;cursor:pointer;"/></span>
                        %{--<span class="button"><g:submitButton name="btnDelete" class="delete" value="Delete Recipe" style="font-size:14px;color:white;background-color:orange;width:120px;height:35px;cursor:pointer;"/></span>--}%
                        %{--<img src="${resource(dir: 'images', file: 'save-recipe.gif')}" hspace="4" vspace="10" border="0"/>--}%
                        %{--<img src="${resource(dir: 'images', file: 'delete-recipe.gif')}" hspace="4" vspace="10" border="0"/>--}%
                    </div>
                </div>
                <!-- end -->
                <!-- left-sub-container2 -->
            %{--<div class="left-sub-container2" style="">--}%
            %{--<ul class="add-recipe-form-container">--}%
            %{--<li class="add-recipe-form-input2">images :</li>--}%
            %{--<li class="add-recipe-form-input2"><input name="" class="input3" type="file"/></li>--}%
            %{--<li class="clr"></li>--}%
            %{--</ul>--}%
            %{--<div class="clr">--}%
            %{--<div align="right">--}%
            %{--<img src="${resource(dir: 'images', file: 'delete-icon.jpg')}" hspace="4" border="0"/><br/>--}%
            %{--</div>--}%
            %{--<div align="center">--}%
            %{--<img src="${resource(dir: 'images', file: 'img2.jpg')}" hspace="4" border="0"/>--}%
            %{--</div>--}%
            %{--</div>--}%
            %{--<div><br/>--}%
            %{--<h1 class="sub-h1">lorem ipsum lorem ipsum lorem</h1>--}%
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
            %{--</div>--}%
            %{--</div>--}%
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

    jQuery(document).ready(function() {

    var quantity = 0;
    var unitId= 0;
    var productId = 0;
    var direction = ""

    var options=
            '<img src="${resource(dir: 'images', file: 'delete.jpg')}" width="16" height="16" align="left" hspace="2" vspace="2" border="0"/>'
            + '<img src="${resource(dir: 'images', file: 'arrow-up.jpg')}" width="16" height="16" align="left" hspace="2" vspace="2" border="0"/>'
            + '<img src="${resource(dir: 'images', file: 'arrow-dwn.jpg')}" width="16" height="16" vspace="2" hspace="2" align="left" border="0"/>'

        jQuery('#btnAddIngredient').click(function() {
            quantity=jQuery('#optionIngredientQuantities').attr('value')
            unitId=jQuery('#optionIngredientUnitIds').attr('value')
            productId=jQuery('#optionIngredientProductIds').attr('value')


            var hiddenIngredient =
                    '<span><input type="hidden" name="ingredientQuantities" value="' + quantity
                    + '" /><input type="hidden" name="ingredientUnitIds" value="' + unitId
                    + '" /><input type="hidden" name="ingredientProductIds" value="' + productId
                    + '" /></span>';

            var showFields = quantity + ' ' + jQuery('#optionIngredientUnitIds :selected').text() + ' '
                    + jQuery('#optionIngredientProductIds :selected').text()

            var addIngredient= options + hiddenIngredient + showFields
            jQuery('#IngredientAdded').append(addIngredient + '<br><br>')
            jQuery('#optionIngredientQuantities').attr('value','')
            jQuery('#optionIngredientUnitIds').attr('value','1')
            jQuery('#optionIngredientProductIds').attr('value','1')
        })
        jQuery('#btnAddDirection').click(function(){
            direction=jQuery('#optionDirections').attr('value')
            var hiddenDirection =
                    '<span><input type="hidden" name="directions" value="' + direction
                    + '" /></span>';
            var showDirection=direction
            var addDirection= options + hiddenDirection + showDirection
            jQuery('#DirectionsAdded').append(addDirection + '<br><br>')
            jQuery('#optionDirections').attr('value','')
        })
    })
</script>

</body>
</html>

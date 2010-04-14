<%@ page import="com.mp.domain.*" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Minute Menu Plan</title>
    <meta name="layout" content="menuPlanner"/>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'createRecipe.js')}"></script>
</head>
<body>
<table id="sampleIngredientRow" style="display:none;">
    <g:render template="ingredientRow"/>
</table>
<table id="sampleDirectionRow" style="display:none;">
    <g:render template="directionRow"/>
</table>
<g:form name="formCreateRecipe" controller="recipe" action="save" enctype="multipart/form-data">
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
        <g:render template="/recipe/generalInfo" model="[recipeCO: recipeCO, timeUnits: timeUnits]"/>
        <g:render template="/recipe/ingredients" model="[recipeCO: recipeCO, metricUnits: metricUnits]"/>
        <g:render template="/recipe/cookingSteps" model="[recipeCO: recipeCO]"/>
        <g:render template="/recipe/serveWith" model="[recipeCO: recipeCO]"/>
        <g:render template="/recipe/nutrientFacts" model="[recipeCO: recipeCO, nutrients:nutrients]"/>

    </div>

    <g:render template="/recipe/preview"/>

    <div class="save-recips">
        <g:submitButton name="btnSave" class="save" value="Save Recipe" style="font-size:14px;color:white;background-color:orange;width:120px;height:35px;cursor:pointer;"/>
        <input type="button" name="preview" id="preview" value="Preview" style="font-size:14px;color:white;background-color:orange;width:120px;height:35px;cursor:pointer;"/>
        <input type="button" name="delete" id="delete" value="Delete" style="font-size:14px;color:white;background-color:orange;width:120px;height:35px;cursor:pointer;"/>
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
            var direction = getPlainText('optionDirections');
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
            'script': "${createLink(controller:'recipe', action:'uploadImage')}",
            'auto': true,
            'buttonImg': "${resource(dir:'jquery.uploadify-v2.1.0', file:'browser.jpg')}",
            'width': 130,
            onComplete: function(event, queId, fileObj, response, data) {
                jQuery('#selectRecipeImagePath').val(response);
                jQuery('#myImageDiv').html('<img id="recipeImage" border="0" width="195" src="${createLink(action:'showImage', controller:'recipe')}?selectRecipeImagePath=' + response + '"/>')
            }
        });
    })
</script>
</body>
</html>
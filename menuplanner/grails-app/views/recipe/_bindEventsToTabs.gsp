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
            var unitName = (unitId) ? jQuery('#optionIngredientUnitIds :selected').text() : ''
            var prodName = jQuery('#AddIngredientToolBox p').html()

            var intRegex = /^\d+$/;
            var floatRegex = /^((\d+(\.\d *)?)|((\d*\.)?\d+))$/;
            var fractionRegex = /^\d+(\s)*(\d*(\s)*\/(\s)*\d+(\s)*)*$/;

            if ((prodName.length > 0) && (intRegex.test(quantity) || fractionRegex.test(quantity) || quantity == '')) {
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
            'script': "${createLink(controller:'recipe', action:'uploadImage')}",
            'auto': true,
            'buttonImg': "${resource(dir:'jquery.uploadify-v2.1.0', file:'browser.jpg')}",
            'width': 130,
            onComplete: function(event, queId, fileObj, response, data) {
                jQuery('#selectRecipeImagePath').val(response);
                jQuery('#myImageDiv').html('<img id="recipeImage" border="0" width="195" src="${createLink(action:'showImage', controller:'recipe')}?selectRecipeImagePath=' + response + '"/>')
            }
        });
        jQuery('#preview').click()
    })

</script>

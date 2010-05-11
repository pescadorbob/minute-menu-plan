<script type="text/javascript">
    var sampleIngredientRowHTML = jQuery('#sampleIngredientRow>tbody').html();
    var sampleDirectionRowHTML = jQuery('#sampleDirectionRow>tbody').html();

    jQuery(document).ready(function() {
        jQuery('#tabGeneralInfo').click(function() {
            jQuery('.tabPanel').css('display', 'none');
            jQuery('.tabs').removeClass('current');
            jQuery('#tabGeneralInfo').addClass('current');
            jQuery('#panelGeneralInfo').show()
        })
        jQuery('#tabIngredients').click(function() {
            jQuery('.tabPanel').css('display', 'none');
            jQuery('.tabs').removeClass('current');
            jQuery('#tabIngredients').addClass('current');
            jQuery('#panelIngredients').show()
            bindEventsFor("tableIngredients", "ingredientRow");
            bindEventsFor("tableDirections", "directionRow")
        })
        jQuery('#tabCookingSteps').click(function() {
            jQuery('.tabPanel').css('display', 'none');
            jQuery('.tabs').removeClass('current');
            jQuery('#tabCookingSteps').addClass('current');
            jQuery('#panelCookingSteps').show()
            bindEventsFor("tableIngredients", "ingredientRow");
            bindEventsFor("tableDirections", "directionRow")
        })
        jQuery('#tabServeWith').click(function() {
            jQuery('.tabPanel').css('display', 'none');
            jQuery('.tabs').removeClass('current');
            jQuery('#tabServeWith').addClass('current');
            jQuery('#panelServeWith').show()
        })
        jQuery('#tabNutritionFacts').click(function() {
            jQuery('.tabPanel').css('display', 'none');
            jQuery('.tabs').removeClass('current');
            jQuery('#tabNutritionFacts').addClass('current');
            jQuery('#panelNutritionFacts').show()
        })
        /* REMOVE IMAGE: function to be executed when removeRecipeImage is Clicked... */
        jQuery('#removeRecipeImage').click(function() {
            jQuery('#recipeImage').attr('src', '${resource(dir: 'images', file: 'no-img.gif')}')
            jQuery('#selectRecipeImagePath').val("");
        })
        /* ADD INGREDIENT:  function to be executed when btnAddIngredient is Clicked... */
        jQuery('#btnAddIngredient').click(function() {
            var quantity = jQuery('#optionIngredientQuantities').attr('value')
            var unitId = jQuery('#optionIngredientUnitIds').attr('value')
            var productId = jQuery('#AddIngredientToolBox input[name=optionIngredientProductIds][value!=""]').attr('value')
            var unitName = (unitId) ? jQuery('#optionIngredientUnitIds :selected').text() : ''
            var unitSymbol = ''
            if(unitId){
                jQuery.each(jQuery('#unitTable td:contains('+ unitName +')'), function() {
                    if (jQuery(this).html() == unitName) {
                        unitSymbol = jQuery(this).next().html()
                    }
                });
            }
            var prodName = jQuery('#optionIngredientProductIds').val()

            var intRegex = /^\d+$/;
            var floatRegex = /^((\d+(\.\d *)?)|((\d*\.)?\d+))$/;
            var fractionRegex = /^\d+(\s)*(\d*(\s)*\/(\s)*\d+(\s)*)*$/;

            if ((prodName.length > 0) && (intRegex.test(quantity) || fractionRegex.test(quantity) || quantity == '')) {
                AddIngredient(quantity, unitId, productId, unitName, prodName, unitSymbol)
                /* Reset Add Ingredient ToolBox.... */
                jQuery('#optionIngredientQuantities').attr('value', '')
                jQuery('#optionIngredientUnitIds').val(1)
                jQuery('#optionIngredientProductIds').val('')
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
    function checkCategory() {
    }
</script>

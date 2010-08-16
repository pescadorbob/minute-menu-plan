<script type="text/javascript">
    var sampleIngredientRowHTML = jQuery('#sampleIngredientRow>tbody').html();
    var sampleDirectionRowHTML = jQuery('#sampleDirectionRow>tbody').html();

    jQuery(document).ready(function() {
        /* ADD INGREDIENT:  function to be executed when btnAddIngredient is Clicked or optionIngredientAisleIds is blured... */
        jQuery("#optionIngredientAisleIds").blur(function(){jQuery('#btnAddIngredient').click()})
        jQuery('#btnAddIngredient').click(function() {
            var quantity = jQuery('#optionIngredientQuantities').attr('value')
            var unitId = jQuery('#optionIngredientUnitIds').attr('value')
            var aisleId = jQuery('#optionIngredientAisleIds').attr('value')
            var preparationMethodId = jQuery('#optionIngredientPreparationMethodIds').attr('value')
            var productId = jQuery('#AddIngredientToolBox input[name=optionIngredientProductIds][value!=""]').attr('value')
            var unitName = (unitId) ? jQuery('#optionIngredientUnitIds :selected').text() : ''
            var unitSymbol = ''
            if (unitId) {
                jQuery.each(jQuery('#unitTable td:contains(' + unitName + ')'), function() {
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
                AddIngredient(quantity, unitId, productId, unitName, prodName, unitSymbol, aisleId, preparationMethodId)
                /* Reset Add Ingredient ToolBox.... */
                jQuery('#optionIngredientQuantities').attr('value', '')
                jQuery('#optionIngredientUnitIds').val(1)
                jQuery('#optionIngredientProductIds').val('')
                jQuery('#combobox_optionIngredientUnitIds').val('')
                jQuery('#optionIngredientPreparationMethodIds').val('')
                jQuery('#optionIngredientAisleIds').val('')
            }
            bindEventsFor("tableIngredients", "ingredientRow");
            jQuery('#optionIngredientQuantities').unbind('blur')
            jQuery('#optionIngredientQuantities').unbind('focus')
            jQuery('#optionIngredientUnitIds').unbind('blur')
            jQuery('#combobox_optionIngredientUnitIds').unbind('focus')
            jQuery('#optionIngredientProductIds').unbind('blur')
            jQuery('#optionIngredientProductIds').unbind('focus')
            jQuery('#optionIngredientQuantities').focus()
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
        /* REMOVE IMAGE: function to be executed when removeRecipeImage is Clicked... */
        jQuery('#removeRecipeImage').click(function() {
            jQuery('#recipeImage').attr('src', '${resource(dir: 'images', file: 'no-img.gif')}')
            jQuery('#selectRecipeImagePath').val("");
        })
        jQuery('#selectRecipeImage').uploadify({
            'uploader': "${resource(dir:'jquery.uploadify-v2.1.0', file:'uploadify.swf')}",
            'script': "${createLink(controller:'image', action:'uploadImage')}",
            'auto': true,
            'buttonImg': "${resource(dir:'jquery.uploadify-v2.1.0', file:'browser.jpg')}",
            'width': 130,
            onComplete: function(event, queId, fileObj, response, data) {
                jQuery('#selectRecipeImagePath').val(response);
                jQuery('#myImageDiv').html('<img id="recipeImage" border="0" height="200" width="200" src="${createLink(action:'imageByPath', controller:'image')}?imagePath=' + response + '&noImage=no-img.gif"/>')
            }
        });
        jQuery('#preview').click()
    })
</script>
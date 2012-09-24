<script type="text/javascript">
    jQuery(document).ready(function() {
        /* ADD INGREDIENT:  function to be executed when btnAddIngredient is Clicked or optionIngredientAisleIds is blurred... */
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
        jQuery('#preview').click(function() {
            reflectInPreviewPanel()
        })
        /* REMOVE IMAGE: function to be executed when removeRecipeImage is Clicked... */
        jQuery('#removeRecipeImage').click(function() {
            jQuery('#recipeImage').attr('src', '${resource(dir: 'images', file: 'no-img.gif')}')
            jQuery('#selectRecipeImagePath').val("");
        })
        jQuery('#selectRecipeImage').uploadify({
            'uploader': "${resource(dir:'js/jquery.uploadify-v2.1.0', file:'uploadify.swf')}",
            'script': "${createLink(controller:'image', action:'uploadImage')}",
            'auto': true,
            'buttonImg': "${resource(dir:'js/jquery.uploadify-v2.1.0', file:'browser.jpg')}",
            'width': 130,
            onComplete: function(event, queId, fileObj, response, data) {
                jQuery('#selectRecipeImagePath').val(response);
                jQuery('#myImageDiv').css('height', 200);
                jQuery('#myImageDiv').css('width', 200);
                jQuery('#myImageDiv').css('float', 'none');
                jQuery('#myImageDiv').css('margin-left', '0px');
                jQuery('#scaleImageSize150').css('height', 150);
                jQuery('#scaleImageSize150').css('width', 150);
                jQuery('#myImageDiv').html('<img id="recipeImage" border="0" src="${createLink(action:'imageByPath', controller:'image')}?imagePath=' + response + '&noImage=no-img.gif"/>')
                jQuery('.scaleImageSize img').load(function() {
                    scaleImageSize();
                });
            }
        });
        jQuery.each(jQuery('a'), function() {
         $(this).attr('target','_blank');
        });
    })

    function mpInitCallback() {
        jQuery('#preview').click()
    }
</script>
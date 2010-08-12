function AddIngredient(quantity, unitId, productId, unitName, prodName, unitSymbol, aisleId, preparationMethodId) {
    var addIngredient = sampleIngredientRowHTML;
    jQuery('#tableIngredients tbody').append(addIngredient)
    jQuery('.ingredientRowNew .Q').val(quantity);
    jQuery('.ingredientRowNew .U').val(unitId);
    jQuery('.ingredientRowNew .P').val(productId);
    jQuery('.ingredientRowNew .R').val(aisleId);
    jQuery('.ingredientRowNew .S').val(preparationMethodId);
    jQuery('.ingredientRowNew .UN').val(unitName);
    jQuery('.ingredientRowNew .US').val(unitSymbol);
    jQuery('.ingredientRowNew .PN').val(prodName);
    jQuery('.ingredientRowNew .quantity').html(quantity)
    jQuery('.ingredientRowNew .unit').html(unitName)
    jQuery('.ingredientRowNew .product').html(prodName)
    jQuery('.ingredientRowNew .aisle').html(aisleId)
    jQuery('.ingredientRowNew .preparationMethod').html(preparationMethodId)
    jQuery('.ingredientRowNew').attr('class', 'ingredientRow')
}

function AddDirection(direction) {
    var addDirection = sampleDirectionRowHTML;
    jQuery('#tableDirections tbody').append(addDirection)
    jQuery('.directionRowNew .D').val(direction);
    jQuery('.directionRowNew .DD').val(direction);
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
    jQuery.each(jQuery(domId + ' .direction'), function() {
        jQuery(this).click(function() {
            jQuery(this).next().show().focus();
            jQuery(this).hide();
        })
    })
    jQuery.each(jQuery(domId + ' .DD'), function() {
        jQuery(this).blur(function() {
            jQuery(this).prev().text(jQuery(this).val()).show();
            jQuery('.D', jQuery(this).parents('tr')).val(jQuery(this).val());
            jQuery(this).hide();
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

    jQuery('#displayRecipeImage').attr('src', jQuery('#recipeImage').attr('src'))

    var myCategory = ''
    var myCategoryList = []
    jQuery.each(jQuery('select[name=subCategoryIds][value!=""]'), function(indx, elem) {
        var myCategoryId = jQuery('option', jQuery(this)).eq(jQuery(this)[0].selectedIndex).attr('rel')
        jQuery.each(jQuery('#categoryTable td:contains(' + myCategoryId + ')'), function() {
            if (jQuery(this).html() == myCategoryId) {
                myCategoryList.push(jQuery(this).next().html())
            }
        });
    })
    myCategory = myCategoryList.join(', ')
    if (myCategory.length > 0) {
        myCategory = 'Categories: ' + myCategory
    }
    jQuery('#displayCategory').html(myCategory)
    jQuery('#displayName').html(getPlainText('name'))

    var showDesc = ''
    if (getPlainText('description').length) {
        showDesc = 'Description: ' + getPlainText('description')
    }
    jQuery('#displayDescription').html(showDesc)

    var showPrep = ''
    if (getPlainText('preparationTime').length) {
        showPrep = 'Prep - ' + getPlainText('preparationTime')
        if (jQuery('#preparationUnitId :selected').text() == 'Hours') {
            showPrep += ' hrs '
        }
        else {
            showPrep += ' mins '
        }
    }
    jQuery('#displayPrepTime').html(showPrep)
    var showCook = ''
    if (getPlainText('cookTime').length) {
        showCook = 'Cook - ' + getPlainText('cookTime')
        if (jQuery('#cookUnitId :selected').text() == 'Hours') {
            showCook += ' hrs'
        }
        else {
            showCook += ' mins'
        }
    }
    jQuery('#displayCookTime').html(showCook)
    var myDifficulty = 'Difficulty Level: '
    myDifficulty += jQuery('input[name=difficulty]').filter(':checked').val().substr(0, 1);
    myDifficulty += jQuery('input[name=difficulty]').filter(':checked').val().toLowerCase().substr(1);
    jQuery('#displayDifficulty').html(myDifficulty)
    var myMakeServing = ''
    if (getPlainText('makesServing').length > 0) {
        myMakeServing += 'Servings: ' + getPlainText('makesServing')
    }
    jQuery('#displayMakeServing').html(myMakeServing)

    var i;
    jQuery('#displayIngredients').html('')
    for (i = 1; i < jQuery('input[name="ingredientQuantities"]').size(); i++) {
        var preparationMethod = jQuery('input[name="hiddenIngredientPreparationMethodNames"]:eq(' + i + ')').attr('value')
        var myIngredients = jQuery('input[name="ingredientQuantities"]:eq(' + i + ')').attr('value') +
            //                            ' ' + jQuery('input[name="hiddenIngredientUnitNames"]:eq(' + i + ')').attr('value') +
                            ' ' + jQuery('input[name="hiddenIngredientUnitSymbols"]:eq(' + i + ')').attr('value') +
                            ' ' + jQuery('input[name="hiddenIngredientProductNames"]:eq(' + i + ')').attr('value') + (preparationMethod ? ' (' + preparationMethod + ')' : '') + '<br>'
        jQuery('#displayIngredients').append(myIngredients)
    }
    jQuery('#displayDirections').html('')
    for (i = 1; i < jQuery('input[name="directions"]').size(); i++) {
        jQuery('#displayDirections').append(jQuery('input[name="directions"]:eq(' + i + ')').attr('value') + ' ')
    }
    jQuery('#showPreviewRecipeImage').attr('src', jQuery('#recipeImage').attr('src'))

    var myServeWith = ''
    var myServeWithList = []
    jQuery.each(jQuery('input[name^="serveWithItems"][value!=""]'), function() {
        myServeWithList.push(jQuery(this).attr('value'))
    })
    myServeWith = myServeWithList.join(', ')
    if (myServeWith.length > 0) {
        myServeWith = 'Serve with:<br/>' + myServeWith
    }
    jQuery('#displayServeWith').html(myServeWith)

    var myNutrients = ''
    for (i = 0; i < jQuery('input[name="nutrientIds"]').size(); i++) {
        if (getPlainTextFromHtml(jQuery('input[name="nutrientQuantities"]:eq(' + i + ')').attr('value')) != '') {
            myNutrients += getPlainTextFromHtml(jQuery('input[name="nutrientQuantities"]:eq(' + i + ')').attr('value')) +
                           ' ' + getPlainTextFromHtml(jQuery('input[name="nutrientUnitSymbols"]:eq(' + i + ')').attr('value')) +
                           ' ' + getPlainTextFromHtml(jQuery('input[name="nutrientNames"]:eq(' + i + ')').attr('value'))
            if (i < jQuery('input[name="nutrientIds"]').size() - 2) {
                myNutrients += ', '
            }
        }
    }
    if (myNutrients.length >= 1) {
        myNutrients = 'Nutritional Facts per serving: ' + myNutrients
    }
    jQuery('#showNutrients').html(myNutrients)
}

function getPlainText(elementId) {
    var x = jQuery('#' + elementId).val()
    return getPlainTextFromHtml(x);
}

function getPlainTextFromHtml(htmlText) {
    var returnText = ''
    if (htmlText) {
        if (jQuery(htmlText).text()) {
            returnText = jQuery(htmlText).text()
        } else {
            returnText = htmlText
        }
    }
    if (returnText.length > 30) {
        returnText = returnText.substring(0, 28) + '...'
    }
    return returnText;
}

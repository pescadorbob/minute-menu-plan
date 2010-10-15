function reflectInPreviewPanel() {

    jQuery('#displayRecipeImage').attr('src', jQuery('#recipeImage').attr('src'))
    scaleImageTo150()

    var myCategory = ''
    var myCategoryList = []
    jQuery.each(jQuery('select[name=subCategoryIds][value!=""]'), function(indx, elem) {
        var myCategoryId = jQuery('option', jQuery(this)).eq(jQuery(this)[0].selectedIndex).attr('rel')
        var newCategory = jQuery('option', jQuery(this)).eq(jQuery(this)[0].selectedIndex).text()
        myCategoryList.push(newCategory)
    })
    myCategory = myCategoryList.join(', ')
    if (myCategory.length > 0) {
        myCategory = 'Categories: ' + myCategory
    }
    jQuery('#displayCategory').html(myCategory)
    jQuery('#displayName').html(getPlainText('name'))

    jQuery('#displayDescription').html('')
    var showDesc = ''
    if (tinyMCE.get('description').getContent().length) {
        showDesc = 'Description: ' + tinyMCE.get('description').getContent();
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

        var ingredient = jQuery('input[name="ingredientQuantities"]:eq(' + i + ')').attr('value')
        var itemName = jQuery('input[name="hiddenIngredientProductNames"]:eq(' + i + ')').attr('value')
        itemName = capitalize(itemName)
        var myIngredients = ingredient +
            //                            ' ' + jQuery('input[name="hiddenIngredientUnitNames"]:eq(' + i + ')').attr('value') +
                ' ' + jQuery('input[name="hiddenIngredientUnitSymbols"]:eq(' + i + ')').attr('value') +
                ' ' + itemName + (preparationMethod ? ' (' + preparationMethod + ')' : '') + '<br>'
        jQuery('#displayIngredients').append(myIngredients)
    }

    jQuery('#displayDirections').html('')
    var showDirection = ''
    if (tinyMCE.get('directions').getContent().length) {
        showDirection = 'Directions: ' + tinyMCE.get('directions').getContent();
    }
    jQuery('#displayDirections').html(showDirection)

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

function resetUpDownIngredientArrow() {
    $(".btnUp").css('visibility', 'visible')
    $(".btnDown").css('visibility', 'visible')
    $(".btnUp:eq(1)").css('visibility', 'hidden');
    $(".btnDown:last").css('visibility', 'hidden');
}

function bindEventUpDownIngredientArrow() {
    resetUpDownIngredientArrow()
    $('.btnUp').unbind();
    $('.btnDown').unbind();
    $('.btnUp').click(function() {
        $(this).parent().prev().before($(this).parent())
        resetUpDownIngredientArrow()
    })
    $('.btnDown').click(function() {
        $(this).parent().next().after($(this).parent())
        resetUpDownIngredientArrow()
    })
}

function showNewLineOnLastFocus() {
    $("#ingredientGrid>li:visible:last").prev().find('input:visible').focus(function() {
        $("#ingredientGrid").append($("ul.ingredients li:first").clone().show());
        $(this).parents('li').find('input').unbind('focus');
        resetIngredients();
        showNewLineOnLastFocus()
    })
}
function capitalize(incomingString) {
    return incomingString.charAt(0).toUpperCase() + incomingString.substring(1).toLowerCase();
}

function scaleImageTo150() {
    var imgH = jQuery('.scaleImageSize150 img').height();
    var imgW = jQuery('.scaleImageSize150 img').width();
    alert(imgH)
    alert(imgW)
    var divH = 150;
    var divW = 150;
    var imgRatio = imgH / imgW
    var divRatio = divH / divW
    if (imgRatio > divRatio) {
        jQuery('.scaleImageSize150 img').attr('height', divH);
        imgW = jQuery('.scaleImageSize150 img').width();
        jQuery('.scaleImageSize150').width(imgW);
    }
    else {
        jQuery('.scaleImageSize150 img').attr('width', divW);
        imgH = jQuery('.scaleImageSize150 img').height();
        jQuery('.scaleImageSize150').height(imgH)
    }
}
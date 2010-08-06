<%@ page import="com.mp.domain.*" %>
<div class="tabPanel leftbox clearfix" id=panelIngredients style="display:none;">
    <div class="formElement">
        <div class="showIngredientsHere">
            <table id="unitTable" style="display:none;">
                <g:each in="${metricUnits}">
                    <tr>
                        <td>${it?.name}</td>
                        <td>${it?.symbol}</td>
                    </tr>
                </g:each>
            </table>
            <table id="tableIngredients" cellspacing="0px" cellpadding="0px" width="100%" class="menuplannerTab">
                <tr id="tableIngredientsHeader" class="mnuTableHeader">
                    <td width="25">&nbsp;</td>
                    <td width="25">&nbsp;</td>
                    <td width="30">&nbsp;</td>
                    <td width="65"><strong>Amount</strong></td>
                    <td width="105"><strong>Unit</strong></td>
                    <td width="105"><strong>Ingredient</strong></td>
                    <td width="110"><strong>Preparation</strong></td>
                    <td><strong>Aisle</strong></td>
                </tr>
                <!-- Show Ingredients Here -->
                <g:each status="i" in="${recipeCO?.hiddenIngredientProductNames}" var="x">
                    <g:render template="ingredientRowWithParams"
                            model="[hiddenIngredientUnitNames:recipeCO?.hiddenIngredientUnitNames[i],
                            hiddenIngredientProductNames:recipeCO?.hiddenIngredientProductNames[i],
                            hiddenIngredientAisleNames:recipeCO?.hiddenIngredientAisleNames[i],
                            hiddenIngredientPreparationMethodNames:recipeCO?.hiddenIngredientPreparationMethodNames[i],
                            ingredientQuantity:recipeCO?.ingredientQuantities[i],
                            ingredientUnitId:recipeCO?.ingredientUnitIds[i],
                            ingredientProductId:recipeCO?.ingredientProductIds[i],
                            ingredientAisleId:recipeCO?.ingredientAisleIds[i],
                            ingredientPreparationMethodId:recipeCO?.ingredientPreparationMethodIds[i],
                             hiddenIngredientUnitSymbol:recipeCO?.hiddenIngredientUnitSymbols[i]]"/>
                </g:each>
            </table>
        </div>
        <ul class="ingredients">
            <li class="liForToolBoxes">
                <span id="AddIngredientToolBox" class="toolBoxes" style="width:400px;">
                    <img class="imagePointer" id="btnAddIngredient" src="${resource(dir: 'images', file: 'plus-add.jpg')}" hspace="4" align="left" border="0"/>
                    <span id="ingredientToBeAdded">
                        <div style="float:left;">
                            <g:textField class="inpboxSmall showToolTip" id='optionIngredientQuantities' name="optionIngredientQuantities" value="" title="${g.message(code:'toolTip.recipe.amount')}" style="width:40px;"/>
                            <g:select class="inpbox" id='optionIngredientUnitIds' noSelection="['':'(No Unit)']" name="optionIngredientUnitIds" from="${metricUnits}" optionKey="id" style="width:105px;display:none;"/>
                            <input name="combobox_optionIngredientUnitIds" class="inpbox showToolTip" id="combobox_optionIngredientUnitIds" title="${g.message(code: 'toolTip.recipe.unit')}" style="width:90px;">
                        </div>
                        <div style="float:left; padding-left:5px;">
                            <input class="inpbox showToolTip" id="optionIngredientProductIds" name="optionIngredientProductIds" value="" title="${g.message(code: 'toolTip.recipe.ingredient')}" style="width:90px;"/>
                        </div>
                        <div style="float:left; padding-left:5px;">
                            <g:select class="inpbox" id='select_optionIngredientPreparationMethodIds' noSelection="['':'(No Preparation Method)']"
                                    name="select_optionIngredientPreparationMethodIds" from="${preparationMethods}" optionKey="id" style="width:105px;display:none;"/>
                            <input class="inpbox" id="optionIngredientPreparationMethodIds" name="optionIngredientPreparationMethodIds" value="" style="width:90px;"/>
                        </div>
                        <div style="float:left; padding-left:5px;">
                            <g:select class="inpbox" id='select_optionIngredientAisleIds' noSelection="['':'(No Aisle)']"
                                    name="select_optionIngredientAisleIds" from="${aisles}" optionKey="id" style="width:1905px;display:none;"/>
                            <input class="inpbox" id="optionIngredientAisleIds" name="optionIngredientAisleIds" value="" style="width:90px;"/>
                        </div>
                    </span>
                </span>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript">
    $("#optionIngredientProductIds").autocomplete("${createLink(action: 'getMatchingItems', controller: 'recipe')}", {
        width : 300,
        minChars: 3,
        selectFirst: false
    });

    var itemsJson = {
        <g:each in="${Item.list()}" var="itemVar">
        '${itemVar?.name}':'${itemVar?.suggestedAisle?.name}',
        </g:each>
    }

    $("#optionIngredientProductIds").result(function(event, data, formatted) {
        jQuery(this).val(data[0]);
        if (itemsJson[data[0]] != '') {
            jQuery("#optionIngredientAisleIds").val(itemsJson[data[0]])
        } else {
            jQuery("#optionIngredientAisleIds").val('')

        }
    })
    $("#optionIngredientAisleIds").focus(function() {
        if (itemsJson[jQuery("#optionIngredientAisleIds").val()] != 'undefined') {
            jQuery("#optionIngredientAisleIds").val(jQuery("#optionIngredientAisleIds").val())
        } else {
            jQuery("#optionIngredientAisleIds").val('')

        }
    })
    var metricUnits = []
    <g:each in="${metricUnits}" var="metricUnit">
    metricUnits.push('${metricUnit}')
    </g:each>
    metricUnits.push('Other...')
    $("#combobox_optionIngredientUnitIds").autocomplete(metricUnits, {
        matchContains: true,
        minChars: 0,
        max:0,
        mustMatch:true
    });

    var aisles = []
    <g:each in="${aisles}" var="aisle">
    aisles.push('${aisle}')
    </g:each>
    $("#optionIngredientAisleIds").autocomplete(aisles, {
        selectFirst: false,
        minChars: 0,
        max:0,
        mustMatch:false
    });

    var preparationMethods = []
    <g:each in="${preparationMethods}" var="preparationMethod">
    preparationMethods.push('${preparationMethod}')
    </g:each>
    $("#optionIngredientPreparationMethodIds").autocomplete(preparationMethods, {
        selectFirst: false,
        minChars: 0,
        max:0,
        mustMatch:false
    });

    $("#combobox_optionIngredientUnitIds").result(function(event, data, formatted) {
        var currentUnit = jQuery(this).val()
        if (currentUnit == 'Other...') {
            $("#optionIngredientUnitIds").val('')
            $("#unitAddPopup").show()
        } else {
            $("#optionIngredientUnitIds").children().each(function() {
                if ($(this).text() == currentUnit) {
                    $(this).attr('selected', 'selected')
                    $("#unitAddPopup").hide()
                }
            })
        }
    })

    $(".showToolTip").tooltip({events: {
        input: "focus,blur"
    },
        effect:'slide'
    }).dynamic({ bottom: { direction: 'down', bounce: true } })

</script>

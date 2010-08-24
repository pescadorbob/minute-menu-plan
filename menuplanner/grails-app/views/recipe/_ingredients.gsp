<%@ page import="com.mp.domain.*" %>
<div class="clearfix" id=panelIngredients>
    <div class="recipeSubhead" style="${hasErrors(bean: recipeCO, field: 'hiddenIngredientProductNames', 'color:red;')}">Ingredients:</div>
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
                    <td width="">&nbsp;</td>
                    <td width="">&nbsp;</td>
                    <td width="">&nbsp;</td>
                    <td width="65"><strong>Amount</strong></td>
                    <td width="105"><strong>Unit</strong></td>
                    <td width="105"><strong>Ingredient</strong></td>
                    <td width="110"><strong>Preparation</strong></td>
                    <td><strong>Aisle</strong></td>
                </tr>
                <!-- Show Ingredients Here -->
                %{--<g:each status="i" in="${recipeCO?.hiddenIngredientProductNames}" var="x">
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
                </g:each>--}%
            </table>
        </div>
        <ul class="ingredients" id="ingredientGrid">
            <g:each in="${(1..6)}" var="i">
                <li style="width:554px;display:${(i == 1) ? 'none' : ''}">
                    <span class="toolBoxes addIngredientBox" style="width:400px;">
                        <span id="ingredientToBeAdded">
                            <div style="float:left;">
                                <g:textField class="inpboxSmall showToolTip iAmount" name="iAmounts" value="" title="${g.message(code:'toolTip.recipe.amount')}" style="width:40px;"/>
                                <input name="iUnits" class="inpbox showToolTip iUnit" title="${g.message(code: 'toolTip.recipe.unit')}" style="width:90px;">
                            </div>
                            <div style="float:left; padding-left:5px;">
                                <input class="inpbox showToolTip iProduct" name="iProducts" value="" title="${g.message(code: 'toolTip.recipe.ingredient')}" style="width:90px;"/>
                            </div>
                            <div style="float:left; padding-left:5px;">
                                <input class="inpbox iPreparationMethod" name="iPreparationMethods" value="" style="width:90px;"/>
                            </div>
                            <div style="float:left; padding-left:5px;">
                                <input class="inpbox iAisle" name="iAsiles" value="" style="width:90px;"/>
                            </div>
                        </span>
                    </span>
                    <img class="btnUp" src="${resource(dir: 'images', file: 'arw-up.gif')}" hspace="2" vspace="2"/>
                    <img class="btnDown" src="${resource(dir: 'images', file: 'arw-dwn.gif')}" vspace="2" hspace="2"/>
                </li>
            </g:each>
        </ul>
    </div>
</div>



<script type="text/javascript">
    var itemsJson = {
        <g:each in="${Item.list()}" var="itemVar">
        '${itemVar?.name}':'${itemVar?.suggestedAisle?.name}',
        </g:each>
    }

    var metricUnits = []
    <g:each in="${metricUnits}" var="metricUnit">
    metricUnits.push(['${metricUnit}','${metricUnit.id}'])
    </g:each>
    metricUnits.push('Other...')

    var preparationMethods = []
    <g:each in="${preparationMethods}" var="preparationMethod">
    preparationMethods.push(['${preparationMethod}','${preparationMethod.id}'])
    </g:each>

    var aisles = []
    <g:each in="${aisles}" var="aisle">
    aisles.push(['${aisle}','${aisle.id}'])
    </g:each>
    var unitPopupCaller;

    function resetUnitAutocomplete() {
        $(".iUnit").unautocomplete().autocomplete(metricUnits, {
            matchContains: true,
            minChars: 0,
            max:0,
            mustMatch:true
        }).result(function(event, data, formatted) {
            var currentUnit = jQuery(this).val()
            if (currentUnit == 'Other...') {
                $(this).val('');
                unitPopupCaller = this;
                $("#unitAddPopup").show();
                $("#unitName").focus();
            } else {
                /*$("#optionIngredientUnitIds").children().each(function() {
                 if ($(this).text() == currentUnit) {
                 $(this).attr('selected', 'selected')
                 $("#unitAddPopup").hide()
                 }
                 })*/
            }
        })
    }
    function resetIngredients() {

        resetUnitAutocomplete()
        $(".iProduct").unautocomplete().autocomplete("${createLink(action: 'getMatchingItems', controller: 'recipe')}", {
            width : 300,
            minChars: 3,
            selectFirst: false
        }).result(function(event, data, formatted) {
            $(this).val(data[0]);
            if (itemsJson[data[0]] != '') {
                $(".iAisle", $(this).parents(".addIngredientBox")).val(itemsJson[data[0]])
            } else {
                $(".iAisle", $(this).parents(".addIngredientBox")).val('')

            }
        })
        $(".iPreparationMethod").unautocomplete().autocomplete(preparationMethods, {
            selectFirst: false,
            minChars: 0,
            max:0,
            mustMatch:false
        });


        $(".iAisle").unautocomplete().autocomplete(aisles, {
            selectFirst: false,
            minChars: 0,
            max:0,
            mustMatch:false
        });
        showNewLineOnLastFocus();
        bindEventUpDownIngredientArrow()
    }


    $(function() {
        resetIngredients();
    })

</script>


%{--<script type="text/javascript">
    $("#combobox_optionIngredientUnitIds").result(function(event, data, formatted) {
        var currentUnit = jQuery(this).val()
        if (currentUnit == 'Other...') {
            $("#optionIngredientUnitIds").val('')
            $("#unitAddPopup").show()
            $("#unitName").focus();
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

</script>--}%

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
                    <td width="75" align="center"><strong>Amount</strong></td>
                    <td width="105"><strong>Unit</strong></td>
                    <td width="115"><strong>Ingredient</strong></td>
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
            <g:render template="ingredientRow" model="[display:'none']"/>
            <g:if test="${recipeCO?.hiddenIngredientProductNames}">
                <g:each status="i" in="${recipeCO?.hiddenIngredientProductNames}" var="x">
                    <g:render template="ingredientRow" model="[hiddenIngredientUnitNames:recipeCO?.hiddenIngredientUnitNames[i],
                            hiddenIngredientProductNames:recipeCO?.hiddenIngredientProductNames[i],
                            hiddenIngredientAisleNames:recipeCO?.hiddenIngredientAisleNames[i],
                            hiddenIngredientPreparationMethodNames:recipeCO?.hiddenIngredientPreparationMethodNames[i],
                            ingredientQuantity:recipeCO?.ingredientQuantities[i],
                            ingredientUnitId:recipeCO?.ingredientUnitIds[i],
                            ingredientProductId:recipeCO?.ingredientProductIds[i],
                            ingredientAisleId:recipeCO?.ingredientAisleIds[i],
                            ingredientPreparationMethodId:recipeCO?.ingredientPreparationMethodIds[i],
                             hiddenIngredientUnitSymbol:recipeCO?.hiddenIngredientUnitSymbols[i]]" />                    
                </g:each>
                <g:render template="ingredientRow"/>                
            </g:if>
            <g:else>
                <g:each in="${(1..5)}" var="i">
                    <g:render template="ingredientRow"/>
                </g:each>
            </g:else>

        </ul>
    </div>
</div>



<script type="text/javascript">
    var itemsJson = {
        <g:each in="${Item.list()}" var="itemVar">
        '${itemVar?.name}':['${itemVar?.suggestedAisle?.name}','${itemVar?.suggestedAisle?.id}'],
        </g:each>
    }

    var metricUnits = []
    <g:each in="${metricUnits}" var="metricUnit">
    metricUnits.push(['${metricUnit}','${metricUnit.id}'])
    </g:each>
//    metricUnits.push('Other...')

    var preparationMethods = []
    <g:each in="${preparationMethods}" var="preparationMethod">
    preparationMethods.push(['${preparationMethod}','${preparationMethod.id}'])
    </g:each>

    var aisles = []
    <g:each in="${aisles}" var="aisle">
    aisles.push(['${aisle}','${aisle.id}'])
    </g:each>
//    var unitPopupCaller;

    function resetUnitAutocomplete() {
        $(".iUnit").unautocomplete().autocomplete(metricUnits, {
            matchContains: true,
            minChars: 0,
            max:0,
            mustMatch:true
        }).result(function(event, data, formatted) {
            var unitId = data[1];
            var currentUnit = jQuery(this).val()
//            if (currentUnit == 'Other...') {
//                $(this).val('');
//                unitPopupCaller = this;
//                $("#unitAddPopup").show();
//                $("#unitName").focus();
//            } else {
                $(this).next().val(unitId);
//            }
            if (jQuery('#unitTable td:contains(' + currentUnit + ')')) {
                if (jQuery('#unitTable td:contains(' + currentUnit + ')').eq(0).text() == currentUnit) {
                    var unitSymbol = jQuery('#unitTable td:contains(' + currentUnit + ')').eq(0).next().html()
                    $(this).next().next().val(unitSymbol)
                }
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
            $(this).next().val(data[1])
            if (itemsJson[data[0]]) {
                $(".iAisle", $(this).parents(".addIngredientBox")).val(itemsJson[data[0]][0])
                $(".iAisle", $(this).parents(".addIngredientBox")).next().val(itemsJson[data[0]][1])
            } else {
                $(".iAisle", $(this).parents(".addIngredientBox")).val('')

            }
        })
        $(".iPreparationMethod").unautocomplete().autocomplete(preparationMethods, {
            selectFirst: false,
            minChars: 0,
            max:0,
            mustMatch:false
        }).result(function(event, data, formatted) {
            $(this).next().val(data[1])
        })


        $(".iAisle").unautocomplete().autocomplete(aisles, {
            selectFirst: false,
            minChars: 0,
            max:0,
            mustMatch:false
        }).result(function(event, data, formatted) {
            $(this).next().val(data[1])
        })
        showNewLineOnLastFocus();
        bindEventUpDownIngredientArrow()
    }


    $(function() {
        resetIngredients();
    })

</script>


%{--<script type="text/javascript">
    $(".showToolTip").tooltip({events: {
        input: "focus,blur"
    },
        effect:'slide'
    }).dynamic({ bottom: { direction: 'down', bounce: true } })

</script>--}%

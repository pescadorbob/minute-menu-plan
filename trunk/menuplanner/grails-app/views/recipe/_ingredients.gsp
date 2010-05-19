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
                    <td width="24">&nbsp;</td>
                    <td width="24">&nbsp;</td>
                    <td width="30">&nbsp;</td>
                    <td width="70"><strong>Amount</strong></td>
                    <td width="110"><strong>Unit</strong></td>
                    <td><strong>Ingredient</strong></td>
                </tr>
                <!-- Show Ingredients Here -->
                <g:each status="i" in="${recipeCO?.hiddenIngredientProductNames}" var="x">
                    <g:render template="ingredientRowWithParams"
                            model="[hiddenIngredientUnitNames:recipeCO?.hiddenIngredientUnitNames[i],hiddenIngredientProductNames:recipeCO.hiddenIngredientProductNames[i], ingredientQuantity:recipeCO.ingredientQuantities[i],ingredientUnitId:recipeCO.ingredientUnitIds[i],ingredientProductId:recipeCO.ingredientProductIds[i], hiddenIngredientUnitSymbol:recipeCO?.hiddenIngredientUnitSymbols[i]]"/>
                </g:each>
            </table>
        </div>
        <ul class="ingredients">
            <li class="liForToolBoxes">
                <span id="AddIngredientToolBox" class="toolBoxes" style="width:400px;">
                    <img class="imagePointer" id="btnAddIngredient" src="${resource(dir: 'images', file: 'plus-add.jpg')}" hspace="4" align="left" border="0"/>
                    <span id="ingredientToBeAdded">
                        <div style="float:left;">
                            <g:textField class="inpboxSmall" id='optionIngredientQuantities' name="optionIngredientQuantities" value=""/>
                            <g:select class="inpbox" id='optionIngredientUnitIds' noSelection="['':'(No Unit)']" name="optionIngredientUnitIds" from="${metricUnits}" optionKey="id" style="width:105px;"/>
                        </div>
                        <div style="padding-top:2px; float:left; padding-left:5px;">
                            <input class="inpbox" id="optionIngredientProductIds" name="optionIngredientProductIds" value=""/>
                        </div>
                    </span>
                </span>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript">
    $("#optionIngredientProductIds").autocomplete("${createLink(action: 'getMatchingItems', controller: 'recipe')}", {
            width: 300,
            multiple: false,
            matchContains: true,
        });

    $("#optionIngredientProductIds").result(function(event, data, formatted) {
        jQuery(this).val(data[0]);
    })

</script>

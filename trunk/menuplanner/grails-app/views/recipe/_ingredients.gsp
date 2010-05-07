<div class="tabPanel leftbox clearfix" id=panelIngredients style="display:none;">
    <div class="formElement">
        <div class="showIngredientsHere" style="">
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
                <g:each status="i" in="${recipeCO?.hiddenIngredientProductNames}" var="X">
                    <g:render template="ingredientRowWithParams" model="[hiddenIngredientUnitNames:recipeCO?.hiddenIngredientUnitNames[i],hiddenIngredientProductNames:recipeCO.hiddenIngredientProductNames[i], ingredientQuantity:recipeCO.ingredientQuantities[i],ingredientUnitId:recipeCO.ingredientUnitIds[i],ingredientProductId:recipeCO.ingredientProductIds[i], hiddenIngredientUnitSymbol:recipeCO?.hiddenIngredientUnitSymbols[i]]"/>
                </g:each>
            </table>
        </div>
        <ul class="ingredients">
            <li class="liForToolBoxes">
                <span id="AddIngredientToolBox" class="toolBoxes" style="width:400px;">
                    <img class="imagePointer" id="btnAddIngredient" src="${resource(dir: 'images', file: 'plus-add.jpg')}" hspace="4" align="left" border="0"/>
                    <span id="ingredientToBeAdded" style="display:block; float:left;padding-left:10px;">
                        <div style="float:left;">
                            <g:textField class="inpbox" id='optionIngredientQuantities' name="optionIngredientQuantities" value="" style="width:50px;"/>
                            <g:select class="inpbox" id='optionIngredientUnitIds' noSelection="['':'(No Unit)']" name="optionIngredientUnitIds" from="${metricUnits}" optionKey="id" style="width:105px;"/>
                        </div>
                        <div style="padding-top:2px; float:left; padding-left:5px;">
                            <mp:tagInput name="optionIngredientProductIds" controller="recipe" action="getMatchingItems" multiselect="false"/>
                        </div>
                    </span>
                </span>
            </li>
        </ul>
    </div>
</div>

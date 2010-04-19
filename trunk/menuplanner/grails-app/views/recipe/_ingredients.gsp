<div id="panelIngredients" class="left-container2" style="display:none;">
    <div class="min-height-container">
        <ul class="ingredients">
            <li>
                <div class="showIngredientsHere" style="">
                    <table id="tableIngredients" cellspacing="0px" cellpadding="0px">
                        <tr id="tableIngredientsHeader" class="mnuTableHeader">
                            <td width="24">&nbsp;</td>
                            <td width="24">&nbsp;</td>
                            <td width="30">&nbsp;</td>
                            <td width="63"><strong>Amount</strong></td>
                            <td width="107"><strong>Unit</strong></td>
                            <td><strong>Ingredient</strong></td>
                        </tr>
                        <!-- Show Ingredients Here -->
                        <g:each status="i" in="${recipeCO?.hiddenIngredientUnitNames}" var="X">
                            <g:render template="ingredientRowWithParams" model="[hiddenIngredientUnitNames:recipeCO?.hiddenIngredientUnitNames[i],hiddenIngredientProductNames:recipeCO.hiddenIngredientProductNames[i], ingredientQuantity:recipeCO.ingredientQuantities[i],ingredientUnitId:recipeCO.ingredientUnitIds[i],ingredientProductId:recipeCO.ingredientProductIds[i]]"/>
                        </g:each>
                    </table>
                </div>
            </li>
            <li class="clr">
                <span id="AddIngredientToolBox" class="toolBoxes" style="width:537px;">
                    <img class="imagePointer" id="btnAddIngredient" src="${resource(dir: 'images', file: 'plus-add.jpg')}" hspace="4" align="left" border="0"/>
                    <span id="ingredientToBeAdded" style="display:block; float:left;padding-left:10px;">
                        <g:textField class="input2" id='optionIngredientQuantities' name="optionIngredientQuantities" value="" style="margin-right:5px;"/>
                        <g:select class="select2" id='optionIngredientUnitIds' name="optionIngredientUnitIds" from="${metricUnits}" optionKey="id" style="width:105px;"/>
                        <div style="padding-top:2px; float:left;">
                            <mp:tagInput name="optionIngredientProductIds" controller="recipe" action="getMatchingItems" multiselect="false"/>
                        </div>
                    </span>
                </span>
            </li>
        </ul>
    </div>
    <div style="clear:both;">
        <img src="${resource(dir: 'images', file: 'left-container-img1.jpg')}"/>
    </div>
</div>

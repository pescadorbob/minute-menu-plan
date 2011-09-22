<li style="width:572px;display:${display ?: ''}">
  <g:render template="nutrientLink" model="[ingredientFoodMapId:ingredientFoodMapId,hiddenIngredientFoodMapName:hiddenIngredientFoodMapName]"/>
    <span class="toolBoxes addIngredientBox" style="width:400px;">
        <span id="ingredientToBeAdded">
            <div style="float:left;">
                <g:textField class="inpboxSmall showToolTip iAmount" name="ingredientQuantities" value="${ingredientQuantity}" title="${g.message(code:'toolTip.recipe.amount')}" style="width:40px;"/>
                <input name="hiddenIngredientUnitNames" value="${hiddenIngredientUnitNames}" class="inpbox showToolTip iUnit" title="${g.message(code: 'toolTip.recipe.unit')}" style="width:90px;">
                <input type="hidden" value="${ingredientUnitId}" name="ingredientUnitIds"/>
                <input type="hidden" name="hiddenIngredientUnitSymbols" value="${hiddenIngredientUnitSymbol}"/>
                <input type="hidden" value="${ingredientFoodMapId}" name="ingredientFoodMapIds"/>
            </div>
            <div style="float:left; padding-left:5px;">
                <input class="inpbox showToolTip iProduct" name="hiddenIngredientProductNames" value="${hiddenIngredientProductNames?.encodeAsHTML()}" title="${g.message(code: 'toolTip.recipe.ingredient')}" style="width:90px;"/>
                <input type="hidden" value="${hiddenIngredientProductNames}" name="ingredientProductIds"/>
            </div>
            <div style="float:left; padding-left:5px;">
                <input class="inpbox iPreparationMethod" name="hiddenIngredientPreparationMethodNames" value="${hiddenIngredientPreparationMethodNames?.encodeAsHTML()}" style="width:90px;"/>
                <input type="hidden" value="${hiddenIngredientPreparationMethodNames}" name="ingredientPreparationMethodIds"/>
            </div>
            <div style="float:left; padding-left:5px;">
                <input class="inpbox iAisle" name="hiddenIngredientAisleNames" value="${hiddenIngredientAisleNames?.encodeAsHTML()}" style="width:90px;"/>
                <input type="hidden" value="${hiddenIngredientAisleNames}" name="ingredientAisleIds"/>
            </div>
        </span>
    </span>
    <p:image src='arw-up.gif' class="btnUp" hspace="2" vspace="2"/>
    <p:image src='arw-dwn.gif' class="btnDown" vspace="2" hspace="2"/>
</li>
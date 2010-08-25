<li style="width:554px;display:${display?:''}">
    <span class="toolBoxes addIngredientBox" style="width:400px;">
        <span id="ingredientToBeAdded">
            <div style="float:left;">
                <g:textField class="inpboxSmall showToolTip iAmount" name="ingredientQuantities" value="${ingredientQuantity}" title="${g.message(code:'toolTip.recipe.amount')}" style="width:40px;"/>
                <input name="hiddenIngredientUnitNames" value="${hiddenIngredientUnitNames}" class="inpbox showToolTip iUnit" title="${g.message(code: 'toolTip.recipe.unit')}" style="width:90px;">
                <input type="hidden" value="${ingredientUnitId}" name="ingredientUnitIds"/>
                <input type="hidden" name="hiddenIngredientUnitSymbols" value="${hiddenIngredientUnitSymbol}"/>
            </div>
            <div style="float:left; padding-left:5px;">
                <input class="inpbox showToolTip iProduct" name="hiddenIngredientProductNames" value="${hiddenIngredientProductNames}" title="${g.message(code: 'toolTip.recipe.ingredient')}" style="width:90px;"/>
                <input type="hidden" value="${ingredientProductId}" name="ingredientProductIds"/>
            </div>
            <div style="float:left; padding-left:5px;">
                <input class="inpbox iPreparationMethod" name="hiddenIngredientPreparationMethodNames" value="${hiddenIngredientPreparationMethodNames}" style="width:90px;"/>
                <input type="hidden" value="${ingredientPreparationMethodId}" name="ingredientPreparationMethodIds"/>
            </div>
            <div style="float:left; padding-left:5px;">
                <input class="inpbox iAisle" name="hiddenIngredientAisleNames" value="${hiddenIngredientAisleNames}" style="width:90px;"/>
                <input type="hidden" value="${ingredientAisleId}" name="ingredientAisleIds"/>
            </div>
        </span>
    </span>
    <img class="btnUp" src="${resource(dir: 'images', file: 'arw-up.gif')}" hspace="2" vspace="2"/>
    <img class="btnDown" src="${resource(dir: 'images', file: 'arw-dwn.gif')}" vspace="2" hspace="2"/>
</li>
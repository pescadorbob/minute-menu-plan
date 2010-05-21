<tr class="ingredientRow">
    <td>
        <img class="btnDelete" src="${resource(dir: 'images', file: 'remove1.gif')}" hspace="2" vspace="2"/>
        <span class="optionImages">
            <input class='Q' type="hidden" value="${ingredientQuantity}" name="ingredientQuantities"/>
            <input class='U' type="hidden" value="${ingredientUnitId}" name="ingredientUnitIds"/>
            <input class='US' type="hidden" value="${hiddenIngredientUnitSymbol}" name="hiddenIngredientUnitSymbols"/>
            <input class='P' type="hidden" value="${ingredientProductId}" name="ingredientProductIds"/>
        </span>
        <span class="hiddenTextIngredient">
            <input class='UN' type="hidden" name="hiddenIngredientUnitNames" value="${hiddenIngredientUnitNames}"/>
            <input class='PN' type="hidden" name="hiddenIngredientProductNames" value="${hiddenIngredientProductNames}"/>
        </span>
    </td>
    <td>
        <img class="btnUp" src="${resource(dir: 'images', file: 'arw-up.gif')}" hspace="2" vspace="2"/>
    </td>
    <td>
        <img class="btnDown" src="${resource(dir: 'images', file: 'arw-dwn.gif')}" vspace="2" hspace="2"/>
    </td>
    <td>
        <span class="quantity">${ingredientQuantity}</span>
    </td>
    <td>
        <span class="unit">${hiddenIngredientUnitNames}</span>
    </td>
    <td>
        <span class="product">${hiddenIngredientProductNames}</span>
    </td>
</tr>
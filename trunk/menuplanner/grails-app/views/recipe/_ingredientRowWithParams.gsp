<span class="ingredientRow">
    <span class="optionImages">
        <img class="btnDelete" src="${resource(dir: 'images', file: 'remove1.gif')}" width="11" height="13" hspace="2" vspace="2" border="0" style="cursor: pointer;"/>
        <img class="btnUp" src="${resource(dir: 'images', file: 'arw-up.gif')}" hspace="2" vspace="2" border="0" style="cursor: pointer;"/>
        <img class="btnDown" src="${resource(dir: 'images', file: 'arw-dwn.gif')}" vspace="2" hspace="2" border="0" style="cursor: pointer;"/>
    </span>
    <span class="ingredientHiddenFields">
        <input class='Q' type="hidden" value="${ingredientQuantity}" name="ingredientQuantities"/>
        <input class='U' type="hidden" value="${ingredientUnitId}" name="ingredientUnitIds"/>
        <input class='P' type="hidden" value="${ingredientProductId}" name="ingredientProductIds"/>
    </span>
    <span class="showIngredient" style="padding-left:10px;"> ${hiddenIngredient} </span>
    <span class="hiddenTextIngredient">
        <input class='H' type="hidden" name="hiddenIngredients" value="${hiddenIngredient}"/>
    </span>
</span>
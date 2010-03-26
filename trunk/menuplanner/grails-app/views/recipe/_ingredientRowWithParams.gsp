<li>
<span class="ingredientRow">
    <span class="optionImages">
        <img hspace="2" height="16" border="0" align="left" width="16" vspace="2" style="cursor: pointer;" src="${resource(dir: 'images', file: 'delete.jpg')}" class="btnDelete"/>
        <img hspace="2" height="16" border="0" align="left" width="16" vspace="2" style="cursor: pointer;" src="${resource(dir: 'images', file: 'arrow-up.jpg')}" class="btnUp"/>
        <img hspace="2" height="16" border="0" align="left" width="16" vspace="2" style="cursor: pointer;" src="${resource(dir: 'images', file: 'arrow-dwn.jpg')}" class="btnDown"/>
    </span>
    <span class="ingredientHiddenFields">
        <input class='Q' type="hidden" value="${ingredientQuantity}" name="ingredientQuantities"/>
        <input class='U' type="hidden" value="${ingredientUnitId}" name="ingredientUnitIds"/>
        <input class='P' type="hidden" value="${ingredientProductId}" name="ingredientProductIds"/>
    </span>
    <span class="showIngredient"> ${hiddenIngredient} </span>
    <span class="hiddenTextIngredient">
        <input class='H' type="hidden" name="hiddenIngredients" value="${hiddenIngredient}"/>
    </span>
</span>
</li>
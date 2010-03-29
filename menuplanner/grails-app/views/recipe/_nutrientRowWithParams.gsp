<span class="nutrientsRow">
    <span class="optionImages">
        <img hspace="2" height="16" border="0" align="left" width="16" vspace="2" style="cursor: pointer;" src="${resource(dir: 'images', file: 'delete.jpg')}" class="btnDelete"/>
        <img hspace="2" height="16" border="0" align="left" width="16" vspace="2" style="cursor: pointer;" src="${resource(dir: 'images', file: 'arrow-up.jpg')}" class="btnUp"/>
        <img hspace="2" height="16" border="0" align="left" width="16" vspace="2" style="cursor: pointer;" src="${resource(dir: 'images', file: 'arrow-dwn.jpg')}" class="btnDown"/>
    </span>
    <span class="nutrientHiddenFields">
        <input class='Q' type="hidden" value="${nutrientQuantity}" name="nutrientQuantities"/>
        <input class='T' type="hidden" value="${nutrientId}" name="nutrientIds"/>
    </span>
    <span class="showNutrient">${hiddenNutrients}</span>
    <span class="hiddenTextNutrient">
        <input class='H' type="hidden" name="hiddenNutrients" value="${hiddenNutrients}"/>
    </span> <br> <br>
</span>
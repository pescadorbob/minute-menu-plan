<li style="width:672px;display:${display ?: ''}">
    <span class="toolBoxes addIngredientBox" style="width:400px;">
        <span class="ingredent">
            <div style="float:left;">
              <!-- quantity -->
              <g:textField class="inpboxSmall showToolTip iAmount" name="itemQuantities"
                  value="${ingredientQuantity}" title="${g.message(code:'toolTip.prices.quantity')}" style="width:40px;"/>
              <!-- unit -->
              <input name="hiddenItemUnitNames" value="${hiddenItemUnitNames}"
                  class="inpbox showToolTip iUnit"
                  title="${g.message(code: 'toolTip.prices.unit')}" style="width:90px;">
              <input type="hidden" value="${ingredientUnitId}" name="itemUnitIds"/>
              <input type="hidden" name="hiddenItemUnitSymbols" value="${hiddenItemUnitSymbol}"/>
              <!-- ingredient -->
              <input class="inpbox showToolTip iProduct" name="hiddenItemProductNames"
                      value="${hiddenItemProductNames?.encodeAsHTML()}"
                      title="${g.message(code: 'toolTip.prices.ingredient')}" style="width:180px;"/>
              <input type="hidden" value="${hiddenItemProductNames}" name="itemProductIds"/>
              <!-- price -->
              <g:textField class="inpboxSmall showToolTip iAmount" name="itemPrices"
                    value="" title="${g.message(code: 'toolTip.prices.price')}" style="width:50px;"/>
            </div>
        </span>
    </span>
</li>
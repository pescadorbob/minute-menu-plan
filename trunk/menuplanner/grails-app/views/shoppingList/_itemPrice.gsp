<!-- in:
hiddenItemProductNames:product?.ingredient?.name,
  ingredientQuantity:StandardConversionService.getQuantityValueString(product?.quantity),
  hiddenItemUnitNames:product?.quantity?.unit,
  ingredientUnitId:product?.quantity?.unit?.id,
  hiddenItemUnitSymbol:product?.quantity?.unit?.symbol,
  product:product 
-->
<div class="receipt-row" >
    <span class="toolBoxes addIngredientBox" style="width:400px;">
        <span class="ingredent">
            <div style="float:left;">
              %{--<g:render controller="recipe" template="nutrientLink"--}%
                      %{--model="[ingredientFoodMapId:product.links[0]?.id,--}%
                      %{--hiddenIngredientFoodMapName:product.links[0]?.nutrition?.weightFor?.shrtDesc]"/>--}%
              <!-- predicted price -->
              %{--<g:textField class="inpboxSmall showToolTip iPredictedPrice" name="week${weekIndex}.predictedPrice"--}%
                  %{--value="${ingredientPredictedPrice}" title="${g.message(code:'toolTip.prices.predicted')}" style="width:40px;"/>--}%
              <!-- quantity -->
              <g:textField class="inpboxSmall showToolTip iAmount" name="itemQuantities"
                  value="${ingredientQuantity}" title="${g.message(code:'toolTip.prices.quantity')}" style="width:40px;"/>
              <!-- unit -->
              <input name="week${weekIndex}.hiddenItemUnitNames" value="${hiddenItemUnitNames}"
                  class="inpbox showToolTip iUnit"
                  title="${g.message(code: 'toolTip.prices.unit')}" style="width:90px;">
              <input type="hidden" value="${ingredientUnitId}" name="itemUnitIds"/>
              <input type="hidden" name="hiddenItemUnitSymbols" value="${hiddenItemUnitSymbol}"/>
              <!-- ingredient -->
              <input class="inpbox showToolTip iProduct" name="hiddenItemProductNames"
                      value="${hiddenItemProductNames?.encodeAsHTML()}"
                      title="${g.message(code: 'toolTip.prices.ingredient')}" style="width:180px;"/>
              <input type="hidden" value="${hiddenItemProductNames}" name="itemProductNames"/>
              <!-- price -->
              <g:textField class="inpboxSmall showToolTip iPrice" name="itemPrices"
                    value="" title="${g.message(code: 'toolTip.prices.price')}" style="width:50px;"/>
            </div>
        </span>
    </span>
</div>
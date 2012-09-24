<%@ page import="org.apache.commons.math.util.MathUtils" %>
<tr class="addGrocery">
    <td width="30px;" valign="top">
        <img class="btnCross" src="${resource(dir: 'images', file: 'crossImg.png')}" hspace="2" vspace="2"/>
    </td>
    %{--<td width="250" class="groceryText">${grocery}</td>--}%
    <td width="250" class="groceryTextBox">
  <div style="float:left;">
        <g:if test="${aisle}">
            <input class="inpbox showToolTip "
                  title="${g.message(code: 'toolTip.shopping.item.name')}"
                    type="text" name="week${weekIndex}.groceries.${aisle.id}" value="${grocery}"/>
        </g:if>
        <g:else>
            <input class="inpbox showToolTip "
                  title="${g.message(code: 'toolTip.shopping.item.name')}"
                    type="text" name="week${weekIndex}.groceries.0" value="${grocery}"/>
        </g:else>
    </div>

        <g:render template="/shoppingList/itemPrice" model="[hiddenItemProductNames:grocery?.ingredient?.name,
        ingredientPredictedPrice:grocery?.predictedPrice,
        ingredientQuantity:grocery?.quantity?.value?MathUtils.round(grocery?.quantity?.value,1):null,
        hiddenItemUnitNames:grocery?.quantity?.unit,
        ingredientUnitId:grocery?.quantity?.unit?.id,
        hiddenItemUnitSymbol:grocery?.quantity?.unit?.symbol
        ]"/>

    </td>
    %{--<td>--}%
        %{--<span class="linkEdit">Edit</span>--}%
    %{--</td>--}%
    <td></td>
</tr>

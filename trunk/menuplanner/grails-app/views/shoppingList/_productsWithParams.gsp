<%@ page import="com.mp.domain.StandardConversion" %>
<tr class="addProduct">
    <td width="30px;" valign="top">
        <img class="btnCross" src="${resource(dir: 'images', file: 'crossImg.png')}" hspace="2" vspace="2"/>
    </td>
    %{--<td width="250" class="groceryText">${product}</td>--}%
    <td width="250" class="groceryTextBox">
  <div style="float:left;">
        <g:if test="${aisle}">
            <input class="inpbox showToolTip iUnit"
                  title="${g.message(code: 'toolTip.shopping.item.name')}"
                    type="text" name="week${weekIndex}.products.${aisle.id}" value="${product}"/>
        </g:if>
        <g:else>
            <input class="inpbox showToolTip iUnit"
                  title="${g.message(code: 'toolTip.shopping.item.name')}" type="text" name="week${weekIndex}.products.0" value="${product}"/>
        </g:else>
    </div>
  <g:render template="/shoppingList/itemPrice" model="[hiddenItemProductNames:product?.ingredient?.name,
  ingredientQuantity:StandardConversion.getQuantityValueString(product?.quantity),
  hiddenItemUnitNames:product?.quantity?.unit,
  ingredientUnitId:product?.quantity?.unit?.id,
  hiddenItemUnitSymbol:product?.quantity?.unit?.symbol
  ]"/>
    </td>
    <td></td>
</tr>

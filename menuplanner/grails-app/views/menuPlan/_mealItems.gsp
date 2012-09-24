<%@ page import="com.mp.tools.CurrencyUtils; com.mp.domain.Quantity; com.mp.domain.Recipe; com.mp.domain.MealType" %>
<li>
    <ul>
        <li class="first">
            <g:if test="${params.action in ['printerFriendlyMenuPlan']}">
                ${alttext}
            </g:if>
            <g:else>
                <img src="${resource(dir: 'images', file: image)}"/>
            </g:else>
        </li>
        <g:each in="${week?.days}" var="day" status="index">
            <li  class="menuContainer placeMyHover" rel="${mealType}.week${weekIndex}.day${index}">
              <% def mealCost = day.meals.find {it.type == mealType}?.cost %>
            <g:each in="${day.getMealByType(mealType)}" var="mealItem">
                <div class="meal-item">
                    <input type="hidden" name="mealItems.${mealType}.week${weekIndex}.day${index}" value="${mealItem.id}"/>
                    <img src="${resource(dir: "images", file: "delete.jpg")}" alt="" align="left" style="display:none;" class="deleteImage">
                    <span>
                    <g:if test="${avePrice[mealItem]}">
                      <p class="item-ellipsis" >
                    </g:if>
                    <g:else >
                      <p class="item-ellipsis-full" >
                    </g:else>
                    <g:if test="${mealItem.instanceOf(Recipe.class)}">

                          <g:link controller="recipe" target="blank" action="show" id="${mealItem.id}">
                            ${mealItem}</g:link></p>
                          <g:if test="${avePrice[mealItem]}">
                        <p class='item-cost'>
                            ${CurrencyUtils.getRoundedAmount(avePrice[mealItem].price?.price,0)}
                            </p>
                          </g:if>
                        <g:if test="${params.action in ['printerFriendlyMenuPlan']}">
                        <span class="tiny">${Quantity.addTime(mealItem.preparationTime,mealItem.cookingTime).small}</span>
                        </g:if>
                        </span>
                    </g:if>
                    <g:else>
                          ${mealItem}</p>
                          <g:if test="${avePrice[mealItem]}">
                        <p class='item-cost'>
                            ${CurrencyUtils.getRoundedAmount(avePrice[mealItem]?.price,0)}
                            </p>
                          </g:if>
                        </span>
                    </g:else>
                </div>
            </g:each>
            <div class="day-total"><span class="day-total">$${CurrencyUtils.getRoundedAmount(mealCost?mealCost:0,0)}</span></div>
            <div class="farji" style="display:none; clear:both;"/>
          </li>
        </g:each>
    </ul>
</li>

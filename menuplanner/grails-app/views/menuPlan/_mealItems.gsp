<%@ page import="com.mp.domain.Recipe; com.mp.domain.MealType" %>
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
            <g:each in="${day.getMealByType(mealType)}" var="mealItem">
                <div>
                    <input type="hidden" name="mealItems.${mealType}.week${weekIndex}.day${index}" value="${mealItem.id}"/>
                    <img src="${resource(dir: "images", file: "delete.jpg")}" alt="" align="left" style="display:none;" class="deleteImage">
                    <g:if test="${mealItem.instanceOf(Recipe.class)}">
                        <span><g:link controller="recipe" target="blank" action="show" id="${mealItem.id}">${mealItem}</g:link></span>
                    </g:if>
                    <g:else>
                        <span>${mealItem}</span>
                    </g:else>
                </div>
            </g:each>
            <div class="farji" style="display:none; clear:both;"></div>
        </g:each>
    </li>
    </ul>
</li>

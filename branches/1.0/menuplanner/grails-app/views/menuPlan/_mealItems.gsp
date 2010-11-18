<%@ page import="com.mp.domain.MealType" %>
<li>
    <ul>
        <li class="first">
            <g:if test="${params.action in ['printerFriendlyMenuPlan']}">
                ${image.tokenize(".")[0]}
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
                    <img src="${resource(dir: "images", file: "delete.jpg")}" alt="" align="left" style="display:none;" class="deleteImage"><span>${mealItem}</span>
                </div>
            </g:each>
            <div class="farji" style="display:none; clear:both;"></div>
        </g:each>
    </li>
    </ul>
</li>

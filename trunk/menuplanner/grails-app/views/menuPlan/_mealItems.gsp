<%@ page import="com.mp.domain.MealType" %>
<li>
  <ul>
    <li class="first">
        <img src="${resource(dir: 'images', file: image)}"/>
    </li>
    <g:each in="${week?.days}" var="day" status="index">
      <li style="overflow: hidden;" class="menuContainer placeMyHover" rel="${mealType}.week${weekIndex}.day${index}">
      <g:each in="${day.getMealByType(mealType)}" var="mealItem">
        <div style="clear:both;">
            <input type="hidden" name="mealItems.${mealType}.week${weekIndex}.day${index}" value="${mealItem.id}" />
            <span style='float:left'>${mealItem}</span><img src="${resource(dir:"images",file:"delete.jpg")}" alt="" style="display:none;padding-left:2px;" class="deleteImage">
        </div>
      </g:each>
    </g:each>
      </li>
  </ul>
</li>

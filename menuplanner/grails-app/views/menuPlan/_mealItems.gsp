<%@ page import="com.mp.domain.MealType" %>
<li>
  <ul>
    <li class="first">
        <img src="${resource(dir: 'images', file: image)}"/>
    </li>
    <g:each in="${week.days}" var="day">
      <li class="menuContainer">
      <g:each in="${day.getMealByType(mealType)}" var="mealItem">
        <div style="clear:both;">
            <span style='float:left'>${mealItem}</span><img src="${resource(dir:"images",file:"delete.jpg")}" alt="" style="display:none;padding-left:2px;" class="deleteImage">
        </div>
      </g:each>
    </g:each>
      </li>
  </ul>
</li>

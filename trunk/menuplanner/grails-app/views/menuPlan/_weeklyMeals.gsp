<%@ page import="com.mp.domain.MealType" %>
<div class="week">
  <ul>
    <mp:mealItems week="${menuPlan.weeks[0]}" type="${MealType.DINNER}" image="week1.gif" weekIndex="0"/>
    <mp:mealItems week="${menuPlan.weeks[1]}" type="${MealType.DINNER}" image="week2.gif" weekIndex="1"/>
    <mp:mealItems week="${menuPlan.weeks[2]}" type="${MealType.DINNER}" image="week3.gif" weekIndex="2"/>
    <mp:mealItems week="${menuPlan.weeks[3]}" type="${MealType.DINNER}" image="week4.gif" weekIndex="3"/>

    <li class="divider"><img src="${resource(dir: 'images', file: 'divider.gif')}"/></li>
    <mp:mealItems week="${menuPlan.weeks[0]}" type="${MealType.BREAKFAST}" image="breakfast.gif" weekIndex="0"/>
    <mp:mealItems week="${menuPlan.weeks[0]}" type="${MealType.LUNCH}" image="lunch.gif" weekIndex="0"/>
  </ul>
</div>
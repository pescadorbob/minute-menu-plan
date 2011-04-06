<%@ page import="com.mp.domain.MealType" %>
<div class="week">
    <ul>
        <mp:mealItems alttext="1" week="${menuPlan.weeks[0]}" type="${MealType.DINNER}" image="week1.gif"/>
        <mp:mealItems alttext="2" week="${menuPlan.weeks[1]}" type="${MealType.DINNER}" image="week2.gif"/>
        <mp:mealItems alttext="3" week="${menuPlan.weeks[2]}" type="${MealType.DINNER}" image="week3.gif"/>
        <mp:mealItems alttext="4" week="${menuPlan.weeks[3]}" type="${MealType.DINNER}" image="week4.gif"/>

        <li class="divider">
            <img src="${resource(dir: 'images', file: 'divider.gif')}"/>
        </li>
        <mp:mealItems alttext="B" week="${menuPlan.weeks[0]}" type="${MealType.BREAKFAST}" image="breakfast.gif"/>
        <mp:mealItems alttext="L" week="${menuPlan.weeks[0]}" type="${MealType.LUNCH}" image="lunch.gif"/>
    </ul>
</div>
<%@ page import="com.mp.domain.MealType" %>
<div class="week">
    <ul>
        <mp:mealItems alttext="Week 1" week="${menuPlan.weeks[0]}" type="${MealType.DINNER}" image="week1.gif"/>
        <mp:mealItems alttext="Week 2" week="${menuPlan.weeks[1]}" type="${MealType.DINNER}" image="week2.gif"/>
        <mp:mealItems alttext="Week 3" week="${menuPlan.weeks[2]}" type="${MealType.DINNER}" image="week3.gif"/>
        <mp:mealItems alttext="Week 4" week="${menuPlan.weeks[3]}" type="${MealType.DINNER}" image="week4.gif"/>

        <li class="divider">
            <img src="${resource(dir: 'images', file: 'divider.gif')}"/>
        </li>
        <mp:mealItems alttext="Breakfast" week="${menuPlan.weeks[0]}" type="${MealType.BREAKFAST}" image="breakfast.gif"/>
        <mp:mealItems alttext="Lunch" week="${menuPlan.weeks[0]}" type="${MealType.LUNCH}" image="lunch.gif"/>
    </ul>
</div>
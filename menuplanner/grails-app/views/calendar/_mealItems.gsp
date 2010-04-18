<%@ page import="com.mp.domain.MealType" %>
<li>
    <ul>
        <li class="first"><img src="${resource(dir: 'images', file: image)}"/></li>
        <g:each in="${week.days}" var="day">
            <li>
            <g:each in="${day.getMealByType(mealType)}" var="mealItem">
                ${mealItem}<br/>
            </g:each>
        </g:each>
    </li>
    </ul>
</li>

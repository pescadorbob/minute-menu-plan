<%@ page import="com.mp.domain.MealType" %>
<html>
<head>
    <meta name="layout" content="menu"/>
    <title>Minute Menu Plan : Calendar</title>
</head>
<body>
<div id="content-wrapper" class="clearfix">
    <!--  start left-panel -->
    <div id="left-panel">
        <!--  start left-panel -->
        <div id="wintermenu">
            <div id="winterplan"><img src="${resource(dir: 'images', file: 'winter-menu-plan.gif')}"/></div>
            <div id="viewmenu">
                <ul>
                    <li>VIEW: <a href="menuPlan.gsp#">Monthly</a></li>
                    <li><a href="menuPlan.gsp#">Weekly</a></li>
                    <li class="noseprator"><a href="menuPlan.gsp#">Shopping List</a></li>
                    <li class="noseprator1"><img src="${resource(dir: 'images', file: 'actions.gif')}"/></li>
                </ul>
            </div>
        </div>
        <div id="daymenu">
            <ul>
                <li class="first">&nbsp;</li>
                <li>
                    <ul>
                        <li><a href="menuPlan.gsp#">Sunday</a></li>
                        <li><a href="menuPlan.gsp#">Monday</a></li>
                        <li><a href="menuPlan.gsp#">Tuesday</a></li>
                        <li><a href="menuPlan.gsp#">Wednesday</a></li>
                        <li><a href="menuPlan.gsp#">Thursday</a></li>
                        <li><a href="menuPlan.gsp#">Friday</a></li>
                        <li><a href="menuPlan.gsp#">Saturday</a></li>
                    </ul>
                </li>
            </ul>
        </div>
        <div id="countrymenu">
            <ul>
                <li class="first">&nbsp;</li>
                <li>
                    <ul>
                        <li><a href="#">Italian</a></li>
                        <li><a href="#">Mexican</a></li>
                        <li><a href="#">Poultry</a></li>
                        <li><a href="#">Beef</a></li>
                        <li><a href="#">Vegetarian</a></li>
                        <li><a href="#">Fish</a></li>
                        <li class="last"><a href="#">Quick</a></li>
                    </ul>
                </li>
            </ul>
        </div>
        <div class="week">
            <ul>
                <mp:mealItems week="${weeks[0]}" type="${MealType.DINNER}" image="week1.gif"/>
                <mp:mealItems week="${weeks[1]}" type="${MealType.DINNER}" image="week2.gif"/>
                <mp:mealItems week="${weeks[2]}" type="${MealType.DINNER}" image="week3.gif"/>
                <mp:mealItems week="${weeks[3]}" type="${MealType.DINNER}" image="week4.gif"/>

                <li class="divider"><img src="${resource(dir: 'images', file: 'divider.gif')}"/></li>
                <mp:mealItems week="${weeks[0]}" type="${MealType.BREAKFAST}" image="breakfast.gif"/>
                <mp:mealItems week="${weeks[0]}" type="${MealType.LUNCH}" image="lunch.gif"/>
            </ul>
        </div>
    </div>
    <!--  end left-panel start right-panel -->
    <g:render template="/menuPlan/search"/>
    <!--  end right-panel -->
</div>
</body>
</html>

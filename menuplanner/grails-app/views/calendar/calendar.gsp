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
                    <li>VIEW: <a href="#">Monthly</a></li>
                    <li><a href="#">Weekly</a></li>
                    <li class="noseprator"><a href="#">Shopping List</a></li>
                    <li class="noseprator1"><img src="${resource(dir: 'images', file: 'actions.gif')}"/></li>
                </ul>
            </div>
        </div>
        <div id="daymenu">
            <ul>
                <li class="first">&nbsp;</li>
                <li>
                    <ul>
                        <li><a href="#">Sunday</a></li>
                        <li><a href="#">Monday</a></li>
                        <li><a href="#">Tuesday</a></li>
                        <li><a href="#">Wednesday</a></li>
                        <li><a href="#">Thursday</a></li>
                        <li><a href="#">Friday</a></li>
                        <li><a href="#">Saturday</a></li>
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
                <g:render template="/calendar/mealItems" model="[image: 'week1.gif']"/>
                <g:render template="/calendar/mealItems" model="[image: 'week2.gif']"/>
                <g:render template="/calendar/mealItems" model="[image: 'week3.gif']"/>
                <g:render template="/calendar/mealItems" model="[image: 'week4.gif']"/>

                <li class="divider"><img src="${resource(dir: 'images', file: 'divider.gif')}"/></li>
                <g:render template="/calendar/mealItems" model="[image: 'breakfast.gif']"/>
                <g:render template="/calendar/mealItems" model="[image: 'lunch.gif']"/>

            </ul>
        </div>
    </div>
    <!--  end left-panel start right-panel -->
    <g:render template="/calendar/search"/>
    <!--  end right-panel -->
</div>
</body>
</html>

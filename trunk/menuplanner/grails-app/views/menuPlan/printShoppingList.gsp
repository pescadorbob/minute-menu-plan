<%@ page import="com.mp.domain.*" %>
<html>
<head>
    <meta name="layout" content="menu"/>
    <title>Shopping List</title>
</head>
<body>
<div id="container">
    <div id="wrapper" class="clearfix">
        <div id="content-wrapper" class="clearfix">
            <div class="top-shadow">
                <label>&nbsp;</label>
            </div>
            <div class="leftbox clearfix">
                <div id="shopping-list">
                    <h2>Shopping List of Parameters</h2>
                    <ul>
                        <g:uploadForm name="formShoppingList">
                            <li><label>Name of shopping list : </label>
                                <span><g:select class="" name="menuPlanId" from="${menuPlans}" optionKey="id" style="width:200px;"/></span></li>
                            <li><label>Servings : </label><span><input name="servings" type="text" class="inpboxSmall" value=""/></span></li>
                            <li><p><input name="weeks" type="checkbox" value="Week1"/> <span>Week1</span></p>
                                <p><input name="weeks" type="checkbox" value="Week2"/> <span>Week2</span></p>
                                <p><input name="weeks" type="checkbox" value="Week3"/> <span>Week3</span></p>
                                <p><input name="weeks" type="checkbox" value="Week4"/> <span>Week4</span></p></li>
                            <li style="text-align:center;">
                                <g:actionSubmit controller="menuPlan" action="detailShoppingList" value="Generate New List" class="button" style="width:150px;"/></li>
                        </g:uploadForm>
                    </ul>
                </div>
            </div>
            <div class="bottom-shadow">
                <label>&nbsp;</label>
            </div>
        </div>
    </div>
</div>
</body>
</html>

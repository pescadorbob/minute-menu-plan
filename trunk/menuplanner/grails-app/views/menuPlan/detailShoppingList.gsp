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
            <div class="headbox">
                <h3>${menuPlan?.name}</h3>
            </div>
            <div class="top-shadow">
                <label>&nbsp;</label>
            </div>
            <div class="leftbox clearfix">
                <div class="wintertop"><ul><li><strong>&nbsp;</strong></li><li><input name="" type="checkbox" value=""/> Export to Todo</li><li>
                    <img src="${resource(dir:'images',file:'printer.gif')}" alt="print" align="absmiddle"/> &nbsp; Print Shopping List
                </li></ul>
                </div>
                <g:each in="${weeks}" var="weekIndex">
                    %{--<mp:shoppingListPerWeek menuPlanId="${menuPlan?.id}" week="${week}"/>--}%
                    <g:render template="/menuPlan/shoppingListPerWeek" model="[menuPlan:menuPlan, weekIndex:weekIndex]"/>
                </g:each>
                <div class="winterButton">
                    <ul><li>
                        <input class="button" type="button" value="Create"/>
                        <input class="button" type="button" value="Save"/>
                        <input class="button" type="button" value="Cancel"/>
                    </li></ul>
                </div>
            </div>
            <div class="bottom-shadow">
                <label></label>
            </div>
        </div>
    </div>
</div>
</body>
</html>
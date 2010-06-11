<%@ page import="com.mp.domain.*" %>
<html>
<head>
    <meta name="layout" content="menu"/>
    <title>Show Shopping List</title>
</head>
<body>
<div id="container">
    <div id="wrapper" class="clearfix">
        <div id="content-wrapper" class="clearfix">
            <div class="headbox">
                <h3>${shoppingList.name}</h3>
            </div>
            <div class="top-shadow">
                <label>&nbsp;</label>
            </div>
            <div class="leftbox clearfix">
                <div class="wintertop"><ul><li><strong>&nbsp;</strong></li><li><input name="" type="checkbox" value=""/> Export to Todo</li><li>
                    <img src="${resource(dir: 'images', file: 'printer.gif')}" alt="print" align="absmiddle"/> &nbsp; Print Shopping List
                </li></ul>
                </div>


                <g:each in="${shoppingList.weeklyShoppingLists}" var="weeklyShoppingList">
                    <div class="winter-week clearfix" style="width:406px;">
                        <div class="winterButton"><strong>Week</strong><p></p></div>
                        <ul>
                            <g:each in="${weeklyShoppingList.week.days}" var="day">
                                <g:set var="items" value="${day.breakfast + day.lunch + day.dinner}"/>
                                <g:each in="${items}" var="item">
                                    <g:render template="shoppingListRecipe${item.instanceOf(Recipe)}" model="[item:item]"/>
                                </g:each>
                            </g:each>
                        </ul>
                    </div>
                    <div class="winterButton">
                        <ul>
                            <li class="grocery">
                                <p>sdgsdf</p>
                                <p>sdgsdf</p>
                                <p>sdgsdf</p>
                            </li>
                        </ul>
                    </div>
                </g:each>
                <div class="winterButton">
                    <ul><li>
                        <g:uploadForm name="formDetailShoppingList">
                            <g:actionSubmit class="button" controller="shoppingList" action="edit" name="edit" id="${shoppingList?.id}" value="Edit"/>
                        </g:uploadForm>
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
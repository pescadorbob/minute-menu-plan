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
            <div class="headbox"><h3>${shoppingListName}</h3></div>
            <div class="top-shadow">
                <label>&nbsp;</label>
            </div>
            <div class="leftbox clearfix">
                <table id="groceryTemplateRow" style="display:none;">
                    <g:render template="/shoppingList/grocery"/>                    
                </table>
                <g:uploadForm name="formDetailShoppingList">
                    <div class="wintertop"><ul><li><strong>&nbsp;</strong></li><li><input name="" type="checkbox" value=""/> Export to Todo</li><li>
                        <img src="${resource(dir: 'images', file: 'printer.gif')}" alt="print" align="absmiddle"/> &nbsp; Print Shopping List
                    </li></ul>
                    </div>
                    <g:each in="${weeks}" var="weekIndex" status="i">
                        <g:render template="/shoppingList/shoppingListPerWeek" model="[menuPlan:menuPlan, weekIndex:weekIndex, productListForWeek:productListForWeeks?.get(i), groceryListForWeek:groceryListForWeeks?.get(i)]"/>
                    </g:each>
                    <div class="winterButton">
                        <ul><li>
                            <g:hiddenField name="menuPlanId" value="${menuPlan?.id}"/> 
                            <g:hiddenField name="weekList" value="${weeks}"/>
                            <g:hiddenField name="servings" value="${servings}"/>
                            <g:hiddenField name="shoppingListName" value="${shoppingListName}"/>
                            <g:actionSubmit class="button" controller="shoppingList" action="create" name="create" value="Create"/>
                            <g:actionSubmit class="button" controller="shoppingList" action="cancelDetailShoppingList" name="create" value="Cancel"/>
                        </li></ul>
                    </div>
                </g:uploadForm>
            </div>
            <div class="bottom-shadow">
                <label></label>
            </div>
        </div>
    </div>
</div>
</body>
</html>
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
            <div class="headbox"><h3>${shoppingList.name}</h3></div>
            <div class="top-shadow">
                <label>&nbsp;</label>
            </div>
            <div class="leftbox clearfix">
                <table id="groceryTemplateRow" style="display:none;">
                    <g:render template="/shoppingList/grocery"/>
                </table>
                <g:form name="formDetailShoppingList">
                    <g:if test="${shoppingListId}">
                        %{--<g:hiddenField name="shoppingListId" value="${shoppingListId}"/>--}%
                        <input type="submit" class="button" name="Update" value="Update" onclick="$('#updateShoppingListButton').click();
                        return false;"/>
                        <g:actionSubmit class="button" controller="shoppingList" action="cancelDetailShoppingList" name="cancel" value="Cancel"/>
                    </g:if>
                    <g:else>
                        <input type="submit" class="button" name="create" value="Create" onclick="$('#createShoppingListButton').click();
                        return false;"/>
                        <g:actionSubmit class="button" controller="shoppingList" action="cancelDetailShoppingList" name="cancel" value="Cancel"/>
                    </g:else>

                    <g:each in="${shoppingList.weeklyShoppingLists}" var="weeklyShoppingList">
                        <g:render template="/shoppingList/weeklyShoppingList" model="[weeklyShoppingList: weeklyShoppingList]"/>
                    </g:each>
                    <div class="winterButton">
                        <ul><li>
                            <g:hiddenField name="menuPlanId" value="${shoppingList?.menuPlan?.id}"/>
                            <g:hiddenField name="weekList" value="${shoppingList?.weeklyShoppingLists*.weekIndex}"/>
                            <g:hiddenField name="servings" value="${shoppingList?.servings}"/>
                            <g:hiddenField name="shoppingListName" value="${shoppingList.name}"/>
                            <g:if test="${shoppingListId}">
                                <g:hiddenField name="shoppingListId" value="${shoppingListId}"/>
                                <g:actionSubmit class="button" controller="shoppingList" action="update" name="update" id="updateShoppingListButton" value="Update"/>
                            </g:if>
                            <g:else>
                                <g:actionSubmit class="button" controller="shoppingList" action="save" id="createShoppingListButton" name="save" value="Create"/>
                            </g:else>
                            <g:actionSubmit class="button" controller="shoppingList" action="cancelDetailShoppingList" name="cancel" value="Cancel"/>
                        </li></ul>
                    </div>
                </g:form>
            </div>
            <div class="bottom-shadow">
                <label></label>
            </div>
        </div>
    </div>
</div>
</body>
</html>
<%@ page import="com.mp.domain.*" %>
<html>
<head>
    <meta name="layout" content="menu"/>
    <title>Generate Shopping List</title>
</head>
<body>
<div id="container">
    <div id="wrapper" class="clearfix">
        <div id="content-wrapper" class="clearfix">
            <g:hasErrors bean="${pslCO}">
                <div id="errorsDiv" class="errors">
                    <g:renderErrors bean="${pslCO}"/>
                </div>
            </g:hasErrors>
            <div class="top-shadow">
                <label>&nbsp;</label>
            </div>
            <div class="leftbox clearfix">
                <div id="shopping-list">
                    <h2>Shopping List of Parameters</h2>
                    <ul>
                        <g:uploadForm name="formShoppingList">
                            <li><label>Name of shopping list :</label>
                                <span><g:textField class="inpbox ${hasErrors(bean:pslCO,field:'name', 'errors')}" name="name" style="width:184px;" value="${pslCO?.name}"/></span></li>
                            <li><label>Choose MenuPlan :</label>
                                <span><g:select class="inpbox" name="menuPlanId" from="${menuPlans}" optionKey="id" style="width:200px;" value="${pslCO?.menuPlanId}"/></span></li>
                            <li><label>Servings :</label><span><input name="servings" type="text" class="inpboxSmall ${hasErrors(bean: pslCO, field: 'servings', 'errors')}" value="${(pslCO) ? (pslCO?.servings) : (servings)}"/></span></li>
                            <li>
                                <p><g:checkBox name="weeks" value="0" checked="${('0' in pslCO.weeks)}"/> <span>Week1</span></p>
                                <p><g:checkBox name="weeks" value="1" checked="${('1' in pslCO.weeks)}"/> <span>Week2</span></p>
                                <p><g:checkBox name="weeks" value="2" checked="${('2' in pslCO.weeks)}"/> <span>Week3</span></p>
                                <p><g:checkBox name="weeks" value="3" checked="${('3' in pslCO.weeks)}"/> <span>Week4</span></p>
                            </li>
                            <li style="text-align:center;">
                                <g:if test="${!shoppingListId}">
                                    <g:actionSubmit controller="shoppingList" action="create" value="Generate New List" class="button" style="width:150px;"/>
                                </g:if>
                                <g:else>
                                    <g:hiddenField name="shoppingListId" value="${shoppingListId}"/>
                                    <g:actionSubmit controller="shoppingList" action="modifyShoppingList" value="Modify List" class="button" style="width:150px;"/>
                                </g:else>
                            </li>
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

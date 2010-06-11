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
            <g:hasErrors bean="${pslCO}">
                <div class="errors">
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
                            <li><label>Servings :</label><span><input name="servings" type="text" class="inpboxSmall ${hasErrors(bean: pslCO, field: 'servings', 'errors')}" value="${(pslCO)? (pslCO?.servings) : (servings)}"/></span></li>
                            <li><p><input name="weeks" type="checkbox" value="0" ${(pslCO?.weeks?.contains("0")) ? "checked=checked" : ""}/> <span>Week1</span></p>
                                <p><input name="weeks" type="checkbox" value="1" ${(pslCO?.weeks?.contains("1")) ? "checked=checked" : ""}/> <span>Week2</span></p>
                                <p><input name="weeks" type="checkbox" value="2" ${(pslCO?.weeks?.contains("2")) ? "checked=checked" : ""}/> <span>Week3</span></p>
                                <p><input name="weeks" type="checkbox" value="3" ${(pslCO?.weeks?.contains("3")) ? "checked=checked" : ""}/> <span>Week4</span></p></li>
                            <li style="text-align:center;">
                                <g:actionSubmit controller="shoppingList" action="detailShoppingList" value="Generate New List" class="button" style="width:150px;"/></li>
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

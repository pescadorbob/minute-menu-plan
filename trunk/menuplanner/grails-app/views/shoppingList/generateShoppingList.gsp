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
                    <h2>Generate Shopping List</h2>
                    <g:form name="formShoppingList">
                        <ul>
                            <li><label>Name of shopping list :</label>
                                <span><g:textField class="inpbox ${hasErrors(bean:pslCO,field:'name', 'errors')}" name="name" style="width:184px;" value="${pslCO?.name}"/></span>
                            </li>
                            <li><label>Choose MenuPlan :</label>
                                <span><g:select class="inpbox" name="menuPlanId" from="${menuPlans}" optionKey="id" style="width:200px;" value="${pslCO?.menuPlanId}"/></span>
                            </li>
                        </ul>
                        <div class="boxDiv">
                            <ul>
                                <li><g:radio name="shoppingList" id="listWithoutWeeks" class="listWithoutWeeksFT" value="GENERATE_WITHOUT_WEEKS" checked="${!pslCO?.isWeeklyShoppingList}"/>
                                    <g:message code="print.list.without.weeks"/><br/>
                                    <p style="padding:0 25px;"><g:message code="print.list.without.weeks.items"/></p>
                                </li>
                                <li><g:radio name="shoppingList" id="listWithWeeks" class="listWithWeeksFT" value="GENERATE_WITH_WEEKS" checked="${pslCO?.isWeeklyShoppingList}"/>
                                    <g:message code="print.list.with.weeks"/><br>
                                    <p style="padding:0 25px;"><g:message code="print.list.with.weeks.select"/></p>
                                </li>
                            </ul>
                        </div>
                        <ul style="padding:0 10px;">
                            <li>
                                <input name="" type="checkbox" value="" class="masterSelect"/>Select All
                            </li>
                            <li>
                                <p style="padding:0 10px;"><g:checkBox class="shoppingWeekFT_1 weekSelect" name="weeks" value="0" checked="${('0' in pslCO.weeks)}"/> <span>Week 1</span></p>
                                <p style="padding:0 10px;"><g:checkBox class="shoppingWeekFT_2 weekSelect" name="weeks" value="1" checked="${('1' in pslCO.weeks)}"/> <span>Week 2</span></p>
                                <p style="padding:0 10px;"><g:checkBox class="shoppingWeekFT_3 weekSelect" name="weeks" value="2" checked="${('2' in pslCO.weeks)}"/> <span>Week 3</span></p>
                                <p style="padding:0 10px;"><g:checkBox class="shoppingWeekFT_4 weekSelect" name="weeks" value="3" checked="${('3' in pslCO.weeks)}"/> <span>Week 4</span></p>
                            </li>
                        </ul>
                        <div id="btnDiv">
                            <ul>
                            <li>
                                <g:if test="${!shoppingListId}">
                                    <span><g:actionSubmit controller="shoppingList" action="createOriginal" value="Generate Shopping List - Original" class="button createOriginalListFT" style="width:250px;"/></span>
                                    &nbsp;&nbsp;<g:message code="generate.shopping.list.original"/>
                                    </li>
                                    <li>
                                    <span><g:actionSubmit controller="shoppingList" action="createScaled" value="Generate Shopping List - Scaled" class="button createScaledListFT" style="width:250px;"/></span>
                                    <g:message code="generate.shopping.list.scaled"/><br/>
                                    <input name="servings" id="servings" type="text" class="inpboxSmall ${hasErrors(bean: pslCO, field: 'servings', 'errors')}" value="${(pslCO?.servings) ? (pslCO?.servings) : 1}"/>
                                    &nbsp;&nbsp;<g:message code="generate.shopping.list.scaled.servings"/>
                                </g:if>
                                <g:else>
                                    <g:hiddenField name="shoppingListId" value="${shoppingListId}"/>
                                    <g:hiddenField name="isWeeklyList" value="${pslCO.isWeeklyShoppingList}"/>
                                    <span><g:actionSubmit controller="shoppingList" action="modifyShoppingListOriginal" value="Modify Shopping List - Original" class="button modifyShoppingListOriginalFT" style="width:250px;"/></span>
                                    &nbsp;&nbsp;<g:message code="generate.shopping.list.original"/>
                                    </li>
                                    <li>
                                    %{--<g:hiddenField name="isWeeklyList" value="${pslCO.isWeeklyShoppingList}"/>--}%
                                    <span><g:actionSubmit controller="shoppingList" action="modifyShoppingListScaled" value="Modify Shopping List - Scaled" class="button modifyShoppingListScaledFT" style="width:250px;"/></span>
                                    <g:message code="generate.shopping.list.scaled"/><br/>
                                    <input name="servings" type="text" class="inpboxSmall ${hasErrors(bean: pslCO, field: 'servings', 'errors')}" value="${(pslCO?.servings) ? (pslCO?.servings) : (servings)}"/>
                                    &nbsp;&nbsp;<g:message code="generate.shopping.list.scaled.servings"/>

                                </g:else>
                            </li>
                            </ul>
                        </div>
                    </g:form>
                </div>
            </div>
            <div class="bottom-shadow">
                <label>&nbsp;</label>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(".masterSelect").click(function() {
        if ($(this).attr("checked")) {
            $(".weekSelect").attr("checked", true);
        } else {
            $(".weekSelect").attr("checked", false);
        }
    });

    jQuery(document).ready(function() {
        if (jQuery('#listWithoutWeeks').attr('checked')) {
            $(".weekSelect").attr("checked", false);
        }
    });
</script>
</body>
</html>

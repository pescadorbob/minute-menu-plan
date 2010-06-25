<%@ page import="com.mp.domain.MealType" %>
<html>
<head>
    <meta name="layout" content="menu"/>
    <title>${menuPlan.name}</title>
    <rateable:resources/>
</head>
<style type="text/css">
.ui-sortable-placeholder {
    border: 1px dotted black;
    visibility: visible !important;
}

.ui-sortable-placeholder * {
    visibility: hidden;
}
</style>
<body>
<div id="content-wrapper" class="clearfix">
    <div id="left-panel">
        <g:render template="/menuPlan/actions"/>
        <g:render template="/quickFill/quickFills"/>
        <div class="week">
            <ul>
                <mp:mealItems week="${menuPlan.weeks[0]}" type="${MealType.DINNER}" image="week1.gif"/>
                <mp:mealItems week="${menuPlan.weeks[1]}" type="${MealType.DINNER}" image="week2.gif"/>
                <mp:mealItems week="${menuPlan.weeks[2]}" type="${MealType.DINNER}" image="week3.gif"/>
                <mp:mealItems week="${menuPlan.weeks[3]}" type="${MealType.DINNER}" image="week4.gif"/>

                <li class="divider">
                    <img src="${resource(dir: 'images', file: 'divider.gif')}"/>
                </li>
                <mp:mealItems week="${menuPlan.weeks[0]}" type="${MealType.BREAKFAST}" image="breakfast.gif"/>
                <mp:mealItems week="${menuPlan.weeks[0]}" type="${MealType.LUNCH}" image="lunch.gif"/>
            </ul>
        </div>
        <br/>
        <div id="button">
            <ul>
                <li><g:link action="edit" id="${menuPlan.id}" name="editMenuPlan">
                    <input type="button" class="button" value="Edit" id="edit"/></g:link>
                </li>
            </ul>
        </div>
    </div>
    <g:render template="/menuPlan/search" model='[categoryList:categoryList]'/>
</div>
<script type="text/javascript">
    $("#printMonthlyMenuPlanBtn").click(function() {
        window.open("${createLink(controller:'menuPlan',action:'printerFriendlyMenuPlan',id:menuPlan.id)}", 'printShoppingList', 'width=800,height=800,scrollbars=yes')
        return false;
    })
</script>
</body>
</html>

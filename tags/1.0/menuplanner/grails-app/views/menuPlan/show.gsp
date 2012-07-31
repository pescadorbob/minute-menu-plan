<%@ page import="com.mp.domain.MealType" %>
<html>
<head>
    <meta name="layout" content="menu"/>
    <title>${menuPlan.name}</title>
    <rateable:resources/>
    <p:css name='custom-ratings'/>
    <style type="text/css">
    .ui-sortable-placeholder {
        border: 1px dotted black;
        visibility: visible !important;
    }

    .ui-sortable-placeholder * {
        visibility: hidden;
    }
    .sharethis{
        color:#ffffff;
        }
    </style>

</head>
<body>
<div id="content-wrapper" class="clearfix">
    <div id="left-panel">
        <g:render template="/menuPlan/actions" model="[menuPlan:menuPlan]"/>
        <g:render template="/quickFill/quickFills"/>
        <g:render template="/menuPlan/menuPlanData" model="[menuPlan:menuPlan]"/>
        <br/>
        <div id="button">
            <ul>
                <li><g:link action="edit" class="editMenuPlanButtonFT" id="${menuPlan.id}" name="editMenuPlan">
                    <input type="button" class="button" value="Edit" id="edit"/></g:link>
                </li>
            </ul>
        </div>
    </div>
    <g:render template="/menuPlan/search" model='[categoryList:categoryList]'/>
</div>
<script type="text/javascript">
    $("#printMonthlyMenuPlanBtn").click(function() {
        window.open("${createLink(controller:'menuPlan',action:'printerFriendlyMenuPlan',id:menuPlan.id)}", 'printShoppingList', 'width=1100,height=600,left=100,top=100,scrollbars=yes')
        return false;
    })
    $("#printMenuPlanRecipesBtn").click(function() {
        window.location.replace("${createLink(controller:'recipe',action:'selectRecipesToPrint',id:menuPlan.id)}");
        return false;
    })
</script>
</body>
</html>
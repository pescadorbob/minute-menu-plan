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
                        <g:render template="/shoppingList/weeklyShoppingList" model="[weeklyShoppingList: weeklyShoppingList ,shoppingList:shoppingList]"/>
                    </g:each>
                    <div class="winterButton">
                        <ul><li>
                            <g:hiddenField name="menuPlanId" value="${shoppingList?.menuPlan?.id}"/>
                            <g:hiddenField name="weekList" value="${shoppingList?.weeklyShoppingLists*.weekIndex}"/>
                            <g:hiddenField name="servings" value="${shoppingList?.servings}"/>
                            <g:hiddenField name="shoppingListName" value="${shoppingList.name}"/>
                            <g:hiddenField name="isWeeklyShoppingList" value="${shoppingList?.isWeeklyShoppingList}"/>
                            <g:if test="${shoppingListId}">
                                <g:hiddenField name="shoppingListId" value="${shoppingListId}"/>
                                <g:actionSubmit class="button updateShoppingListButtonFT" controller="shoppingList" action="update" name="update" id="updateShoppingListButton" value="Update"/>
                            </g:if>
                            <g:else>
                                <g:actionSubmit class="button" controller="shoppingList" action="save" id="createShoppingListButton" name="save" value="Create"/>
                            </g:else>
                            <g:actionSubmit class="button" controller="shoppingList" action="cancelDetailShoppingList" name="cancel" value="Cancel"/>
                        </li></ul>
                    </div>
                </g:form>
                <script type="text/javascript">
                    var itemsJson = {
                        <g:each in="${Item.list()}" var="itemVar">
                        '${itemVar?.name?.replaceAll("'","\\\\'")}':['${itemVar?.suggestedAisle?.name?.replaceAll("'","\\\\'")}','${itemVar?.suggestedAisle?.id}'],
                        </g:each>
                    }

                    var metricUnits = []
                    <g:each in="${metricUnits}" var="metricUnit">
                    metricUnits.push(['${metricUnit}','${metricUnit.id}'])
                    </g:each>
                    //    metricUnits.push('Other...')

                    //    var unitPopupCaller;

                    function resetUnitAutocomplete() {
                        $(".iUnit").unautocomplete().autocomplete(metricUnits, {
                            matchContains: true,
                            minChars: 0,
                            max:0,
                            mustMatch:true
                        }).result(function(event, data, formatted) {
                                    var unitId = data[1];
                                    var currentUnit = jQuery(this).val()
                                    $(this).next().val(unitId);
                                    if (jQuery('#unitTable td:contains(' + currentUnit + ')')) {
                                        if (jQuery('#unitTable td:contains(' + currentUnit + ')').eq(0).text() == currentUnit) {
                                            var unitSymbol = jQuery('#unitTable td:contains(' + currentUnit + ')').eq(0).next().html()
                                            $(this).next().next().val(unitSymbol)
                                        }
                                    }
                                })
                    }
                    function resetIngredients() {

                        resetUnitAutocomplete()
                        $(".iProduct").unautocomplete().autocomplete("${createLink(action: 'getMatchingProducts', controller: 'recipe')}", {
                            width : 300,
                            minChars: 3,
                            selectFirst: false
                        }).result(function(event, data, formatted) {
                                    $(this).val(data[0]);
                                    $(this).next().val(data[1])
                                })
                        showNewLineOnLastFocus();
                        bindEventUpDownIngredientArrow()
                    }


                    $(function() {
                        resetIngredients();
                    })

                    $("#ingredientGrid>li:eq(1) input:visible[value='']").tooltip({events: {
                        input: "focus,blur"
                    },
                        effect:'slide'
                    }).dynamic({ bottom: { direction: 'down', bounce: true } })

                    $("#ingredientGrid>li:eq(1) input:visible[value='']").keydown(function() {
                        $(this).unbind('focus')
                        $(".tooltip").hide();
                    })

                </script>
            </div>
            <div class="bottom-shadow">
                <label></label>
            </div>
        </div>
    </div>
</div>
</body>
</html>
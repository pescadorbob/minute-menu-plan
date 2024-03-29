<%@ page import="com.mp.domain.Recipe" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta name="layout" content="menu"/>
    <title>Select Recipes to Print</title>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'recipe.css')}"/>
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
            <form name="printRecipeForm" onsubmit="window.open('', 'printRecipeWindow', 'width=800,height=800,scrollbars=yes')" action="${createLink(controller: 'recipe', action: 'printRecipes')}" method="post" target="printRecipeWindow">
                <input type="hidden" name="menuPlanId" value="${menuPlan.id}"/>
                <div class="leftbox clearfix">
                    <div id="selectedWeeksBox">
                        <div class="winterWeek">
                            Select the options to  use to print the recipes from your menu plan
                        </div>
                        <div class="winterWeek">
                            <g:checkBox name="customServings" value="0" checked="false"/> Scale recipes to
                            &nbsp;<input type="text" name="noOfServings" value="${noOfServings}" style="width:40px"> &nbsp;servings (Otherwise, recipes are printed using the original serving yield)
                        </div>

                        <div class="winterWeek">
                            <input name="printRecipe" type="radio" value="PRINT_SELECTED_WEEKS" checked=""/>
                            Print your recipes correlated to meals ( Great to use on a weekly basis, keeping all of your recipes at ready reference)
                        </div>
                        <div class="winterWeek">
                            <ul>
                                <li>
                                    <input name="fullWeek1" type="checkbox" value="1" checked="checked"/>
                                    Week 1</li>
                                <li>
                                    <input name="fullWeek2" type="checkbox" value="2" checked="checked"/>
                                    Week 2</li>
                                <li>
                                    <input name="fullWeek3" type="checkbox" value="3" checked="checked"/>
                                    Week 3</li>
                                <li>
                                    <input name="fullWeek4" type="checkbox" value="4" checked="checked"/>
                                    Week 4</li>
                            </ul>
                        </div>
                    </div>
                    <div id="printSelectedRecipesBox">
                        <div class="winterWeek">
                            <input name="printRecipe" type="radio" value="PRINT_SELECTED_RECIPES"/>
                            Print your recipes one recipe per page( Great for a recipes book)</div>
                        <div class="winterWeekbox ">
                            <ul>
                                <li class="clclearfix head">
                                    <ul>
                                        <li class="first_clumon">
                                            <input name="" type="checkbox" value="" class="masterSelect" id="weekSelect"/>
                                        </li>
                                        <li class="first_clumon">
                                            <input name="" type="checkbox" value="" class="masterSelect" id="allSelect"/>
                                        </li>
                                        <li>${menuPlan?.name}</li>

                                    </ul>
                                </li>
                                <g:each in="${menuPlan?.weeks}" var="week" status="weekIndex">
                                    <li class="oddEvenColor clclearfix">
                                        <ul>
                                            <li class="first_clumon">
                                                <input name="" type="checkbox" value="" class="weekSelect masterSelect" id="currentWeekSelect${weekIndex}"/>
                                            </li>
                                            <li class="first_clumon">
                                                <input name="" type="checkbox" value="" id="currentWeekRecipesSelect${weekIndex}" class="masterSelect weekSelect currentWeekSelect${weekIndex} allSelect"/>
                                            </li>
                                            <li class="weekby"><span class="toggleExpansion" id="toggleExpansion${weekIndex}" style="cursor:pointer;">-</span> Week ${weekIndex + 1}</li>
                                        </ul>
                                    </li>
                                    <g:each in="${week.recipes}" var="recipe">
                                        <li class="oddEvenColor clclearfix toggleExpansion${weekIndex}">
                                            <ul><li class="first_clumon">
                                            </li>  <li class="first_clumon">
                                                <input name="recipeIds" type="checkbox" value="${recipe.id}" class="currentWeekRecipesSelect${weekIndex} currentWeekSelect${weekIndex} weekSelect allSelect"/>
                                            </li>
                                                <li>${recipe?.name}</li>
                                            </ul>
                                        </li>
                                    </g:each>
                                </g:each>
                            </ul>
                        </div>
                    </div>       <div class="winterWeek">
                    <input type="submit" value="Print Recipes" onclick="return showRecipeServingsConfirmBox()"/>
                    <input id="backToMenuPlanLink" type="button" value="Back to Menu Plan"/>
                </div>
                </div>
            </form>
            <div class="bottom-shadow">
                <label>&nbsp;</label>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(".masterSelect").click(function() {
        if ($(this).attr("checked")) {
            $("." + $(this).attr("id")).attr("checked", true);
        } else {
            $("." + $(this).attr("id")).attr("checked", false);
        }
    });

    $(".toggleExpansion").toggle(function() {
        $("." + $(this).attr("id")).slideUp();
        $(this).text("+");
    }, function() {
        $("." + $(this).attr("id")).slideDown();
        $(this).text("-");
    });
    jQuery('#printSelectedRecipesBox input').click(function() {
        if (jQuery(this).is(':checked')) {
            jQuery('#selectedWeeksBox input').filter(':checked').removeAttr('checked');
            jQuery('#printSelectedRecipesBox :radio').attr('checked', 'checked');
        }
    });

    jQuery('#selectedWeeksBox input').click(function() {
        if (jQuery(this).is(':checked')) {
            jQuery('#printSelectedRecipesBox input').filter(':checked').removeAttr('checked');
            jQuery('#selectedWeeksBox :radio').attr('checked', 'checked');
        }
    })
    $(".oddEvenColor:even").addClass("alternatecolor");
    $("#backToMenuPlanLink").click(function() {
        window.location.replace("${createLink(controller:'menuPlan',action:'show',id:menuPlan.id)}");
        return false;
    })

    function showRecipeServingsConfirmBox() {
        if (jQuery('#customServings').attr('checked')) {
            return confirm('<g:message code="print.scaled.recipes.warning"/>');
        } else {
            return true;
        }
    }
</script>
</body>
</html>

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
                <h3>Winter</h3>
            </div>
            <div class="top-shadow">
                <label>&nbsp;</label>
            </div>
            <div class="leftbox clearfix">
                <div class="winterWeek">
                    <input name="" type="radio" value="" checked=""/>
                    Print your recipes correlated to meals ( Great to use on a weekly basis, keeping all of your recipes at ready reference)</div>
                <div class="winterWeek">
                    <ul>
                        <li>
                            <input name="" type="checkbox" value=""/>
                            Week 1</li>
                        <li>
                            <input name="" type="checkbox" value=""/>
                            Week 2</li>
                        <li>
                            <input name="" type="checkbox" value=""/>
                            Week 3</li>
                        <li>
                            <input name="" type="checkbox" value=""/>
                            Week 4</li>
                    </ul>
                </div>
                <div class="winterWeek">
                    <input name="" type="radio" value=""/>
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
                            <li class="alternatecolor clclearfix">
                                <ul>
                                    <li class="first_clumon">
                                        <input name="" type="checkbox" value="" class="weekSelect masterSelect" id="currentWeekSelect${weekIndex}"/>
                                    </li>
                                    <li class="first_clumon">
                                        <input name="" type="checkbox" value="" id="currentWeekRecipesSelect${weekIndex}" class="masterSelect weekSelect currentWeekSelect${weekIndex} allSelect"/>
                                    </li>
                                    <li class="weekby"><span class="toggleExpansion" id="toggleExpansion${weekIndex}">-</span> Week ${weekIndex + 1}</li>
                                </ul>
                            </li>
                            <g:each in="${week?.days}" var="day">
                                <g:set var="items" value="${day.breakfast+ day.lunch+day.dinner}"/>
                                <g:each in="${(items as Set)}" var="item">
                                    <g:if test="${item?.instanceOf(Recipe)}">
                                        <li class="clclearfix toggleExpansion${weekIndex}">
                                            <ul><li class="first_clumon">
                                            </li>  <li class="first_clumon">
                                                <input name="" type="checkbox" value="" class="currentWeekRecipesSelect${weekIndex} currentWeekSelect${weekIndex} weekSelect allSelect"/>
                                            </li>
                                                <li>${item?.name} ${item?.id}</li>

                                            </ul>
                                        </li>
                                    </g:if>
                                </g:each>
                            </g:each>
                        </g:each>
                        <li class="alternatecolor clclearfix">
                            <ul>
                                <li class="first_clumon">
                                    <input name="" type="checkbox" value=""/>
                                </li>
                                <li class="first_clumon">
                                    <input name="" type="checkbox" value=""/>
                                </li>
                                <li class="weekby">+ Week 1</li>
                            </ul>
                        </li>
                        <li class="clclearfix">
                            <ul><li class="first_clumon">
                                <input name="" type="checkbox" value=""/>
                            </li>  <li class="first_clumon">
                                <input name="" type="checkbox" value=""/>
                            </li>
                                <li>Eggs</li>

                            </ul>
                        </li>
                        <li class="alternatecolor clclearfix">
                            <ul><li class="first_clumon">
                                <input name="" type="checkbox" value=""/>
                            </li>  <li class="first_clumon">
                                <input name="" type="checkbox" value=""/>
                            </li>
                                <li>Eggs</li>
                            </ul>
                        </li>
                        <li class="clclearfix">
                            <ul><li class="first_clumon">
                                <input name="" type="checkbox" value=""/>
                            </li>  <li class="first_clumon">
                                <input name="" type="checkbox" value=""/>
                            </li>
                                <li>Eggs</li>

                            </ul>
                        </li>
                        <li class="alternatecolor clclearfix">
                            <ul><li class="first_clumon">
                                <input name="" type="checkbox" value=""/>
                            </li>  <li class="first_clumon">
                                <input name="" type="checkbox" value=""/>
                            </li>
                                <li>Eggs</li>
                            </ul>
                        </li>
                        <li class="clclearfix">
                            <ul><li class="first_clumon">
                                <input name="" type="checkbox" value=""/>
                            </li>  <li class="first_clumon">
                                <input name="" type="checkbox" value=""/>
                            </li>
                                <li>Eggs</li>

                            </ul>
                        </li>
                        <li class="alternatecolor clclearfix">
                            <ul><li class="first_clumon">
                                <input name="" type="checkbox" value=""/>
                            </li>  <li class="first_clumon">
                                <input name="" type="checkbox" value=""/>
                            </li>
                                <li>Eggs</li>
                            </ul>
                        </li>
                        <li class="clclearfix">
                            <ul><li class="first_clumon">
                                <input name="" type="checkbox" value=""/>
                            </li>  <li class="first_clumon">
                                <input name="" type="checkbox" value=""/>
                            </li>
                                <li>Eggs</li>

                            </ul>
                        </li>
                        <li class="alternatecolor clclearfix">
                            <ul><li class="first_clumon">
                                <input name="" type="checkbox" value=""/>
                            </li>  <li class="first_clumon">
                                <input name="" type="checkbox" value=""/>
                            </li>
                                <li>Eggs</li>
                            </ul>
                        </li>
                    </ul>
                </div>
                <div class="winterWeek"><input type="button" value="Print Recipes"/>
                    <input type="button" value="Back to Menu Plan"/>
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
        if ($(this).attr("checked") == true) {
            $("." + $(this).attr("id")).attr("checked", true)
        } else {
            $("." + $(this).attr("id")).attr("checked", false)
        }
    })

    $(".toggleExpansion").toggle(function() {
        $("." + $(this).attr("id")).slideUp()
        $(this).text("+")
    }, function() {
        $("." + $(this).attr("id")).slideDown()
        $(this).text("-")
    })
</script>
</body>
</html>

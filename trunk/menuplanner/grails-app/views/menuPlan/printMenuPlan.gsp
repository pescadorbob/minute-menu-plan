<%@ page import="com.mp.domain.MealType" %>
<html>
<head>
    <meta name="layout" content="printRecipelayout"/>
    <title>${menuPlan.name}</title>
    <style type="text/css">
    body {
        font-family: "Times New Roman", Times, serif;
        font-size: 16px;
        color: #000000;
    }

    .ui-sortable-placeholder {
        border: 1px dotted black;
        visibility: visible !important;
    }

    .ui-sortable-placeholder * {
        visibility: hidden;
    }

    .week {
        font-size: 11px;
        font-weight: bold;
    }

    .week ul li {
        margin: 2px 0px;
        clear: both;
    }

    .week ul li.divider {
        margin: 0px 0px 0px 0px;
        clear: both;
    }

    .week ul li ul {
        padding: 0px;
        margin: 0px;
        list-style: none;
    }

    .week ul li ul li {
        float: left;
        border: #d5d4d4 1px solid;
        width: 120px;
        margin-left: 4px;
        padding: 6px 6px 6px 0px;
        clear: none;
        color: #000
    }

    .week ul li ul li.first {
        float: left;
        border: none;
        background: #b1bdc1;
        width: 60px;
        text-align: center;
        color: #000;
    }

    .week ul li ul li div {
        position: relative;
        padding-left: 4px;
        clear: both;
        color: #000;
    }

    #daymenu {
        clear: both;
        margin-top: 5px;
        font-weight: bold;
        color: #000;
        background: url(images/day-menu-second.gif) repeat-x top left;
        line-height: 26px;
    }

    #daymenu ul li.first {
        width: 70px;
        float: left;
        background: url(images/day-menu-first.gif) repeat-x top left;
        border-right: 1px solid #fff;
    }

    #daymenu ul li {
        float: left;
    }

    #daymenu ul li li {
        float: left;
        padding: 0px;
        width: 125px;
        color: #000;
        margin-left: 4px;
        text-align: center;
    }

    #daymenu ul li a {
        color: #000;
        text-decoration: none;
    }

    #daymenu ul li li:last-child {
        float: left;
    }

    #countrymenu {
        display: none;
    }

    #button {
        display: none;
    }
    </style>
    <script type="text/javascript">
        $(function() {
            var earyHeight = $.each(jQuery('.week  ul> li > ul'), function(i) {
                $(this).addClass('clearfix')
                var ulH = $(this).height()
                $(this).children().each(function() {
                    $(this).css('height', ulH + 'px')
                })
            })
        })
    </script>
</head>

<body>
<div id="wrapper" class="clearfix">
    <div id="content-wrapper" class="clearfix">
        <div id="wintermenu">
            <div id="winterplan">
                ${menuPlan?.name}
            </div>
        </div>
        <g:render template="/quickFill/quickFills"/>
        <g:render template="/menuPlan/menuPlanData" model="[menuPlan:menuPlan]"/>
    </div>
</div>
</body>

</html>

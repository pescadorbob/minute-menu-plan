<%@ page import="com.mp.domain.MealType" %>
<html>
<head>
    <meta name="layout" content="printRecipelayout"/>
    <title>${menuPlan.name}</title>
    <style type="text/css">
    body {
        font-size: 16px;
        color: #000000;
    }

    .ui-sortable-placeholder {
        border: 1px dotted black;
        visibility: visible !important;
    }
    a, a:active, a:hover, a:visited {
      text-decoration:none;
      color: #000;
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
        width: 125px;
        margin-left: 4px;
        padding: 6px 6px 6px 0px;
        clear: none;
        color: #000
    }

    .week ul li ul li.first {
        float: left;
        border: none;
        background: #b1bdc1;
        width: 10px;
        text-align: center;
        color: #000;
        font-size: 20px !important;
      padding: 6px 6px 6px 3px;
        display: block;
    }

    .week ul li ul li div {
        position: relative;
        padding-left: 4px;
        clear: both;
        color: #000;
        font-size: 16px !important;
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
        width: 10px;
        float: left;
        background: url(images/day-menu-first.gif) repeat-x top left;
        border-right: 1px solid #fff;
          margin-left: 4px;
    padding: 6px 6px 6px 0;
    }

    #daymenu ul li {
        float: left;
    }
    .tiny {
      font-size:8px;
    }
    #daymenu ul li li {
        float: left;
        width: 125px;
        font-size: 18px;
        color: #000;
        margin-left: 4px;
        text-align: center;
      padding: 6px 6px 6px 0;
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
<script type="text/javascript">
    window.onload = print()
</script>
</body>

</html>

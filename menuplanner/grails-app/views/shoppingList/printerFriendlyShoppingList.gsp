<%@ page import="com.mp.domain.*" %>
<html>
<head>
    <meta name="layout" content="printRecipelayout"/>
    <title>Show Shopping List</title>
    <style type="text/css">
    body {
        color: #000000;
        background: #ffffff;
        font-family: "Times New Roman", Times, serif;
        font-size: 12pt;
    }

    a {
        text-decoration: underline;
        color: #0000ff;
    }
    </style>
</head>
<body>
<div id="container">
    <div id="wrapper" class="clearfix">
        <div id="content-wrapper" class="clearfix">
            <div class="headbox" style="background-color:#2AA4B1">
                <h3>Shopping List for: ${shoppingList?.name}</h3>
            </div>
            <table cellpadding="2" cellspacing="3" style="padding-left:40px;">
                <g:each in="${shoppingList?.weeklyShoppingLists}" var="weeklyShoppingList" status="outer_i">
                    <tr><td colspan="2">
                        <strong>Week ${weeklyShoppingList?.weekIndex + 1}</strong>
                    </td></tr>
                    <g:each in="${weeklyShoppingList?.products}" var="product" status="i">
                        <tr>
                            <td><input type="checkbox" name="shoppingItem"></td>
                            <td>${product}</td></tr>
                    </g:each>
                    <g:each in="${weeklyShoppingList?.groceries}" var="item" status="j">
                        <tr>
                            <td><input type="checkbox" name="shoppingItem"></td>
                            <td>${item}</td>
                        </tr>
                    </g:each>
                </g:each>
            </table>
        </div>
    </div>
</div>
<script type="text/javascript">
    window.onload = print()
</script>
</body>
</html>
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
            <g:render template="/shoppingList/showShoppingListData" model="[shoppingList: shoppingList]"/>
        </div>
    </div>
</div>
<script type="text/javascript">
    window.onload = print()
</script>
</body>
</html>
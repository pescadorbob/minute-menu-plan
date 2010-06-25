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
        font-size: 16px;
    }

    strong, h1, h2, h3, h4, h5, h6, form, p, ul {
        color: #000000 !important;
    }

    .alternate {
        background: #fff;
        clear: both;
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
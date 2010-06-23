<%@ page import="com.mp.domain.*" %>
<html>
<head>
    <meta name="layout" content="printRecipelayout"/>
    <title>Show Shopping List</title>
</head>
<body>
<div id="container">
    <div id="wrapper" class="clearfix">
        <div id="content-wrapper" class="clearfix">
            <div class="headbox">
                <h3>Shopping List for: ${shoppingList?.name}</h3>
            </div>
            <g:render template="/shoppingList/showShoppingListData" model="[shoppingList: shoppingList]"/>
        </div>
    </div>
</div>
</body>
</html>
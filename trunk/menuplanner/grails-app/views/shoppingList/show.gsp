<%@ page import="com.mp.domain.*" %>
<html>
<head>
    <meta name="layout" content="menu"/>
    <title>Show Shopping List</title>
    <style>.winterButton ul {
        margin-bottom: 10px;
    }

    .productCheckBox {
        display: none;
    }
    </style>
</head>
<body>
<div id="container">
    <div id="wrapper" class="clearfix">
        <div id="content-wrapper" class="clearfix">
            <div class="headbox">
                <h3>${shoppingList?.name}</h3>
            </div>
            <div class="top-shadow">
                <label>&nbsp;</label>
            </div>
            <div class="leftbox clearfix">
                <div class="wintertop">
                    <ul>
                        <li><strong>&nbsp;</strong></li>
                        <li id="emailShoppingListBtn" style="cursor:pointer">
                            <img src="${resource(dir: 'images', file: 'emailMe.jpeg')}" alt="" align="absmiddle" style="width:29px;height:23px">
                            Email the Shopping List
                        </li>
                        <li style="cursor:pointer" id="printShoppingListBtn">
                            <img src="${resource(dir: 'images', file: 'printer.gif')}" alt="print" align="absmiddle"/> &nbsp; Print Shopping List
                        </li>
                    </ul>
                </div>
                <g:render template="/shoppingList/showShoppingListData" model="[shoppingList: shoppingList]"/>
                <div class="winterButton">
                    <ul><li>
                        <g:uploadForm name="formDetailShoppingList">
                            <g:hiddenField name="shoppingListId" value="${shoppingList?.id}"/>
                            <g:actionSubmit class="button" controller="shoppingList" action="edit" name="edit" value="Edit"/>
                        </g:uploadForm>
                    </li></ul>
                </div>
            </div>
            <div class="bottom-shadow">
                <label></label>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $("#printShoppingListBtn").click(function() {
        window.open("${createLink(controller:'shoppingList',action:'printerFriendlyShoppingList',id:shoppingList.id)}", 'printShoppingList', 'width=800,height=800,scrollbars=yes')
        return false;
    })

    $(function() {
        $("#emailShoppingListBtn").click(function() {
            $.get("${createLink(controller:'shoppingList',action:'emailShoppingList',id:shoppingList.id)}", function(data) {
                alert("An email has been sent to your email id.");
            })
        })
    })
</script>
</body>
</html>
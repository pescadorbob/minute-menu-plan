<%@ page import="com.mp.domain.*" %>
<html>
<head>
    <meta name="layout" content="menu"/>
    <title>Show Shopping List</title>
    <style>
    .winterButton ul {
        margin-bottom: 10px;
    }
    .wintertop ul li {
        width: 24%;
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
                            <p:image src='emailMe.jpeg' alt="" align="absmiddle" style="width:29px;height:23px"/>
                            Email the Shopping List
                        </li>
                        <li style="cursor:pointer" id="printShoppingListBtn">
                            <p:image src='printer.gif' alt="print" align="absmiddle"/> &nbsp; Print Shopping List
                        </li>
                        <li style="cursor:pointer" id="shareThisUrl">
                            <g:if test="${(party?.subAffiliate)}">
                                <mp:shareThis coachId="${party?.uniqueId}" shareUrl="${createLink(controller:'shoppingList',action:'show',id:shoppingList.id,absolute:true)}"/>
                            </g:if>
                            <g:else>
                                <mp:shareThis shareUrl="${createLink(controller:'shoppingList',action:'show',id:shoppingList.id,absolute:true)}"/>
                            </g:else>
                        </li>
                    </ul>
                </div>
                <div class="emailShoppingList" id="emailShoppingList" style="display:none">
                    <g:formRemote name="emailListForm" url="${[action:'emailShoppingList']}">
                        Send To: <g:textField name="emailId"/>
                        <g:hiddenField name="shoppingListId" value="${shoppingList?.id}"/>
                        <g:submitButton name="submit" value="send" id="emailSubmitButton" onClick="attachPaginationLinkEvents()"/>
                    </g:formRemote>
                </div>
            <div class="winterButton">
            <ul><li>
                <g:form name="formDetailShoppingList">
                    <g:actionSubmit class="button" controller="shoppingList" action="edit" name="edit" value="Edit"/>
                    </li></ul>
                </div>
                    <g:render template="/shoppingList/showShoppingListData" model="[shoppingList: shoppingList]"/>
                    <div class="winterButton">
                    <ul><li>

                    <g:hiddenField name="shoppingListId" value="${shoppingList?.id}"/>
                    <g:actionSubmit class="button editShoppingListButtonFT" controller="shoppingList" action="edit" name="edit" value="Edit"/>
                </g:form>
            </li></ul>
            </div>
            </div>
            <div id="emailMessageTemplate">
                <g:render template="/shoppingList/ajaxEmailSendingMessage"/>
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
            jQuery('#emailShoppingList').show()
        })
    })
</script>
</body>
</html>
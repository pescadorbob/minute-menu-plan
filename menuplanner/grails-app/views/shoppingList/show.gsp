<%@ page import="com.mp.tools.UserTools; com.mp.domain.*" %>
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
            <g:if test="${flash.message}">
                <div id="flashMsgTst" class="flashMessage">
                    ${flash.message}
                </div>
            </g:if>
            <g:if test="${listErrors && listErrors.size()>0}">
                <div id="errorsDiv" class="errors">
                    <ul>
                        <g:each in="${listErrors}" var="error" status="i">
                            <li>${error}</li>
                        </g:each>
                    </ul>
                </div>
            </g:if>
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
                          <a href="javascript:void(0);" onclick="printList('med')"><p:image src='printer.gif' alt="print" align="absmiddle"/></a> 
                          <a href="javascript:void(0);" onclick="printList('small')">small</a>
                          <a href="javascript:void(0);" onclick="printList('med')">med</a>
                          <a href="javascript:void(0);" onclick="printList('large')">large</a>
                        </li>
                        <li style="cursor:pointer" id="shareThisUrl">
                            <g:if test="${(party?.coach)}">
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
                    <g:if test="${UserTools.currentUser && ((UserTools.currentUser.party.roles*.type in [PartyRoleType.Admin, PartyRoleType.SuperAdmin])|| (UserTools.currentUser.party == shoppingList.party))}">
                    <g:actionSubmit class="button" controller="shoppingList" action="edit" name="edit" value="Edit"/>
                    <g:actionSubmit class="button" controller="shoppingList" action="delete" name="delete" value="Delete" onclick="return confirm('Are you sure?');"/>
                        </g:if>
                    </li></ul>
                </div>
                    <g:render template="/shoppingList/showShoppingListData" model="[shoppingList: shoppingList]"/>
                    <div class="winterButton">
                    <ul><li>

                    <g:hiddenField name="shoppingListId" value="${shoppingList?.id}"/>
                    <g:if test="${UserTools.currentUser && ((UserTools.currentUser.party.roles*.type in [PartyRoleType.Admin, PartyRoleType.SuperAdmin])|| (UserTools.currentUser.party == shoppingList.party))}">
                    <g:actionSubmit class="button editShoppingListButtonFT" controller="shoppingList" action="edit" name="edit" value="Edit"/>
                    <g:actionSubmit class="button" controller="shoppingList" action="delete" name="delete" value="Delete" onclick="return confirm('Are you sure?');"/>
                    </g:if>
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
    document.printList = function(size) {
        if(size == 'small'){
          window.open("${createLink(controller:'shoppingList',action:'printerFriendlyShoppingList',id:shoppingList.id)}?size=small", 'printShoppingList', 'width=800,height=800,scrollbars=yes')
        } else if(size == 'med'){
          window.open("${createLink(controller:'shoppingList',action:'printerFriendlyShoppingList',size:'med',id:shoppingList.id)}?size=med", 'printShoppingList', 'width=800,height=800,scrollbars=yes')
        } else if(size=='large'){
          window.open("${createLink(controller:'shoppingList',action:'printerFriendlyShoppingList',size:'large',id:shoppingList.id)}?size=large", 'printShoppingList', 'width=800,height=800,scrollbars=yes')
        }
        return false;
    };
    $(function() {
        $("#emailShoppingListBtn").click(function() {
            jQuery('#emailShoppingList').show()
        })
    })
</script>
</body>
</html>

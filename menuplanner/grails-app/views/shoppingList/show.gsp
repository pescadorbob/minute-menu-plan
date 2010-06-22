<%@ page import="com.mp.domain.*" %>
<html>
<head>
    <meta name="layout" content="menu"/>
    <title>Show Shopping List</title>
    <style>.winterButton ul{margin-bottom:10px;}</style>
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
                <g:each in="${shoppingList?.weeklyShoppingLists}" var="weeklyShoppingList">
                    <div class="winterButton" style="float:left;"><h3>Week ${weeklyShoppingList?.weekIndex + 1}</h3><p></p></div>
                    <g:each in="${weeklyShoppingList.aisles + null}" var="aisle">
                        <g:if test="${aisle}">
                            <div class="winter-week clearfix" style="width:406px;">
                                <strong>${(aisle ? aisle : 'Others')}</strong>
                                <ul>
                                    <li class="grocery">
                                        <g:each in="${weeklyShoppingList?.getProductsByAisle(aisle)}" var="item">
                                            <p>${item}</p>
                                        </g:each>
                                    </li>
                                </ul>
                            </div>
                            <div class="winterButton">
                                <ul>
                                    <li class="grocery">
                                        <g:each in="${weeklyShoppingList?.getGroceriesByAisle(aisle)}" var="item">
                                            <p>${item}</p>
                                        </g:each>
                                    </li>
                                </ul>
                            </div>
                        </g:if>
                        <g:else>
                            <g:if test="${(weeklyShoppingList?.getProductsByAisle() || weeklyShoppingList?.getGroceriesByAisle())}">
                                <div class="winter-week clearfix" style="width:406px;">
                                    <strong>&nbsp;&nbsp;&nbsp;&nbsp;${(aisle ? aisle : 'Others')}</strong>
                                    <ul>
                                        <li class="grocery">
                                            <g:each in="${weeklyShoppingList?.getProductsByAisle(aisle)}" var="item">
                                                <p>${item}</p>
                                            </g:each>
                                        </li>
                                    </ul>
                                </div>
                                <div class="winterButton">
                                    <ul>
                                        <li class="grocery">
                                            <g:each in="${weeklyShoppingList?.getGroceriesByAisle(aisle)}" var="item">
                                                <p>${item}</p>
                                            </g:each>
                                        </li>
                                    </ul>
                                </div>
                            </g:if>
                        </g:else>
                    </g:each>
                </g:each>
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
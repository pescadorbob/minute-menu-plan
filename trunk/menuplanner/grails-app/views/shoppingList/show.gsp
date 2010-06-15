<%@ page import="com.mp.domain.*" %>
<html>
<head>
    <meta name="layout" content="menu"/>
    <title>Show Shopping List</title>
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
                <div class="wintertop"><ul><li><strong>&nbsp;</strong></li><li><input name="" type="checkbox" value=""/> Export to Todo</li><li>
                    <img src="${resource(dir: 'images', file: 'printer.gif')}" alt="print" align="absmiddle"/> &nbsp; Print Shopping List
                </li></ul>
                </div>
                <g:each in="${shoppingList?.weeklyShoppingLists}" var="weeklyShoppingList" status="outer_i">
                    <div class="winter-week clearfix" style="width:406px;">
                        <div class="winterButton"><strong>Week ${weeklyShoppingList?.weekIndex + 1}</strong><p></p></div>
                        <ul>
                            <g:each in="${weeklyShoppingList?.products}" var="product" status="i">
                                <li class="${(i % 2 == 1) ? 'alternatecolor' : ''}">
                                    <ul>
                                        <li class="first_clumon">
                                            <input name="" type="checkbox" value=""/>
                                        </li>
                                        <li class="email">${product?.item}</li>
                                        <li>${product?.quantity}
                                        </li>
                                    </ul>
                                </li>
                            </g:each>
                        </ul>
                    </div>
                    <div class="winterButton">
                        <ul>
                            <li class="grocery">
                                <g:each in="${weeklyShoppingList?.groceries}" var="item">
                                    <p>${item}</p>
                                </g:each>
                            </li>
                        </ul>
                    </div>
                </g:each>
                <div class="winterButton">
                    <ul><li>
                        <g:uploadForm name="formDetailShoppingList">
                            <g:actionSubmit class="button" controller="shoppingList" action="edit" name="edit" id="${shoppingList?.id}" value="Edit"/>
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
</body>
</html>
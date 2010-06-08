<%@ page import="com.mp.domain.*" %>
<html>
<head>
    <meta name="layout" content="menu"/>
    <title>Shopping List</title>
</head>
<body>
<div id="container">
    <div id="wrapper" class="clearfix">
        <div id="content-wrapper" class="clearfix">
            <div class="headbox">
                <h3>${menuPlan?.name}</h3>
            </div>
            <div class="top-shadow">
                <label>&nbsp;</label>
            </div>
            <div class="leftbox clearfix">
                <div class="wintertop"><ul><li><strong>&nbsp;</strong></li><li><input name="" type="checkbox" value=""/> Export to Todo</li><li>
                    <img src="${resource(dir:'images',file:'printer.gif')}" alt="print" align="absmiddle"/> &nbsp; Print Shopping List
                </li></ul>
                </div>
                <g:each in="${weeks}" var="week">
                    <div class="winter-week clearfix" style="width:406px;">
                        <div class="winterButton"><strong>${week}</strong>
                            <p>Produce</p>
                        </div>
                        <ul>
                            <li>
                                <ul><li class="first_clumon"><input name="" type="checkbox" value=""/></li>
                                    <li class="email">Eggs</li><li>2 dozen</li>
                                </ul>
                            </li>
                            <li class="alternatecolor">
                                <ul><li class="first_clumon"><input name="" type="checkbox" value=""/></li>
                                    <li class="email">Eggs</li>
                                    <li>2 dozen</li>
                                </ul>
                            </li>
                        </ul>
                    </div>
                    <div class="winterButton">
                        <ul>
                            <li><p>Meat/Poultry/Seafood</p>
                                <p>Dairyt/Refrigerater/Freezer case</p>
                                <p>Can/Bottle/Jar</p>
                                <p>Miscellaneous</p>
                                <p>Stock Items(Inventory your own stock before you shop)</p>
                            </li>

                            <li><span><input type="button" value="Add Item"/></span>
                                <label>
                                    <input type="text" class="inpbox" value="Auto complete grocery Items " size="40"/>
                                </label>
                            </li>
                        </ul>
                    </div>
                    %{--<div style="height:50px;">&nbsp;</div>--}%
                </g:each>
                <div class="winterButton">
                    <ul><li>
                        <input class="button" type="button" value="Create"/>
                        <input class="button" type="button" value="Save"/>
                        <input class="button" type="button" value="Cancel"/>
                    </li></ul>
                </div>
            </div>
            <div class="bottom-shadow">
                <label>&nbsp;</label>
            </div>
        </div>
    </div>
</div>
</body>
</html>

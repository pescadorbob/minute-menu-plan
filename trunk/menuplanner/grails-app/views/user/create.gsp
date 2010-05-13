<%@ page import="com.mp.domain.*" %>
<html>
<head>
    <meta name="layout" content="menu"/>
    <title>Add Recipe</title>
</head>
<body>

<div id="container">
    <div id="wrapper" class="clearfix">
        <div id="content-wrapper" class="clearfix">
            <div class="headbox">
                <h3>Admin Profile  Edit / Add</h3>
            </div>

            <div class="top-shadow">
                <label>&nbsp;</label>
            </div>
            <div class="leftbox clearfix">
                <g:uploadForm name="formCreateUser" controller="user" action="save">
                    <div id="leftpanel">
                        <div id="photo">
                            <img src="${resource(dir: 'images', file: 'photo-pic.png')}" alt="Photo"/>
                        </div>
                        %{--<ul>--}%
                        %{--<li><a href="#">Upload New Photo</a> <a href="#">Remove Photo</a></li>--}%

                        %{--<li>Member since March 2010</li>--}%
                        %{--<li>--}%
                        %{--<h3>Contributed Recipes</h3>--}%
                        %{--</li>--}%
                        %{--<li><a href="#">Beef & broccoll</a></li>--}%
                        %{--<li><a href="#">Lamb Curry</a></li>--}%

                        %{--<li><a href="#">Turkey Pie</a></li>--}%
                        %{--<li>--}%
                        %{--<h3>Favorites</h3>--}%
                        %{--</li>--}%
                        %{--<li><a href="#">Beef & broccoll</a> <a href="#">remove</a></li>--}%
                        %{--<li><a href="#">Lamb Curry</a> <a href="#">remove</a></li>--}%

                        %{--<li><a href="#">Turkey Pie</a> <a href="#">remove</a></li>--}%
                        %{--</ul>--}%
                    </div>
                    <div id="rightpanel">

                        <ul>
                            <li><span><strong>Username :</strong></span>
                                <label>
                                    <input name="userName" type="text" class="inpbox" value="${userCO?.userName}"/>
                                </label>
                            </li>
                            <li><span><strong>Name :</strong></span>
                                <label>
                                    <input name="name" type="text" class="inpbox" value="${userCO?.name}"/>
                                    &nbsp; Public name displayed on recipes
                                </label>
                            </li>
                            <li><span><strong>City :</strong></span>
                                <label>
                                    <input name="city" type="text" class="inpbox" value="${userCO?.city}"/>
                                    &nbsp; City displayed on recipes</label>
                            </li>
                            <li><span><strong>Mouths to Feed :</strong></span>

                                <label>
                                    <input name="mouthsToFeed" type="text" class="inpbox" value="${userCO?.mouthsToFeed}"/>
                                </label>
                            </li>
                            <li><span><strong>Something about yourself :</strong></span>
                                <label>
                                    <g:textArea name="introduction" value="" class="txtarea" rows="4" cols="20"/>
                                    &nbsp; Public</label>
                            </li>
                            %{--<li><span>&nbsp;</span>--}%
                            %{--<label>--}%
                            %{--<input name="" type="checkbox" value=""/>--}%
                            %{--<strong>Automatically renews</strong></label>--}%
                            %{--</li>--}%
                            %{--<li><span>&nbsp;</span>--}%
                            %{--<label>--}%

                            %{--<input name="" type="checkbox" value=""/>--}%
                            %{--<strong>Unlimited subscription</strong></label>--}%
                            %{--</li>--}%
                            %{--<li><span>&nbsp;</span>--}%
                            %{--<label><strong>Last day of subscription</strong> &nbsp;--}%
                            %{--<input type="text" class="inpbox" style="width:70px;" value="2 / 2 / 2010"/>--}%
                            %{--&nbsp;--}%
                            %{--<img src="${resource(dir: 'images', file: 'calendar.png')}" alt="Calendar " align="absmiddle"/>--}%
                            %{--</label>--}%
                            %{--</li>--}%
                            %{--<li><span>&nbsp;</span>--}%
                            %{--<label>--}%
                            %{--<input name="" type="checkbox" value=""/>--}%
                            %{--<strong>Account enabled</strong></label>--}%
                            %{--</li>--}%
                        </ul>
                        %{--<div id="right-link">--}%

                        %{--<h3>Contributed Recipes</h3>--}%
                        %{--<ul>--}%
                        %{--<li>2 / 2 / 05 <a href="#">remove</a></li>--}%
                        %{--<li>2 / 3 / 05 <a href="#">remove</a></li>--}%
                        %{--<li>2 / 6 / 06</a> <a href="#">remove</a></li>--}%

                        %{--</ul>--}%
                        %{--</div>--}%

                    </div>
                    <div id="button">
                        <g:submitButton name="btnUpdate" class="button" value="Create User"/>
                        <input class="button" type="button" name="cancel" id="cancel" value="Cancel"/>
                    </div>
                </g:uploadForm>
            </div>
            <div class="bottom-shadow">
                <label>&nbsp;</label>
            </div>
        </div>
    </div>
</div>
</body>
</html>
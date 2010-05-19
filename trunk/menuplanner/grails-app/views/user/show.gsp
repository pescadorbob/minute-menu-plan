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
                <h3>Admin Profile Detail</h3>
            </div>

            <div class="top-shadow">
                <label>&nbsp;</label>
            </div>
            <div class="leftbox clearfix">
                <div id="leftpanel">
                    <div id="photo">
                        <mp:recipeImage id="${user?.image?.id}" noImage="no-img.gif" height="150" width="150"/>
                    </div>
                    <ul>
                        %{--<li><a href="#">Upload New Photo</a> <a href="#">Remove Photo</a></li>--}%

                        <li>Member since ${user?.joiningDate?.format('MMMM yyyy')}</li>

                        <li></li>
                        <li></li>

                        <g:each in="${user?.type}" var="role">
                            <li>
                                <strong>${role}</strong>
                            </li>
                        </g:each>
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
                    </ul>
                </div>
                <div id="rightpanel">

                    <ul>
                        <li><span><strong>Email :</strong></span>
                            <label>${user?.email}</label>
                        </li>
                        <li><span><strong>Name :</strong></span>
                            <label>${user?.name}</label>
                        </li>
                        <li><span><strong>City :</strong></span>
                            <label>${user?.city}</label>
                        </li>
                        <li><span><strong>Mouths to Feed :</strong></span>
                            <label>${user?.mouthsToFeed}</label>
                        </li>
                        <li><span><strong>Something about yourself :</strong></span>
                            <label>${user?.introduction}</label>
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
                        %{--<input type="text" class="inpboxSmall" value="2 / 2 / 2010"/>--}%
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
                    <a href="${createLink(controller: 'user', action: 'edit', id: user?.id)}">
                        <input type="button" name="btnEdit" class="button" value="Edit Profile"/>
                    </a>
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
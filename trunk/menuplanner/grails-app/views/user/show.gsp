<%@ page import="com.mp.domain.*" %>
<html>
<head>
    <meta name="layout" content="menu"/>
    <title>Show User</title>
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
                        <li>Member since ${user?.joiningDate?.format('MMMM yyyy')}</li>
                        <li></li><li></li>
                        <g:each in="${user?.roles}" var="role">
                            <li><strong>${role}</strong></li>
                        </g:each>
                        <li><h3>Contributed Recipes</h3></li>
                        <g:each in="${user?.contributions}" var="recipe">
                            <li><a href="${createLink(controller: 'recipe', action: 'show', id: recipe?.id)}">${recipe?.name}</a></li>
                        </g:each>
                        <li><h3>Favorites</h3></li>
                        <g:each in="${user?.favourites}" var="recipe">
                            <li><a href="${createLink(controller: 'recipe', action: 'show', id: recipe?.id)}">${recipe?.name}</a>
                                <a href="${createLink(controller: 'user', action: 'removeFavorite', id: recipe?.id)}">remove</a></li>
                        </g:each>
                    </ul>
                </div>
                <div id="rightpanel">
                    <ul>
                        <li><span><strong>Email :</strong></span><label>${user?.email}</label></li>
                        <li><span><strong>Name :</strong></span><label>${user?.name}</label></li>
                        <li><span><strong>City :</strong></span><label>${user?.city}</label></li>
                        <li><span><strong>Mouths to Feed :</strong></span><label>${user?.mouthsToFeed}</label></li>
                        <li><span><strong>Something about yourself :</strong></span><label>${user?.introduction}</label></li>
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
                    <div id="right-link">
                        <g:if test="${user.inappropriateFlagsCount}">
                            <h3>Inappropriate Flags</h3>
                            <ul>
                                <g:each in="${user?.abusiveRecipes}" var="abusiveRecipe">
                                    <li>${abusiveRecipe?.dateCreated?.format('M/d/yy')} &nbsp;${abusiveRecipe.name} &nbsp;&nbsp;<a>remove</a></li>
                                </g:each>
                                <g:each in="${user?.abusiveComments}" var="abusiveComment">
                                    <li>${abusiveComment?.dateCreated?.format('M/d/yy')} &nbsp;${abusiveComment.body} &nbsp;&nbsp;<a>remove</a></li>
                                </g:each>
                            </ul>
                        </g:if>
                    </div>
                </div>
                <div id="button">
                    <g:uploadForm name="formUserDetail">
                        <g:hiddenField name='id' value='${user?.id}'/>
                        <g:actionSubmit class='button' controller='user' action='edit' id='${user?.id}' value='Edit Profile' name='edit'/>
                        <g:actionSubmit class='button' controller='user' action='delete' id='${user?.id}' value='Delete User' name='delete' onclick="return confirm('Are you sure?');"/>
                    </g:uploadForm>
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
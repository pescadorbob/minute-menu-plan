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
                <g:if test="${flash.message}">
                    <div id="flashMsgTst" class="userFlashMessage">
                        ${flash.message}
                    </div>
                </g:if>

                <div id="leftpanel">
                    <div id="photo">
                        <mp:image id="${user?.image?.id}" height="150" width="150"/>
                    </div>
                    <ul>
                        <li>Member since ${user?.party?.joiningDate?.format('MMMM yyyy')}</li>
                        <li></li><li></li>
                        <g:each in="${user?.party?.roleTypes}" var="roleType"><li><strong>${roleType}</strong></li></g:each>
                        <li><h3>Contributed Recipes</h3></li>
                        <g:each in="${user?.party?.contributions}" var="recipe">
                            <li><a href="${createLink(controller: 'recipe', action: 'show', id: recipe?.id)}">${recipe?.name}</a></li>
                        </g:each>
                        <li><h3>Favorites</h3></li>
                        <g:each in="${user?.party?.favourites}" var="recipe">
                            <li><a href="${createLink(controller: 'recipe', action: 'show', id: recipe?.id)}">${recipe?.name}</a>
                                <a href="${createLink(controller: 'user', action: 'removeFavorite', id: recipe?.id)}">remove</a></li>
                        </g:each>
                    </ul>
                </div>
                <div id="rightpanel">
                    <ul>
                        <li><span><strong>Email :</strong></span><label>${user?.party?.email}</label></li>
                        <li><span><strong>Name :</strong></span><label>${user?.screenName}</label></li>
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
                        <g:if test="${user?.party?.inappropriateFlagsCount}">
                            <h3>Inappropriate Flags</h3>
                            <ul>
                                <g:each in="${abusiveRecipesMap?.keySet()}" var="abusiveRecipe">
                                    <li>${abusiveRecipe?.dateCreated?.format('M/d/yy')} &nbsp;<g:link controller="recipe" action="show" id="${abusiveRecipe?.id}" onclick="return confirm('Remove Abuse?');">${abusiveRecipe.name}</g:link>&nbsp;(Abuse Reported ${abusiveRecipesMap[abusiveRecipe]} Times)&nbsp;&nbsp;
                                    <g:if test="${permission.hasPermission(permission: Permission.REMOVE_RECIPE_ABUSE)}">
                                        <g:link controller="recipe" action="removeRecipeAbuse" id="${abusiveRecipe.id}" params="[userId:user?.id]">Remove</g:link>
                                    </g:if>
                                    </li>
                                </g:each>
                                <g:each in="${abusiveCommentsMap?.keySet()}" var="abusiveComment">
                                    <li>${abusiveComment?.dateCreated?.format('M/d/yy')} &nbsp;${abusiveComment.body}&nbsp;(Abuse Reported ${abusiveCommentsMap[abusiveComment]} Times)&nbsp;&nbsp;
                                    <g:if test="${permission.hasPermission(permission: Permission.REMOVE_COMMENT_ABUSE)}">
                                        <g:link controller="recipe" action="removeCommentAbuse" id="${abusiveComment?.id}" params="[userId:user?.id]" onclick="return confirm('Remove Abuse?');">Remove</g:link></li>
                                    </g:if>
                                </g:each>
                            </ul>
                        </g:if>
                    </div>
                </div>
                <div id="button">
                    <g:form name="formUserDetail">
                        <g:hiddenField name='id' value='${user?.id}'/>
                        <g:actionSubmit class='button editUserButtonFT' controller='user' action='edit' id='${user?.id}' value='Edit Profile'/>
                        <g:actionSubmit class='button' controller='user' action='delete' id='${user?.id}' value='Delete User' onclick="return confirm('Are you sure?');"/>
                    </g:form>
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
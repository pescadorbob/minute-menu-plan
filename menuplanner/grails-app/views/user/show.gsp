<%@ page import="com.mp.domain.Permission; org.apache.commons.lang.StringUtils; org.codehaus.groovy.grails.commons.ConfigurationHolder; com.mp.domain.party.*; com.mp.domain.*" %>
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
            <h3>${StringUtils.capitaliseAllWords(party?.toString())} Profile</h3>
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
                    <g:if test="${party?.subscriber}">
                        <div class="scaleImageSizeUpperDiv" style="height:180px;width:180px;border:none;">
                        <div id="photo" class="scaleImageSize">
                            <mp:image size="200" id="${party?.subscriber?.image?.id}"/>
                        </div>
                        </div>
                    </g:if>
                    <ul>
                        <li>Member since ${party?.joiningDate?.format('MMMM yyyy')}</li>
                        <li></li><li></li>
                      <h3>Roles</h3>
                      <g:each in="${party?.roleTypes}" var="roleType"><li class="userRolesFT"><strong>${roleType}</strong></li></g:each>
                      <h3>Relationships</h3>
                        %{--<pty:relationships party="${party}" >--}%
                          %{--<li class="userRolesFT"><strong>${lhs}</strong>:${rhs}</li>--}%
                        %{--</pty:relationships>--}%

                        <li><h3>Contributed Recipes</h3></li>
                        <g:render template="/user/contributedRecipes" model="[party:party]"/>
                        <li><h3>Favorites</h3></li>
                        <g:render template="/user/favoriteRecipes" model="[party:party]"/>
                    </ul>
                    <g:if test="${(party?.director) && (permission.hasPermission(permission: Permission.CAN_VIEW_SUB_AFFILIATES))}">
                        <ul>
                            <li><h3>Coaches</h3></li>
                            <party:coaches party="${party}" var="coach">
                              <li><g:link controller="user" action="show" id="${coach?.party?.id}">${coach}</g:link></li>
                            </party:coaches>
                        </ul>
                    </g:if>
                </div>
                <div id="rightpanel">
                    <ul>
                        <li><span><strong>Email :</strong></span><label>${party?.email}</label></li>
                        <li><span><strong>Name :</strong></span><label>${party?.name}</label></li>
                        <g:if test="${party?.subscriber}">
                            <li><span><strong>City :</strong></span><label>${party?.subscriber?.city ?: ''}</label></li>
                            <li><span><strong>Mouths to Feed :</strong></span><label>${party?.subscriber?.mouthsToFeed}</label></li>
                            <li><span><strong>Something about yourself :</strong></span><label>${party?.subscriber?.introduction}</label></li>
                            <li><span><strong>Show Content Using Alcohol :</strong></span><label>${party?.showAlcoholicContent ? "Yes" : "No"}</label></li>
                        </g:if>
                      <g:if test="${(party?.coach)&& (permission.hasPermission(permission: Permission.CAN_VIEW_INVITATION_URL))}">
                        <li><strong>Url to Invite Subscribers</strong></br>
                            <textArea name="uniqueUrl" readonly="true" cols="80" rows="1" class="urlTextArea">${ConfigurationHolder.config.grails.serverURL + '/?coachId=' + party?.uniqueId}</textArea>
                        </li>
                    </g:if>
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
                        <g:if test="${party?.inappropriateFlagsCount}">
                            <h3>Inappropriate Flags</h3>
                            <ul>
                                <g:each in="${abusiveRecipesMap?.keySet()}" var="abusiveRecipe">
                                    <li>${abusiveRecipe?.dateCreated?.format('M/d/yy')} &nbsp;<g:link controller="recipe" action="show" id="${abusiveRecipe?.id}" onclick="return confirm('Remove Abuse?');">${abusiveRecipe.name}</g:link>&nbsp;(Abuse Reported ${abusiveRecipesMap[abusiveRecipe]} Times)&nbsp;&nbsp;
                                    <g:if test="${permission.hasPermission(permission: Permission.REMOVE_RECIPE_ABUSE)}">
                                        <g:link controller="recipe" action="removeRecipeAbuse" id="${abusiveRecipe.id}" params="[userId:party?.id]">Remove</g:link>
                                    </g:if>
                                    </li>
                                </g:each>
                                <g:each in="${abusiveCommentsMap?.keySet()}" var="abusiveComment">
                                    <li>${abusiveComment?.dateCreated?.format('M/d/yy')} &nbsp;${abusiveComment.body}&nbsp;(Abuse Reported ${abusiveCommentsMap[abusiveComment]} Times)&nbsp;&nbsp;
                                    <g:if test="${permission.hasPermission(permission: Permission.REMOVE_COMMENT_ABUSE)}">
                                        <g:link controller="recipe" action="removeCommentAbuse" id="${abusiveComment?.id}" params="[userId:party?.id]" onclick="return confirm('Remove Abuse?');">Remove</g:link></li>
                                    </g:if>
                                </g:each>
                            </ul>
                        </g:if>
                    </div>
                </div>
                <div id="button">
                    <g:form name="formUserDetail">
                        <g:hiddenField name='id' value='${party?.id}'/>
                        <g:if test="${(party?.superAdmin) && permission.hasPermission(permission: Permission.MANAGE_SUPER_ADMIN,party:currentUser)}">
                            <g:actionSubmit class='button editUserButtonFT' controller='user' action='edit' id='${party?.id}' value='Edit Profile'/>
                            <g:actionSubmit class='button deleteUserButtonFT' controller='user' action='delete' id='${party?.id}' value='Delete User' onclick="return confirm('Are you sure?');"/>
                        </g:if>

                        <g:elseif test="${(party?.administrator) && permission.hasPermission(permission: Permission.MANAGE_ADMIN,party:currentUser)}">
                            <g:actionSubmit class='button editUserButtonFT' controller='user' action='edit' id='${party?.id}' value='Edit Profile'/>
                            <g:actionSubmit class='button deleteUserButtonFT' controller='user' action='delete' id='${party?.id}' value='Delete User' onclick="return confirm('Are you sure?');"/>
                        </g:elseif>

                        <g:elseif test="${(party?.subscriber) && permission.hasPermission(permission: Permission.MANAGE_SUBSCRIBER,party:currentUser)}">
                            <g:actionSubmit class='button editUserButtonFT' controller='user' action='edit' id='${party?.id}' value='Edit Profile'/>
                            <g:actionSubmit class='button deleteUserButtonFT' controller='user' action='delete' id='${party?.id}' value='Delete User' onclick="return confirm('Are you sure?');"/>
                        </g:elseif>

                        <g:elseif test="${(party?.director) && permission.hasPermission(permission: Permission.MANAGE_AFFILIATE,party:currentUser)}">
                            <g:actionSubmit class='button editUserButtonFT' controller='user' action='edit' id='${party?.id}' value='Edit Profile'/>
                            <g:actionSubmit class='button deleteUserButtonFT' controller='user' action='delete' id='${party?.id}' value='Delete User' onclick="return confirm('Are you sure?');"/>
                        </g:elseif>

                        <g:elseif test="${(party?.coach) && permission.hasPermission(permission: Permission.MANAGE_SUB_AFFILIATE,party:currentUser)}">
                            <g:actionSubmit class='button editUserButtonFT' controller='user' action='edit' id='${party?.id}' value='Edit Profile'/>
                            <g:actionSubmit class='button deleteUserButtonFT' controller='user' action='delete' id='${party?.id}' value='Delete User' onclick="return confirm('Are you sure?');"/>
                        </g:elseif>
                        %{--<g:if test="${(currentUser?.id !=party?.id)}">--}%
                            %{--<g:submitButton class='button' id="backToCurrentUserProfile" controller='user' action='show' value='Back' name="back" onclick=""/>--}%
                        %{--</g:if>--}%
                    </g:form>
                </div>
            </div>
            <div class="bottom-shadow">
                <label>&nbsp;</label>
            </div>
        </div>
    </div>
</div>
%{--<script type="text/javascript">--}%
    %{--$("#backToCurrentUserProfile").click(function() {--}%
        %{--window.location.replace("${createLink(controller:'user',action:'show',id:UserTools.currentUser?.party?.id)}");--}%
        %{--return false;--}%
    %{--})--}%
%{--//</script>--}%
</body>
</html>

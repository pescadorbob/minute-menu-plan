<%@ page import="com.mp.tools.UserTools; com.mp.subscriptions.SubscriptionStatus; com.mp.domain.Permission; org.apache.commons.lang.StringUtils; org.codehaus.groovy.grails.commons.ConfigurationHolder; com.mp.domain.party.*; com.mp.domain.*" %>
<html>
<head>
    <meta name="layout" content="menu"/>
    <title>${StringUtils.capitaliseAllWords(party?.toString())} Profile</title>
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
                    <div id="flashMsgTst" class="flashMessage">
                        ${flash.message}
                    </div>
                </g:if>

                <div id="leftpanel">
                    <g:if test="${party?.subscriber}">
                        <div class="scaleImageSizeUpperDiv" style="height:180px;width:180px;border:none;">
                            <div class="scaleImageSize">
                                <mp:image size="200" id="${party?.subscriber?.image?.id}" title="${party?.subscriber}"/>
                            </div>
                        </div>
                    </g:if>
                    <ul>
                        <li>Member since ${party?.joiningDate?.format('MMMM yyyy')}</li>
                        <li></li><li></li>
                        <h3>Roles</h3>
                        <g:each in="${party?.roleTypes}" var="roleType">
                            <li class="userRolesFT">
                                <strong>${roleType}</strong>
                                <g:if test="${(roleType == PartyRoleType.Subscriber) && party.subscriber.subscriptions?.any{it.status == SubscriptionStatus.CURRENT}}">
                                    <ul>
                                        <li style="padding-left:20px;">Current Subscriptions:</li>
                                        <g:each in="${party.subscriber.subscriptions.sort{it.activeFrom}}" var="subscription">
                                            <g:if test="${subscription.status == SubscriptionStatus.CURRENT}">
                                                <li style="padding-left:20px;">${subscription.originalProductOffering}</li>
                                            </g:if>
                                        </g:each>
                                    </ul>
                                </g:if>
                            </li>
                        </g:each>
                    </ul>
                </div>
                <div id="rightpanel">
                    <ul>
                        <g:if test="${UserTools.currentUser && ((UserTools.currentUser.party.roles*.type in [PartyRoleType.Admin, PartyRoleType.SuperAdmin])|| (UserTools.currentUser.party == party))}">
                            <li><span><strong>Email :</strong></span><label>${party?.email}</label></li>
                        </g:if>
                        <li><span><strong>Name :</strong></span><label>${party?.name}</label></li>
                        <g:if test="${party?.subscriber}">
                            <li><span><strong>City :</strong></span><label>${party?.subscriber?.city ?: ''}</label></li>
                            <li><span><strong>Mouths to Feed :</strong></span><label>${party?.subscriber?.mouthsToFeed}</label></li>
                            <li><span><strong>Something about yourself :</strong></span><label>${party?.subscriber?.introduction}</label></li>
                            <li><span><strong>Show Content Using Alcohol :</strong></span><label>${party?.showAlcoholicContent ? "Yes" : "No"}</label></li>
                            <g:if test="${party?.subscriber?.coach}">
                                <li><span><strong>Coach :</strong></span>
                                    <label><g:link controller="user" action="show" id="${party?.subscriber?.coach?.party?.id}">${party?.subscriber?.coach}</g:link></label>
                                </li>
                            </g:if>

                        </g:if>
                        <g:if test="${party?.coach?.director}">
                            <li><span><strong>Director :</strong></span>
                                <label><g:link controller="user" action="show" id="${party?.coach?.director?.party?.id}">${party?.coach?.director}</g:link></label>
                            </li>
                        </g:if>
                        <g:if test="${(party?.coach)&& (permission.hasPermission(permission: Permission.CAN_VIEW_INVITATION_URL))}">
                            <li><strong>Url to Invite Subscribers</strong></br>
                       <textArea name="uniqueUrl" readonly="true" cols="90" rows="1" class="urlTextArea">${ConfigurationHolder.config.grails.serverURL + '/?coachId=' + party?.uniqueId}</textArea>
                        </li>
                        </g:if>

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
              <g:if test="${(party?.director) && (permission.hasPermission(permission: Permission.CAN_VIEW_SUB_AFFILIATES))}">
                <div class="contributedRecipes">
                    <h3>Clients&nbsp;<span id="emailCoachesNoteBtn" style="cursor:pointer"><p:image src='emailMe.jpeg' alt="" align="absmiddle" style="width:29px;height:23px"/></span></h3>
                    <div class="emailNote" id="emailCoachesNote" style="display:none">
                    <g:formRemote name="emailCoachesNoteForm" url="${[action:'emailNote']}">
                      <div>Email a note to all your coaches:</div>
                        <div><g:textArea name="note" rows="5" cols="40" /></div>
                        <g:hiddenField name="partyId" value="${party?.id}"/>
                        <g:hiddenField name="role" value="director"/>
                        <g:submitButton name="submit" value="send" id="emailCoachesSubmitButton" onClick="attachPaginationLinkEvents()"/>
                    </g:formRemote>
                      </div>
                  <ul>
                  <li class="product"><ul>
                      <pty:coaches party="${party}" var="coach" status="index">
                        <g:if test="${index %4==0}">
                        </ul><ul class="clearfix">
                        </g:if>
                             <g:render template="/user/thumbnail" model="[party:coach?.party]"/>
                      </pty:coaches>
                  </ul></li>
                  </ul>
                </div>
              </g:if>
              <g:if test="${(party?.coach) && (permission.hasPermission(permission: Permission.CAN_VIEW_CLIENTS))}">


                  <div class="clients">
                    <h3>Clients&nbsp;<span id="emailNoteBtn" style="cursor:pointer"><p:image src='emailMe.jpeg' alt="" align="absmiddle" style="width:29px;height:23px"/></span></h3>
                    <div class="emailNote" id="emailShoppingList" style="display:none">
                    <g:formRemote name="emailNoteForm" url="${[action:'emailNote']}">
                      <div>Email a note to all your clients:</div>
                        <div><g:textArea name="note" rows="5" cols="40" /></div>
                        <g:hiddenField name="partyId" value="${party?.id}"/>
                        <g:hiddenField name="role" value="coach"/>
                        <g:submitButton name="submit" value="send" id="emailSubmitButton" onClick="attachPaginationLinkEvents()"/>
                    </g:formRemote>
                </div>
                     <ul>
                     <li class="product"><ul>
                      <pty:clients party="${party}" var="client" status="index">
                        <g:if test="${index %4==0}">
                        </ul><ul class="clearfix">
                        </g:if>
                             <g:render template="/user/thumbnail" model="[party:client?.party]"/>
                      </pty:clients>
                     </ul></li>
                  </ul>

                  </div>
              </g:if>

                <div class="contributedRecipes">
                  <h3>Contributed Recipes</h3>
                  <ul>
                  <g:render template="/user/contributedRecipes" model="[party:party]"/>
                  </ul>
                </div>
              <div class="favoriteRecipes">
                <h3>Favorites</h3>
                <ul>
                  <g:render template="/user/favoriteRecipes" model="[party:party]"/>
                  </ul>
                </div>
              <div class="menuPlans">
                <h3>Menu Plans</h3>
                <ul>
                  <g:each in="${MenuPlan.findAllByOwner(party)}" var="menuPlan">
                      <li><g:link controller="menuPlan" action="show" id="${menuPlan.id}">${menuPlan.name}</g:link></li>
                  </g:each>
                  </ul>
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
          <div id="emailMessageTemplate">
              <g:render template="/shoppingList/ajaxEmailSendingMessage"/>
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
<script type="text/javascript">
  $(function() {
        $("#emailNoteBtn").click(function() {
            jQuery('#emailShoppingList').show()
        })
    })
  $(function() {
        $("#emailCoachesNoteBtn").click(function() {
            jQuery('#emailCoachesNote').show()
        })
    })
</script>
</body>
</html>

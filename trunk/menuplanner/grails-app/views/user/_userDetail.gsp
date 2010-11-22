<%@ page import="com.mp.domain.Permission; com.mp.domain.PartyRoleType" %>

%{--<fb:prompt-permission perms="read_stream,offline_access">Connect with Facebook</fb:prompt-permission>--}%

%{--<fb:login-button onlogin="alert('hello');"></fb:login-button>--}%
%{--<fb:login-button v="2" perms="read_stream,offline_access" size="medium" onlogin="window.location.reload(true);">x with Facebook</fb:login-button>--}%
%{--<fb:profile-pic uid="loggedinuser" size="square" facebook-logo="true"></fb:profile-pic>--}%
%{--<fb:name uid="loggedinuser" useyou="false" linked="true"></fb:name>--}%

<div id="rightpanel">

    <ul>
        <g:if test="${userCO.id}">
            <g:if test="${party?.id==currentUser?.id}">
                <li><span>&nbsp;</span>
                    <label><facebook:connect userId="${userCO.id}"/></label>
                </li>
            </g:if>
        </g:if>
        <li><span><strong>Email :</strong></span>
            <label>
                <span id="displayEmailAsLabel"></span>
                <input name="email" type="text" class="inpbox ${hasErrors(bean: userCO, field: 'email', 'errors')}" value="${(userCO) ? (userCO.email) : ''}" autocomplete="false"/>
                <input class="passwordSection" type="button" id="btnChangePassword" name="changePassword" value="Change Password" onclick="ChangePassword()" style="display:none;"/>
            </label>
        </li>
        <li class="passwordSection">
            <span><strong>Password :</strong></span>
            <label>
                <input name="password" type="password" class="inpbox  ${hasErrors(bean: userCO, field: 'password', 'errors')}" value="${userCO?.password}" autocomplete="false"/>
                &nbsp; Minimum 4 characters</label>
        </li>
        <li class="passwordSection">
            <span><strong>Confirm Password :</strong></span>
            <label><input name="confirmPassword" type="password" autocomplete="false" class="inpbox  ${hasErrors(bean: userCO, field: 'confirmPassword', 'errors')}" value="${userCO?.confirmPassword}"/>&nbsp; Same as password</label>
        </li>
        <li><span><strong>Name :</strong></span>
            <label><input name="name" type="text" class="inpbox  ${hasErrors(bean: userCO, field: 'name', 'errors')}" value="${userCO?.name}"/>&nbsp; Public name displayed on recipes</label>
        </li>
      <pty:hasRole role="${PartyRoleType.Subscriber}" bean="${party}">
        <li><span><strong>City :</strong></span>
            <label><input name="city" type="text" class="inpbox  ${hasErrors(bean: userCO, field: 'city', 'errors')}" value="${userCO?.city}"/>&nbsp; City displayed on recipes</label>
        </li>
        <li><span><strong>Mouths to Feed :</strong></span>
            <label><input name="mouthsToFeed" type="text" class="inpboxSmall  ${hasErrors(bean: userCO, field: 'mouthsToFeed', 'errors')}" value="${userCO?.mouthsToFeed}"/></label>
        </li>
        <li><span><g:checkBox name="showAlcoholicContent" value="${userCO?.showAlcoholicContent}"/></span>
            <label><strong>Opt-in for Content Using Alcohol</strong></label>
        </li>
        <li><span><strong>Something about yourself :</strong></span>

          <label><fckeditor:editor width="100%" height="400"
                  name="introduction">${userCO?.introduction}</fckeditor:editor>&nbsp; Public</label>
        </li>
      </pty:hasRole>
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
        <li><span></span>
            <g:render template="/user/enableUser" model="[party:party,currentUser:currentUser,userCO:userCO]"/>
        </li>
    </ul>
</div>


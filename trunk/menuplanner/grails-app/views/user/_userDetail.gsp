<%@ page import="com.mp.domain.Permission; com.mp.domain.UserType" %>
<div id="rightpanel">

    <ul>
        <li><span><strong>Email :</strong></span>
            <label>
                <span id="displayEmailAsLabel"></span>
                <input name="email" type="text" class="inpbox ${hasErrors(bean: userCO, field: 'email', 'errors')}" value="${userCO?.email}"/>
                <input class="passwordSection" type="button" id="btnChangePassword" name="changePassword" value="Change Password" onclick="ChangePassword()" style="display:none;"/>
            </label>
        </li>
        <li class="passwordSection">
            <span><strong>Password :</strong></span>
            <label>
                <input name="password" type="password" class="inpbox  ${hasErrors(bean: userCO, field: 'password', 'errors')}" value="${userCO?.password}"/>
                &nbsp; Minimum 4 characters
            </label>
        </li>
        <li class="passwordSection">
            <span><strong>Confirm Password :</strong></span>
            <label>
                <input name="confirmPassword" type="password" class="inpbox  ${hasErrors(bean: userCO, field: 'confirmPassword', 'errors')}" value="${userCO?.confirmPassword}"/>
                &nbsp; Same as password
            </label>
        </li>
        <li><span><strong>Name :</strong></span>
            <label>
                <input name="name" type="text" class="inpbox  ${hasErrors(bean: userCO, field: 'name', 'errors')}" value="${userCO?.name}"/>
                &nbsp; Public name displayed on recipes
            </label>
        </li>
        <li><span><strong>City :</strong></span>
            <label>
                <input name="city" type="text" class="inpbox  ${hasErrors(bean: userCO, field: 'city', 'errors')}" value="${userCO?.city}"/>
                &nbsp; City displayed on recipes</label>
        </li>
        <li><span><strong>Mouths to Feed :</strong></span>

            <label>
                <input name="mouthsToFeed" type="text" class="inpboxSmall  ${hasErrors(bean: userCO, field: 'mouthsToFeed', 'errors')}" value="${userCO?.mouthsToFeed}"/>
            </label>
        </li>
        <li><span><strong>Something about yourself :</strong></span>
            <label>
                <g:textArea name="introduction" value="${userCO?.introduction}" class="txtarea  ${hasErrors(bean:userCO,field:'introduction', 'errors')}" rows="4" cols="22"/>
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
    %{--<input type="text" class="inpboxSmall" value="2 / 2 / 2010"/>--}%
    %{--&nbsp;--}%
    %{--<img src="${resource(dir: 'images', file: 'calendar.png')}" alt="Calendar " align="absmiddle"/>--}%
    %{--</label>--}%
    %{--</li>--}%

        <g:if test="${mp.hasPermission(permission: Permission.UPDATE_USER_ROLES)}">
            <li><span>&nbsp;</span>
                <label>
                    <input id="chk_Enable" name="isEnabled" ${(userCO) ? ((userCO?.isEnabled) ? 'checked="checked"' : '') : 'checked="checked"'}type="checkbox" value="true"/>
                    <strong>Account enabled</strong>
                </label>
            </li>
        </g:if>
        <g:else>
            <g:if test="${userCO?.isEnabled}">
                <input id="chk_Enable" name="isEnabled" type="hidden" value="true"/>
            </g:if>
        </g:else>
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

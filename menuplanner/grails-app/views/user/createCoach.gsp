<%@ page import="com.mp.domain.*" %>
<html>
<head>
    <meta name="layout" content="menu"/>
    <title>Add Coach</title>
</head>
<body>

<div id="container">
    <div id="wrapper" class="clearfix">
        <div id="content-wrapper" class="clearfix">
            <div class="headbox">
                <h3>Add Coach</h3>
            </div>
            <g:hasErrors bean="${userCO}">
                <div id="displayUserCOErrors" class="errors">
                    <g:renderErrors bean="${userCO}" as="list"/>
                </div>
            </g:hasErrors>

            <div class="top-shadow">
                <label>&nbsp;</label>
            </div>
            <div class="leftbox clearfix">
                <g:form name="formCreateCoach">
                    <div id="leftpanel">
                        <g:if test="${userCO?.joiningDate}"><li>Member since ${userCO?.joiningDate?.format('MMMM yyyy')}</li></g:if>
                    </div>
                    <input type="hidden" name="roles"  value="${userCO?.roles}"/>
                    <div class="rightpanel">
                        <ul>
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
                            <li><span></span>
                                <label>
                                    <input id="chk_Enable" name="isEnabled" ${(userCO) ? ((userCO?.isEnabled) ? 'checked="checked"' : '') : 'checked="checked"'}type="checkbox" value="true"/>
                                    <strong>Account enabled</strong>
                                </label>
                            </li>
                        </ul>
                    </div>
                    <div id="button">
                        <g:actionSubmit class="button" value="Create" controller="user" action="saveCoach"/>
                        <g:actionSubmit name="cancel" class="button" value="Cancel" controller="recipe" action="list"/>
                    </div>
                </g:form>
            </div>
            <div class="bottom-shadow">
                <label>&nbsp;</label>
            </div>
        </div>
    </div>
</div>
<g:render template="/user/userJquery"/>
</body>
</html>

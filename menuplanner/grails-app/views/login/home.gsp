<%@ page import="grails.util.Environment; grails.util.GrailsUtil" %>
<html>
<head>
    <meta name="layout" content="menu"/>
    <title>Minute Menu Plan</title>
</head>
<body>
<g:if test="${!(GrailsUtil.environment in ['test'])}">
    <facebook:facebookConnectJavascript/>
  </g:if>
<div id="content-wrapper" class="clearfix">
    <div id="left-panel-product">
        <div class="top-shadow">
            <label>&nbsp;</label>
        </div>
        <div class="leftbox clearfix">
            <div id="country-cateLogin">
                ${homePage?.leftBar}
            </div>
        </div>
        <div class="bottom-shadow">
            <label>&nbsp;</label>
        </div>
    </div>
    <div id="maiddle-panel">
        <div class="top-shadow">
            <label>&nbsp;</label>
        </div>
        <div class="leftbox clearfix">
            <div id="content">
                ${homePage?.centralText}
                <div class="clear clearfix">
                    <g:link controller="user" action="createFreeUser"><div class="orderbtn"><h3>Free</h3>Sign Up Now</div></g:link>
                    <div id="video-box"><p:image src='video.png'/></div>
                    <g:link controller="user" action="createFreeUser"><div class="orderbtn"><h3>Free</h3>Sign Up Now</div></g:link>
                </div>
                <br/>
                <div>
                    ${homePage?.categories}
                </div>
            </div>

        </div>
        <div class="bottom-shadow">
            <label>&nbsp;</label>
        </div>
    </div>
    <div id="right-panel-landing">
        <div class="top-shadow">
            <label>&nbsp;</label>
        </div>
        <div class="leftbox clearfix">
            <div id="login">
                <g:form name="loginForm">
                    <g:hiddenField name="targetUri" value="${params.targetUri}"/>
                    <ul>
                        <li>Username : (Email Address)<div class="search-input">
                            <input name="email" type="text" class="inp  ${hasErrors(bean: loginCO, field: 'email', 'loginExc')}" value="${loginCO?.email}"/></div>
                            <g:hasErrors bean="${loginCO}" field="email">
                                <div id="displayEmailError" class="loginError">
                                    <g:renderErrors bean="${loginCO}" field="email"/>
                                </div>
                            </g:hasErrors>
                        </li>
                        <li>Password :<div class="search-input">
                            <input name="password" type="password" class="inp  ${hasErrors(bean: loginCO, field: 'password', 'loginExc')}" value=""/></div>
                            <g:hasErrors bean="${loginCO}" field="password">
                                <div id="displayPasswordError" class="loginError">
                                    <g:renderErrors bean="${loginCO}" field="password"/>
                                </div>
                            </g:hasErrors>

                            <g:if test="${flash.message}">
                                <div id="display_WrongPassword_DisabledUser_Error" class="loginError">
                                    ${flash.message}
                                </div>
                            </g:if>
                        </li>
                        <li>
                            <g:actionSubmit controller="login" action="login" value="Login" class="userLoginLink"/>
                        </li>
                        <li><g:link controller="login" action="forgotPassword">forgot password or username?</g:link></li>
                        <span style="color:#007AD8">Or login using Facebook</span>
                        <g:if test="${!(GrailsUtil.environment in ['test'])}">
                            <fb:login-button onlogin="loginToMenuPlanner()" autologoutlink="true">Login</fb:login-button>
                        </g:if>
                        <li class="border"><h2>TESTIMONIAL</h2></li>
                        <li>${homePage?.testimonial}</li>
                    </ul>
                </g:form>
            </div>
        </div>
        <div class="bottom-shadow">
            <label>&nbsp;</label>
        </div>
    </div>
</div>
<g:if test="${!(GrailsUtil.environment in ['test'])}">
    <script type="text/javascript">
        <g:if test="${!(params.fbLogout)}">
        setTimeout("loginToMenuPlanner()", 2000);
        </g:if>
        function getUid() {
            return FB.Connect.get_loggedInUser()
        }
        function logoutFB() {
            FB.Connect.logout()
        }
        function loginToMenuPlanner() {
            FB.Facebook.get_sessionState().waitUntilReady(function(session) {
                if (session) {
                    $.post("${createLink(controller:'login',action:'isFacebookConnected')}", {'facebookUid':getUid()}, function(data) {
                        if (data == "true") {
                            window.location.reload();
                        }
                        else {
                            alert("Your Facebook account is not connected with Menu Planner website.\n Please login and connect your facebook account.");
                        }
                    })
                }
            });
        }
    </script>
</g:if>
</body>
</html>

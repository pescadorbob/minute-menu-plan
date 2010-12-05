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
                <div class="clearfix">
                    ${homePage?.centralText}
                </div>
                <div class="clearfix">
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
                    <ul>
                      <g:render template="login"/>
                         
                        <li class="border"><h2>TESTIMONIAL</h2></li>
                        <g:each in="${testimonials}" var="testimonial">
                            <li>${testimonial}</li>
                        </g:each>
                    </ul>

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

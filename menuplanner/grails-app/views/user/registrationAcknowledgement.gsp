<%@ page import="com.mp.domain.*" %>
<html>
<head>
    <meta name="layout" content="menu"/>
    <title>Registration Acknowledgement</title>
</head>
<body>

<div id="container">
    <div id="wrapper" class="clearfix">
        <div id="content-wrapper" class="clearfix">
            <div class="headbox">
                <h3>Welcome &nbsp;${user?.name}</h3>
            </div>
            <div class="top-shadow">
                <label>&nbsp;</label>
            </div>
            <div class="leftbox clearfix">
                <div id="rightpanel-welcome">
                    <ul>
                        <li>
Thank you for registering with the Minute Menu Plan.<br/>
<g:if test="${!user?.subscriber?.subscriptions*.requirements*.fulfilledBy}">
To complete your registration, please complete the following:
  <g:render template="pendingSubscriptions" model="[pendingSubscriptions:pendingSubscriptions]" />
       </g:if>
                        </li>
                    </ul>
                    <div id="right-link-back">
                        Click here to go back to <g:link class="registrationAcknowledgementFT" uri="/">Home Page</g:link>
                    </div>
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
<%@ page import="com.mp.domain.*" %>
<html>
<head>
    <meta name="layout" content="menu"/>
    <title>Registration Error</title>
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
Thank you for your interest in the Minute Menu Plan.<br/>
                          I'm really sorry, but it appears that an error has occurred in registering your account.
                          Please contact technical support at mmpsupport@yahoo.com.
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
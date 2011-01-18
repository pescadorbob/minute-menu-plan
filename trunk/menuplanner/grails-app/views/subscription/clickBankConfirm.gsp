<%@ page import="com.mp.domain.subscriptions.Subscription" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="menu"/>
    <title>Payment Received</title>
</head>
<body>
<div id="container">
    <div id="wrapper" class="clearfix">
        <div id="content-wrapper" class="clearfix">
            <div class="body">
                <div class="headbox">
                <h3>Your payment has been processed successfully.</h3>
                    </div>
                <span>Hi <b>${name}</b>, an email has been sent to <b>${email}</b>. Please click on <b>'Confirm Email'</b> link to confirm your email address. After confirmation, use following credentials to log-into the website:</span><br/><br/>
                <span><b>Email: </b> ${email}</span><br/>
                <span><b>Password: </b> [Your ClickBank Order Number]</span>

            </div>
        </div>
    </div>
</div>
</body>
</html>

<%@ page import="com.mp.tools.UserTools; org.codehaus.groovy.grails.commons.ConfigurationHolder" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta name="layout" content="menu"/>
    <title>Click Bank Promotion</title>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'user.css')}"/>

    <style type="text/css" media="screen">
    #mpFacebookConnectSuccess {
        width: 80% !important;
    }

    .FBConnectButton_Text {
        width: 150px !important;
    }

    .fbconnect_login_button {
        margin-left: 0px;
    }
    </style>
</head>
<body>
<div id="content-wrapper" class="clearfix">
    <div class="headbox">
        <h3>Click Bank Subscription</h3>
    </div>
    <div class="top-shadow">
        <label>&nbsp;</label>
    </div>
    <div class="leftbox clearfix">
        <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
        </g:if>

        <div class="clearfix"><br/>
            The Minute Menu plan is an online software service. This is dummy description of the plan offered on ClickBank website.<br/>
            <br/>
        </div>

        <g:form name="clickBankPromotionForm" controller="subscription" action="createClickBankSubscription">
            <input type="image" src="${resource(dir: 'images', file: 'clickBank.gif')}" id="submitForm"/>
        </g:form>
        <g:if test="${!UserTools.currentUser}">
            <div id="rightpanelbox">
                <span>Already a member?  Login Now!</span>
                <g:render template="/login/login"/>
            </div>
        </g:if>
    </div>
    <div class="bottom-shadow">
        <label>&nbsp;</label>

    </div>
</div>
</body>
</html>

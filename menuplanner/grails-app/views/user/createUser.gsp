<%@ page import="org.codehaus.groovy.grails.commons.ConfigurationHolder" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta name="layout" content="menu"/>
    <title>Add User</title>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'user.css')}"/>

    <style type="text/css" media="screen">
        #mpFacebookConnectSuccess {width: 80% !important;}
        .FBConnectButton_Text {width: 150px !important;}
        .fbconnect_login_button {margin-left: 100px;}
    </style>
</head>
<body>
<div id="content-wrapper" class="clearfix">
    <div class="headbox">
        <h3>One Page Checkout</h3>
    </div>
    <div class="top-shadow">
        <label>&nbsp;</label>
    </div>
    <div class="leftbox clearfix">
        <div class="clearfix"><br/>
            The Minute Menu plan is an online software service.Your credit card will be charged as per
            the subscription period chose and indicated below.<br/>
            <br/>
        </div>
        <g:form id="googleCheckoutForm" name="googleCheckoutForm" action="newUserCheckout">
            <div id="leftpanelbox">
                <h1>Your subscription</h1>
                <div id="subscription">
                    <ul>
                        <li class="head">
                            <ul>
                                <li class="bigcluman">Subscription</li>
                                <li>Price</li>
                                <li>Total</li>
                            </ul>
                        </li>
                        <li class="alternatecolor clearfix">
                            <ul>
                                <li class="bigcluman">Yearly - 3/2/2010</li>
                                <li>$50.00</li>
                                <li>$50.00</li>
                            </ul>
                        </li>
                        <li>
                            <ul>
                                <li class="bigcluman">Yearly - 3/2/2010</li>
                                <li>$50.00</li>
                                <li>$50.00</li>
                            </ul>
                        </li>
                    </ul>
                </div>

                <div class="boxDiv">
                    <h1>Profile Information</h1>
                    <ul>
                        <li>
                            <input name="" type="radio" value=""/>
                            Link your account with your facebook account
                        </li>
                        <li><facebook:connect/></li>
                        <li>
                            <input name="" type="radio" value=""/>
                            Create a new account. (You can link it later)
                        </li>
                        <li>
                            <g:hasErrors bean="${userCO}">
                                <div id="displayUserCOErrors" class="errors">
                                    <g:renderErrors bean="${userCO}" as="list"/>
                                </div>
                            </g:hasErrors>
                        </li>
                    </ul>
                </div>
                <div class="boxDiv">

                    <ul>
                        <li>
                            <label>Name</label>
                            <span>
                                <input name="name" type="text" class="inpbox  ${hasErrors(bean: userCO, field: 'name', 'errors')}" value="${userCO?.name}"/>
                            </span>
                        </li>
                        <li>
                            <label>City</label>
                            <span>
                                <input name="city" type="text" class="inpbox  ${hasErrors(bean: userCO, field: 'city', 'errors')}" value="${userCO?.city}"/>
                            </span>
                        </li>
                        <li>
                            <label>Email</label>
                            <span>
                                <input name="email" type="text" class="inpbox  ${hasErrors(bean: userCO, field: 'email', 'errors')}" value="${userCO?.email}"/>
                            </span>
                        </li>
                        <li>
                            <label>Password</label>
                            <span>
                                <input name="password" type="password" class="inpbox  ${hasErrors(bean: userCO, field: 'password', 'errors')}" value="${userCO?.password}"/>
                            </span>
                        </li>
                        <li>
                            <label>Confirm Password</label>
                            <span>
                                <input name="confirmPassword" type="password" class="inpbox  ${hasErrors(bean: userCO, field: 'confirmPassword', 'errors')}" value="${userCO?.confirmPassword}"/>
                            </span>
                        </li>

                        <li>
                            <label>Mouths to feed</label>
                            <span>
                                <input name="mouthsToFeed" type="text" class="inpbox  ${hasErrors(bean: userCO, field: 'mouthsToFeed', 'errors')}" value="${userCO?.mouthsToFeed}"/>
                            </span>
                        </li>

                        <li>
                        </li>
                    </ul>
                </div>
            </div>
            <g:render template="/user/googleCheckout"/>
        </g:form>
    </div>
    <div class="bottom-shadow">
        <label>&nbsp;</label>

    </div>
</div>
</body>
</html>

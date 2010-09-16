<%@ page import="org.codehaus.groovy.grails.commons.ConfigurationHolder" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta name="layout" content="menu"/>
    <title>Add User</title>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'user.css')}"/>

    <style type="text/css" media="screen">
    #mpFacebookConnectSuccess {
        width: 80% !important;
    }

    .FBConnectButton_Text {
        width: 150px !important;
    }

    .fbconnect_login_button {
        margin-left: 100px;
    }
    </style>
</head>
<body>
<div id="wrapper" class="clearfix">
    <div id="content-wrapper" class="clearfix">
        <div class="headbox">
            <h3>Free Signup</h3>
        </div>

                                    <g:hasErrors bean="${userCO}">
                                        <div id="displayUserCOErrors" class="errors">
                                            <g:renderErrors bean="${userCO}" as="list"/>
                                        </div>
                                    </g:hasErrors>
        <div class="top-shadow">
            <label>&nbsp;</label>
        </div>
        <g:form name="freeSignupForm" action="newFreeUserSignUp">
            
            <div class="leftbox clearfix">
                <div id="leftpanelbox">
                    <div class="boxDiv">
                        The Minute Menu plan is an online community based software service offered to you with our compliments. Create a free account now and take it for a spin, and let us know what you think.
                    </div>

                    <div class="boxDiv" style="text-align: center;">
                        <input type="image" src="${resource(dir: 'images', file: 'click-FreeUserSignup.jpg')}"/>
                    </div>
                </div>
                <div id="rightpanelbox" style="border-left:1px solid #ddd;">

                    <div class="clearfix">
                        <div class="boxDiv  boxDivcenter">
                            <h1>Profile Information</h1>
                            <ul>
                                <li>
                                    <input name="" type="radio" value=""/>
                                    Link your account with your Facebook account</li>
                                <li>&nbsp; &nbsp;<facebook:freeUserConnect/><br/></li>
                                <li><input name="" type="radio" value=""/>Create a new account. (You can link it later) </li>
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
                                    <label>City, St.</label>
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
                                    <label>Repeat Password</label>
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
                                    <label></label>
                                    <span>
                                        <g:checkBox name="showAlcoholicContent" value="${userCO?.showAlcoholicContent}"/><strong>Opt-in for Content Featuring Alcohol</strong>
                                    </span>
                                </li>

                            </ul>
                        </div></div>
                </div>
            </div>
        </g:form>
        <div class="bottom-shadow">
            <label>&nbsp;</label>
        </div>
    </div>
</div>
</body>
</html>

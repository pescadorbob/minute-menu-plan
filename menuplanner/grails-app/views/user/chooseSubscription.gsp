<%@ page import="com.mp.tools.UserTools; org.codehaus.groovy.grails.commons.ConfigurationHolder" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta name="layout" content="menu"/>
    <title>Choose Subscription</title>
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
        <h3>Choose a subscription:</h3>
    </div>
    <div class="top-shadow">
        <label>&nbsp;</label>
    </div>
    <div class="leftbox clearfix">
        <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
        </g:if>

        <div class="clearfix"><br/>
            The Minute Menu plan is an online software service.
            Please choose a subscription level from the options below.<br/>
            <br/>
        </div>

    %{--<div id="ajaxPaypalForm" style="display:none">--}%
    %{----}%
    %{--</div>--}%
        <g:form name="googleCheckoutForm" action="newUserCheckout">
            <input type="hidden" name="roles" value="${com.mp.domain.PartyRoleType.Subscriber.name()}"/>
            <div id="leftpanelbox">
                <h1>Available Subscriptions:</h1>

                <table class="data"><tbody>
                <tr>
                    <th>Subscription</th><th>Price</th>
                </tr>
                <g:each in="${availableProducts}" var="product">
                    <g:each in="${product.pricing}" status="i" var="pricingComponent">
                        <tr>
                            <td nowrap><input id="${pricingComponent.id}" name="pricingId" type="radio" value="${pricingComponent.id}"/>
                                <label for="${pricingComponent.id}">${product.name}:${pricingComponent.description}</label></td>
                            <td align="right"><g:formatNumber number="${pricingComponent.value}" type="currency" currencyCode="USD"/>
                            </td>
                        </tr>
                    </g:each>
                </g:each>
                </tbody></table>

                <g:if test="${!UserTools.currentUser}">
                    <div class="account-link">
                        <h1>Profile Information</h1>
                        <ul>
                            <li>
                                <input id="facebook" name="loginCredential" type="radio" value="facebook"/>
                                <span><label for="facebook">Link your account with your facebook account</label></span>
                            </li>
                            <li id="fb"><facebook:connect/></li>
                            <li>
                                <input id="mmp" name="loginCredential" type="radio" value="mmp"/>
                                <span><label for="mmp">Create a new account. (You can link it later)</label></span>
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
                </g:if>
                <div class="boxDiv">

                    <ul>
                        <g:if test="${!UserTools.currentUser}">
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

                        </g:if>
                        <li>
                            <input type="image" src="https://www.paypal.com/en_US/i/btn/btn_subscribeCC_LG.gif" id="submitForm"/>
                            %{--<g:render template="/user/googleCheckout"/>--}%
                        </li>
                    </ul>
                </div>
            </div>
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
%{--<script type="text/javascript">--}%
%{--jQuery("#submitForm").click(function(){--}%
%{--jQuery.post("${createLink(action:'newUserCheckout')}",function(htmlData){--}%
%{--//                  jQuery("#googleCheckoutForm").hide()--}%
%{--jQuery("#ajaxPaypalForm").html(htmlData)--}%
%{--})--}%
%{--return false;--}%
%{--})--}%
%{--</script>--}%
</body>
</html>

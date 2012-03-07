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
            <p class="offer">Some information is available to subscribed community members only.<br/>
            <b>Why not subscribe to the Minute Menu Plan community to see more.</b><br/></p>
            
          <g:if test="${UserTools.currentUser}"><p class="offer">
                  <g:if test="${pendingSubscriptions?.size()>0}">
                    The following PENDING subscriptions were found that you could activate: </br>
                    <g:render template="pendingSubscriptions" model="[pendingSubscriptions:pendingSubscriptions]" />
                      <i>If you really want don't want to contribute a recipe, then you can purchase a subscription.  You choose.</i>
                  </g:if>
              </p>
          </g:if>            
        <g:else>
            <p class="offer"><i>Community Membership is easy, and fun!</i>
              <ul><li>All it takes for a Community Membership is your
            favorite recipe contribution.</li>
              <li>If you don't want to contribute your recipe or you'd just like to support
            the community more, please choose a supporting membership.  We love you for it!</li>
              <li>Supporting members have
            priority on new feature requests!</li>

            </ul></i></p>
        </g:else>
        </div>

    %{--<div id="ajaxPaypalForm" style="display:none">--}%
    %{----}%
    %{--</div>--}%
        <g:form name="googleCheckoutForm" action="newUserCheckout" id="createAccountForm">
            <input type="hidden" name="roles" value="${com.mp.domain.PartyRoleType.Subscriber.name()}"/>
            <div id="leftpanelbox">
                <h1>Available Subscriptions:</h1>
                <g:if test="${blankSubscriptionError}">
                    <div class="errors">Please choose a Subscription.</div>
                </g:if>
                <table class="data"><tbody>
                <tr>
                    <th>Subscription</th><th>Price</th>
                </tr>
                <%
                   boolean isSelectedProductFree = false
                %>
                <g:each in="${availableProducts}" var="productOffering">
                  <%
                    if(productOffering.id == params.long('productId')&&
                       productOffering.basePrice?.value <=0) isSelectedProductFree = true
                  %>
                  <g:if test="${(UserTools.currentUser  && productOffering.basePrice?.value > 0) ||
                                 !UserTools.currentUser}">

                    <tr>
                        <td class="${productOffering.basePrice?.value <=0? "free":"paypal"}" nowrap>
                            <g:radio id="productOffering_${productOffering.id}" value="${productOffering.id}" name="productId" checked="${productOffering.id == params.long('productId')}"/>

                            <label for="productOffering_${productOffering.id}">${productOffering.name} : ${productOffering.basePrice?.description}</label>
                        </td>
                        <td align="right">
                            <g:formatNumber number="${productOffering.basePrice?.value}" type="currency" currencyCode="USD"/>
                        </td>
                    </tr>
                  </g:if>
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
                          <g:if test="${isSelectedProductFree}">
                            <input type="image" src="${resource(dir:'images', file:'click-FreeUserSignup.jpg')}" id="submitForm"/>
                            </g:if><g:else>
                          <input type="image" src="https://www.paypal.com/en_US/i/btn/btn_subscribeCC_LG.gif" id="submitForm"/>
                        </g:else>
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
<script type="text/javascript">
jQuery("td.free").click(function(){

  jQuery("#submitForm").attr( 'src', '${resource(dir:'images', file:'click-FreeUserSignup.jpg')}' )
  return true;
})
jQuery("td.paypal").click(function(){
  jQuery("#submitForm").attr( 'src', 'https://www.paypal.com/en_US/i/btn/btn_subscribeCC_LG.gif' )
  return true;
})
</script>
</body>
</html>

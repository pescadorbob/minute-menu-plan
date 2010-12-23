<%@ page import="org.codehaus.groovy.grails.commons.ConfigurationHolder" %>
<html>
<head>
    <g:javascript library="jquery"/>
    <g:setProvider library="jquery"/>
</head>
<body>
<form action="${ConfigurationHolder.config.grails.paypal.server}" method="post">
    <input type="hidden" name="cmd" value="_xclick-subscriptions">
    <input type="hidden" name="returnAction" value="paymentAccept" id="returnAction"/>
    <input type="hidden" name="returnController" value="subscription" id="returnController"/>
    <input type="hidden" name="notifyAction" value="paymentConfirm" id="notifyAction"/>
    <input type="hidden" name="notifyController" value="subscription" id="notifyController"/>
    <input type="hidden" name="cancelAction" value="paymentCancel" id="cancelAction"/>
    <input type="hidden" name="cancelController" value="subscription" id="cancelController"/>
    %{--<input type="hidden" name="notify_url" value="${createLink(controller: 'subscription', action: 'paymentConfirm')}" id="notify_url"/>--}%
    %{--<input type="hidden" name="return" value="http://qa.menuplanner.intelligrape.net/subscription/paymentConfirm?buyerId=${userId}&item_number=${item_number}" id="return"/>--}%
    <input type="hidden" name="business" value="${ConfigurationHolder.config.grails.paypal.email}">
    <input type="hidden" name="lc" value="US">
    <input type="hidden" name="custom" value="${userId}">
    <input type="hidden" name="item_name" value="${item_name}">
    <input type="hidden" name="item_number" value="${item_number}">
    <input type="hidden" name="no_note" value="1">
    <input type="hidden" name="no_shipping" value="1">
    <g:if test="${startAfter}">
        <input type="hidden" name="a1" value="0.00">
        <input type="hidden" name="p1" value="${startAfter.tokenize('.').first()}">
        <g:if test="${recurrence.tokenize('.').last()=='year'}">
            <input type="hidden" name="t1" value="Y">
        </g:if>
        <g:elseif test="${recurrence.tokenize('.').last()=='month'}">
            <input type="hidden" name="t1" value="M">
        </g:elseif>
        <g:elseif test="${recurrence.tokenize('.').last()=='week'}">
            <input type="hidden" name="t1" value="W">
        </g:elseif>
        <g:elseif test="${recurrence.tokenize('.').last()=='day'}">
            <input type="hidden" name="t1" value="D">
        </g:elseif>
    </g:if>
    <input type="hidden" name="src" value="1">
    <input type="hidden" name="a3" value="${item_price}">
    <input type="hidden" name="p3" value="${recurrence.tokenize('.').first()}">
    <g:if test="${recurrence.tokenize('.').last()=='year'}">
        <input type="hidden" name="t3" value="Y">
    </g:if>
    <g:elseif test="${recurrence.tokenize('.').last()=='month'}">
        <input type="hidden" name="t3" value="M">
    </g:elseif>
    <g:elseif test="${recurrence.tokenize('.').last()=='week'}">
        <input type="hidden" name="t3" value="W">
    </g:elseif>
    <g:elseif test="${recurrence.tokenize('.').last()=='day'}">
        <input type="hidden" name="t3" value="D">
    </g:elseif>
    <input type="hidden" name="currency_code" value="${item_currency}">
    <input type="hidden" name="bn" value="PP-SubscriptionsBF:btn_subscribeCC_LG.gif:NonHostedGuest">
    <input type="image" style="display: none;" src="https://www.paypal.com/en_US/i/btn/btn_subscribeCC_LG.gif" border="0" name="submit" alt="PayPal - The safer, easier way to pay online!">
    <img alt="" border="0" src="https://www.paypal.com/en_US/i/scr/pixel.gif" width="1" height="1">
</form>

<script type="text/javascript">
    jQuery(document).ready(function() {
        jQuery('form').submit();
    });
</script>
</body>
</html>
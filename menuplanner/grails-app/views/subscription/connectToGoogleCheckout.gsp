<%@ page import="org.codehaus.groovy.grails.commons.ConfigurationHolder" %>
<html>
<head>
    <g:javascript library="jquery"/>
    <g:setProvider library="jquery"/>
</head>
<body>
<script type="text/javascript">

    jQuery(document).ready(function() {
        jQuery('#googleCheckoutPaymentForm').submit()
    });
</script>

<form id="googleCheckoutPaymentForm" method="POST"
        action="${ConfigurationHolder.config.googleCheckout.action}">
    <input type="hidden" name="item_name_1" value="${item_name}"/>
    <input type="hidden" name="item_description_1"
            value="${item_description}"/>
    <input type="hidden" name="item_price_1" value="${item_price}"/>
    <input type="hidden" name="item_currency_1" value="${item_currency}"/>
    <input type="hidden" name="item_quantity_1" value="${item_quantity}"/>
    <input type="hidden" name="item_merchant_id_1" value="${userId}"/>
</form>
</body>
</html>
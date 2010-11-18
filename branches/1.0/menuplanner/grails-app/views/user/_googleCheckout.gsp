<%@ page import="org.codehaus.groovy.grails.commons.ConfigurationHolder" %>
<div id="rightpanelbox">
    <h1>Complete Order with Google Checkout</h1>
    <div id="googleCheckoutLogo">
    </div>
    <div class="clearfix" id="checkoutImg">
        <img src="https://www.google.com/images/logos/checkout_logo.gif" alt=""/> <br/>
        <input type="image" class="googleCheckoutLinkFT" name="Google Checkout" alt="Fast checkout through Google"
                src="${ConfigurationHolder.config.googleCheckout.imageSource}">
    </div>
</div>
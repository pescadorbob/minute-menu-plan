<%@ page import="org.codehaus.groovy.grails.commons.ConfigurationHolder" %>
<div>
    <h1>Complete Order:</h1>

    <div class="clearfix" id="checkoutImg">
        <img src="https://www.google.com/images/logos/checkout_logo.gif" alt=""/> <br/>
        <input type="image" class="googleCheckoutLinkFT" name="Google Checkout" alt="Fast checkout through Google"
                src="${ConfigurationHolder.config.googleCheckout.imageSource}">
    </div>
</div>
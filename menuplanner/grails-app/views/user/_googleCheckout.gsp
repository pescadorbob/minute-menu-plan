<%@ page import="org.codehaus.groovy.grails.commons.ConfigurationHolder" %>
<div id="rightpanelbox">
    <h1>Complete Order with Google Checkout</h1>
    <div class="clearfix"><br/>
        <img src="https://www.google.com/images/logos/checkout_logo.gif"/>
        <form method="POST"
                action="${ConfigurationHolder.config.googleCheckout.action}">
            <input type="image" name="Google Checkout" alt="Fast checkout through Google"
                    src="${ConfigurationHolder.config.googleCheckout.imageSource}">
            <input type="hidden" name="item_name_1" value="Monthly Subscription"/>
            <input type="hidden" name="item_description_1"
                    value="Monthly Subscription of MenuPlanner"/>
            <input type="hidden" name="item_price_1" value="5.00"/>
            <input type="hidden" name="item_currency_1" value="USD"/>
            <input type="hidden" name="item_quantity_1" value="1"/>
        </form>

    </div>
</div>
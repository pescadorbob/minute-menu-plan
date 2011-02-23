<%@ page import="com.mp.domain.Permission" %>
<g:if test="${permission.hasPermission(permission: Permission.LIST_USERS)}">
    <li>
        <a href="#"><span>Admin</span></a>
        <ul>
            <li>
                %{--<a>Volume to weight</a>--}%
                <g:link class="accountsLinkFT"  controller="user" action="list">User Accounts</g:link>
                <g:link class="quickFillLinkFT"  controller="quickFill" action="quickFillAdmin">Quick Fill</g:link>
                <g:link class="securityRoleLinkFT"  controller="securityRole" action="list">Security Role</g:link>
                <g:if test="${(permission.hasPermission(permission: Permission.MANAGE_HOME_PAGE))}">
                    <g:link class="homePageLinkFT" name="manageHomePage" controller="homePage" action="list">Homepage</g:link>
                </g:if>
                <g:if test="${(permission.hasPermission(permission: Permission.MANAGE_TESTIMONIAL))}">
                    <g:link class="testimonialLinkFT" name="manageTestimonials" controller="testimonial" action="list">Testimonials</g:link>
                </g:if>
                <g:if test="${(permission.hasPermission(permission: Permission.MANAGE_THEMES))}">
                    <g:link class="testimonialLinkFT" name="manageContent" controller="wcmRepository">Manage Content</g:link>
                </g:if>
            </li>
            <li>
                <g:if test="${(permission.hasPermission(permission: Permission.MANAGE_SUBSCRIPTIONS))}">
                  <g:link class="showGeneralLedgerLinkFT"  controller="account" action="showGeneralLedger">General Ledger</g:link>
                    <g:link class="manageBasePriceLinkFT" name="manageBasePrice" controller="basePrice" action="list">Base Price</g:link>
                    <g:link class="manageContentLinkFT" name="manageContent" controller="content" action="list">Content</g:link>
                    <g:link class="manageContentSubscriptionsLinkFT" name="manageContentSubscriptions" controller="contentSubscription" action="list">Content Subscriptions</g:link>
                    <g:link class="manageFeaturesLinkFT" name="manageFeatures" controller="feature" action="list">Features</g:link>
                    <g:link class="manageFeaturedOfferingApplicabilityLinkFT" name="manageFeaturedOfferingApplicability" controller="featuredOfferingApplicability" action="list">Featured Offering Applicability</g:link>
                    <g:link class="manageFeatureSubscriptionsLinkFT" name="manageFeatureSubscriptions" controller="featureSubscription" action="list">Feature Subscription</g:link>
                    <g:link class="managePricingComponentLinkFT" name="managePricingComponent" controller="pricingComponent" action="list">Pricing Component</g:link>
                    <g:link class="manageProductOfferingLinkFT" name="manageProductOffering" controller="productOffering" action="list">Product Offerings</g:link>
                    <g:link class="manageRecurringChargesLinkFT" name="manageRecurringCharges" controller="recurringCharge" action="list">Recurring Charges</g:link>
                    <g:link class="manageSubscriptionsLinkFT" name="manageSubscriptions" controller="subscription" action="list">Subscriptions</g:link>
                </g:if>
                %{--<a>Featured plans</a>--}%
            </li>
            <li><!-- Theme -->
                <pty:hasPermission bean="${loggedUser?.party}" permission="${Permission.MANAGE_THEMES}">
                  <g:link controller="account" action="bootstrapAccounts">Populate Accounts</g:link>
                    <g:link controller="theme" action="list">Themes</g:link>
                    <g:link controller="pageElement" action="list">Page Elements</g:link>
                </pty:hasPermission>
                %{--<a>Featured plans</a>--}%
            </li>
            <li><!-- Bootstrap -->

            </li>
        </ul>
    </li>
</g:if>

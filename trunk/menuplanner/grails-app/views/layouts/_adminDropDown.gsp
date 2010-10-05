<%@ page import="com.mp.domain.Permission" %>
<g:if test="${permission.hasPermission(permission: Permission.LIST_USERS)}">
    <li>
        <a href="#"><span>Admin</span></a>
        <ul>
            <li>
                %{--<a>Volume to weight</a>--}%
                <g:link class="accountsLinkFT"  controller="user" action="list">Accounts</g:link>
                <g:link class="quickFillLinkFT"  controller="quickFill" action="quickFillAdmin">Quick Fill</g:link>
                <g:if test="${(permission.hasPermission(permission: Permission.MANAGE_HOME_PAGE))}">
                    <g:link name="manageHomePage" controller="homePage" action="show">Homepage</g:link>
                </g:if>
                <g:if test="${(permission.hasPermission(permission: Permission.MANAGE_TESTIMONIAL))}">
                    <g:link name="manageTestimonials" controller="testimonial" action="list">Testimonials</g:link>
                </g:if>
                %{--<a>Featured plans</a>--}%
            </li>
        </ul>
    </li>
</g:if>
    
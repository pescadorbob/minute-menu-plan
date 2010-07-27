<%@ page import="com.mp.domain.Permission" %>
<g:if test="${permission.hasPermission(permission: Permission.LIST_USERS)}">
    <li>
        <a href="#"><span>Admin</span></a>
        <ul>
            <li>
                %{--<a>Volume to weight</a>--}%
                <g:link class="accountsLinkFT"  controller="user" action="list">Accounts</g:link>
                <g:link class="quickFillLinkFT"  controller="quickFill" action="quickFillAdmin">Quick Fill</g:link>
                %{--<a>Featured plans</a>--}%
            </li>
        </ul>
    </li>
</g:if>
    
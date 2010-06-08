<%@ page import="com.mp.domain.Permission" %>
<g:if test="${mp.hasPermission(permission: Permission.LIST_USERS)}">
    <li>
        <a href="#"><span>Admin</span></a>
        <ul>
            <li>
                %{--<a>Volume to weight</a>--}%
                <g:link controller="user" action="list">Accounts</g:link>
                %{--<a>Quick Fill</a>--}%
                %{--<a>Featured plans</a>--}%
            </li>
        </ul>
    </li>
</g:if>
    
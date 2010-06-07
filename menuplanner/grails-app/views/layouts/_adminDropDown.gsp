<%@ page import="com.mp.domain.Permission" %>
<li>
    <a href="#"><span>Admin</span></a>
    <ul>
        <li>
            %{--<a>Volume to weight</a>--}%
            <g:if test="${mp.hasPermission(permission: Permission.LIST_USERS)}">
                <g:link controller="user" action="list">Accounts</g:link>
            </g:if>
            %{--<a>Quick Fill</a>--}%
            %{--<a>Featured plans</a>--}%
        </li>
    </ul>
</li>
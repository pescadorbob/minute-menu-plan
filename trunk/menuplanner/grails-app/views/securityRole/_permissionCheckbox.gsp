<%@ page import="com.mp.MenuConstants" %>
<li>
    <g:checkBox value="${MenuConstants.UNRESTRICTED_ACCESS_PERMISSION_LEVEL}" name="${permission}" class="checkbox7"
                checked="${mp.isPermissionChecked(role: role, permission: permission, value: MenuConstants.UNRESTRICTED_ACCESS_PERMISSION_LEVEL)}"/>
    <span>${title}</span>
</li>

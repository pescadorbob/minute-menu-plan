<%@ page import="com.mp.MenuConstants" %>
<li>
    <span>
        <g:checkBox value="${MenuConstants.UNRESTRICTED_ACCESS_PERMISSION_LEVEL}" name="${permissionName}" class="checkbox7"
                checked="${permission.isPermissionChecked(role: role, permission: permissionName, value: MenuConstants.UNRESTRICTED_ACCESS_PERMISSION_LEVEL)}"/>
    </span>
    <span>${title}</span>
</li>

<%@ page import="com.mp.MenuConstants" %>
<li class="permissionLi">
    <span>
        <g:checkBox value="${MenuConstants.UNRESTRICTED_ACCESS_PERMISSION_LEVEL}" name="${permissionName}" class="parent-check-box"
                checked="${permission.isPermissionChecked(role: role, permission: permissionName, value: MenuConstants.UNRESTRICTED_ACCESS_PERMISSION_LEVEL)}"/>
    </span>
    <span>${title}</span>
    <div>
        <span>
            <g:checkBox value="${MenuConstants.ACCESS_IF_OWNS_RECIPE_PERMISSION_LEVEL}" name="${permissionName}" class="child-check-box"
                    checked="${permission.isPermissionChecked(role: role, permission: permissionName, value: MenuConstants.ACCESS_IF_OWNS_RECIPE_PERMISSION_LEVEL)}"/>
        </span>
        <span>If owner of Recipe</span>
    </div>
    <div>
        <span>
            <g:checkBox value="${MenuConstants.ACCESS_IF_OWNS_USER_PERMISSION_LEVEL}" name="${permissionName}" class="child-check-box"
                    checked="${permission.isPermissionChecked(role: role, permission: permissionName, value: MenuConstants.ACCESS_IF_OWNS_USER_PERMISSION_LEVEL)}"/>
        </span>
        <span>If owner of Profile</span>
    </div>
</li>

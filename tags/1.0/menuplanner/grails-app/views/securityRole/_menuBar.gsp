<div class="vendor">
    <span>Menus</span>
    <ul>
        <g:render template="/securityRole/permissionCheckbox" model="[permission: 'CAN_ACCESS_PERIMETER_GROUP_MENU', title: 'Perimeter Groups', role: role]" />
        <g:render template="/securityRole/permissionCheckbox" model="[permission: 'CAN_ACCESS_PERIMETER_MENU', title: 'Perimeters', role: role]" />
        <g:render template="/securityRole/permissionCheckbox" model="[permission: 'CAN_ACCESS_REPORTS_MENU', title: 'Reports', role: role]" />
        <g:render template="/securityRole/permissionCheckbox" model="[permission: 'CAN_ACCESS_ADMIN_MENU', title: 'Admin', role: role]" />
    </ul>
    <div class="clr"></div>
</div>

<%@ page import="com.mp.MenuConstants" %>
<li class="dotsLine"><strong>User</strong><br/>
    <ul>
        <g:render template="/securityRole/permissionCheckbox" model="[title: 'List', role: role, permissionName: 'LIST_USERS']"/>
        <g:render template="/securityRole/permissionCheckbox" model="[title: 'Remove Recipe Abuse', role: role, permissionName: 'REMOVE_RECIPE_ABUSE']"/>
        <g:render template="/securityRole/permissionCheckbox" model="[title: 'Remove Comment Abuse', role: role, permissionName: 'REMOVE_COMMENT_ABUSE']"/>
        <g:render template="/securityRole/selfUserPermissionCheckbox" model="[title: 'Edit', role: role, permissionName: 'UPDATE_USERS']"/>
        <g:render template="/securityRole/selfUserPermissionCheckbox" model="[title: 'Delete', role: role, permissionName: 'DELETE_USERS']"/>
    </ul>
</li>
<li class="dotsLine">
    <strong>Recipes</strong>
    <ul>
        <g:render template="/securityRole/ownsRecipePermissionCheckbox" model="[title: 'Update', role: role, permissionName: 'UPDATE_RECIPE']"/>
        <g:render template="/securityRole/selfUserPermissionCheckbox" model="[title: 'Remove From Favourties', role: role, permissionName: 'REMOVE_RECIPE_FROM_FAVOURITES']"/>
    </ul>
</li>
<li class="dotsLine">
    <strong>Manage Roles</strong>
    <ul>
        <g:render template="/securityRole/permissionCheckbox" model="[title: 'Manage Super Admin', role: role, permissionName: 'MANAGE_SUPER_ADMIN']"/>
        <g:render template="/securityRole/permissionCheckbox" model="[title: 'Manage Administrator', role: role, permissionName: 'MANAGE_ADMIN']"/>
        <g:render template="/securityRole/permissionCheckbox" model="[title: 'Manage Subscriber', role: role, permissionName: 'MANAGE_SUBSCRIBER']"/>
        <g:render template="/securityRole/permissionCheckbox" model="[title: 'Manage Director', role: role, permissionName: 'MANAGE_AFFILIATE']"/>
        <g:render template="/securityRole/permissionCheckbox" model="[title: 'Manage coach', role: role, permissionName: 'MANAGE_SUB_AFFILIATE']"/>
    </ul>
</li>
<li class="dotsLine">
    <strong>Assign Permissions</strong>
    <ul>
        <g:render template="/securityRole/permissionCheckbox" model="[title: 'Assign Super Admin', role: role, permissionName: 'CAN_ASSIGN_SUPER_ADMIN_ROLE']"/>
        <g:render template="/securityRole/permissionCheckbox" model="[title: 'Assign Administrator', role: role, permissionName: 'CAN_ASSIGN_ADMIN_ROLE']"/>
        <g:render template="/securityRole/permissionCheckbox" model="[title: 'Assign Subscriber', role: role, permissionName: 'CAN_ASSIGN_SUBSCRIBER_ROLE']"/>
        <g:render template="/securityRole/permissionCheckbox" model="[title: 'Assign Director', role: role, permissionName: 'CAN_ASSIGN_AFFILIATE_ROLE']"/>
        <g:render template="/securityRole/permissionCheckbox" model="[title: 'Assign Coach', role: role, permissionName: 'CAN_ASSIGN_SUB_AFFILIATE_ROLE']"/>
    </ul>
</li>
<li class="dotsLine">
    <strong>Director Permissions </strong>
    <ul>
        <g:render template="/securityRole/permissionCheckbox" model="[title: 'View Subscriber Invitation URL', role: role, permissionName: 'CAN_VIEW_INVITATION_URL']"/>
        <g:render template="/securityRole/permissionCheckbox" model="[title: 'View Clients of Sub-Director', role: role, permissionName: 'CAN_VIEW_CLIENTS']"/>
        <g:render template="/securityRole/permissionCheckbox" model="[title: 'View Sub-directors of Director', role: role, permissionName: 'CAN_VIEW_SUB_AFFILIATES']"/>
    </ul>
</li>
<li class="dotsLine">
    <strong>Manage Home Page</strong>
    <ul>
        <g:render template="/securityRole/permissionCheckbox" model="[title: 'Manage Homepage ', role: role, permissionName: 'MANAGE_HOME_PAGE']"/>
        <g:render template="/securityRole/permissionCheckbox" model="[title: 'Manage Testimonials ', role: role, permissionName: 'MANAGE_TESTIMONIAL']"/>
    </ul>
</li>
<li class="dotsLine">
    <strong>Manage Subscriptions</strong>
    <ul>
        <g:render template="/securityRole/permissionCheckbox" model="[title: 'Manage Subscriptions ', role: role, permissionName: 'MANAGE_SUBSCRIPTIONS']"/>
        <g:render template="/securityRole/permissionCheckbox" model="[title: 'Manage Themes ', role: role, permissionName: 'MANAGE_THEMES']"/>
    </ul>
</li>
<script type="text/javascript">

    jQuery(document).ready(function() {
        jQuery.each(jQuery('.parent-check-box'), function() {
            jQuery(this).click(function() {
                disableChildrenIfUnchecked(this);
            });
            disableChildrenIfUnchecked(this);
        });
    });

    function disableChildrenIfUnchecked(parent) {
        if (jQuery(parent).is(':checked')) {
            jQuery.each(jQuery(parent).parents('li.permissionLi').find('.child-check-box'), function() {
                jQuery(this).removeAttr('disabled');
            })
        } else {
            jQuery.each(jQuery(parent).parents('li.permissionLi').find('.child-check-box'), function() {
                jQuery(this).removeAttr('checked');
                jQuery(this).attr('disabled', 'true');
            })
        }
    }

</script>


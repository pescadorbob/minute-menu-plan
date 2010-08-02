<%@ page import="com.mp.MenuConstants" %>
<li class="dotsLine"><strong>User</strong><br/>
    <ul>
        <g:render template="/securityRole/permissionCheckbox" model="[title: 'List', role: role, permissionName: 'LIST_USERS']" />
        <g:render template="/securityRole/permissionCheckbox" model="[title: 'Update Role', role: role, permissionName: 'UPDATE_USER_ROLES']" />
        <g:render template="/securityRole/permissionCheckbox" model="[title: 'Remove Recipe Abuse', role: role, permissionName: 'REMOVE_RECIPE_ABUSE']" />
        <g:render template="/securityRole/permissionCheckbox" model="[title: 'Remove Comment Abuse', role: role, permissionName: 'REMOVE_COMMENT_ABUSE']" />
        <g:render template="/securityRole/selfUserPermissionCheckbox" model="[title: 'Edit', role: role, permissionName: 'UPDATE_USERS']" />
        <g:render template="/securityRole/selfUserPermissionCheckbox" model="[title: 'Delete', role: role, permissionName: 'DELETE_USERS']" />
    </ul>
</li>
<li class="dotsLine">
    <strong>Recipes</strong>
    <ul>
        <g:render template="/securityRole/ownsRecipePermissionCheckbox" model="[title: 'Update', role: role, permissionName: 'UPDATE_RECIPE']" />
        <g:render template="/securityRole/selfUserPermissionCheckbox" model="[title: 'Remove From Favourties', role: role, permissionName: 'REMOVE_RECIPE_FROM_FAVOURITES']" />
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


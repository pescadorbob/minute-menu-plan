<%@ page import="com.mp.MenuConstants" %>
<li class="dotsLine"><strong>User</strong><br/>
    <ul>
        <li><span>
            <g:checkBox value="${MenuConstants.UNRESTRICTED_ACCESS_PERMISSION_LEVEL}" name="LIST_USERS" class="checkbox7"
                    checked="${permission.isPermissionChecked(role: role, permission: 'LIST_USERS', value: MenuConstants.UNRESTRICTED_ACCESS_PERMISSION_LEVEL)}"/>
            </span><span>List</span></li>
        <li><span>
            <g:checkBox value="${MenuConstants.UNRESTRICTED_ACCESS_PERMISSION_LEVEL}" name="UPDATE_USER_ROLES" class="checkbox7"
                    checked="${permission.isPermissionChecked(role: role, permission: 'UPDATE_USER_ROLES', value: MenuConstants.UNRESTRICTED_ACCESS_PERMISSION_LEVEL)}"/>
            </span><span>Update Role</span></li>
        <li><span>
            <g:checkBox value="${MenuConstants.UNRESTRICTED_ACCESS_PERMISSION_LEVEL}" name="REMOVE_RECIPE_ABUSE" class="checkbox7"
                    checked="${permission.isPermissionChecked(role: role, permission: 'REMOVE_RECIPE_ABUSE', value: MenuConstants.UNRESTRICTED_ACCESS_PERMISSION_LEVEL)}"/>
            </span><span>Remove Recipe Abuse</span></li>
        <li><span>
            <g:checkBox value="${MenuConstants.UNRESTRICTED_ACCESS_PERMISSION_LEVEL}" name="REMOVE_COMMENT_ABUSE" class="checkbox7"
                    checked="${permission.isPermissionChecked(role: role, permission: 'REMOVE_COMMENT_ABUSE', value: MenuConstants.UNRESTRICTED_ACCESS_PERMISSION_LEVEL)}"/>
            </span><span>Remove Comment Abuse</span></li>
    </ul>
</li>
<li class="dotsLine">
    <strong>Recipes</strong>
    <ul>
        <li class="permissionLi">
            <span>
                <g:checkBox value="${MenuConstants.UNRESTRICTED_ACCESS_PERMISSION_LEVEL}" name="UPDATE_RECIPE" class="parent-check-box"
                        checked="${permission.isPermissionChecked(role: role, permission: 'UPDATE_RECIPE', value: MenuConstants.UNRESTRICTED_ACCESS_PERMISSION_LEVEL)}"/>
                </span>
            <span>Update</span>
            <div>
                <span>
                    <g:checkBox value="${MenuConstants.ACCESS_IF_OWNS_RECIPE_PERMISSION_LEVEL}" name="UPDATE_RECIPE" class="child-check-box"
                            checked="${permission.isPermissionChecked(role: role, permission: 'UPDATE_RECIPE', value: MenuConstants.ACCESS_IF_OWNS_RECIPE_PERMISSION_LEVEL)}"/>
                    </span>
                <span>If Owned</span>
            </div>
        </li>
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


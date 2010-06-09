<%@ page import="com.mp.domain.Permission" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="menu"/>
    <title>Show Security Role</title>
</head>
<body>
<div id="content-wrapper" class="clearfix">
    <div class="headbox">
        <h3>Create Security Role</h3>
    </div>
    <g:if test="${flash.message}">
        <div class="errors">${flash.message}</div>
    </g:if>
    <div class="top-shadow">
        <label>&nbsp;</label>
    </div>
    <div class="leftbox clearfix">

    %{--<div class="button-tripe">--}%
    %{--<img src="${resource(dir: 'images', file: 'save.png')}"/>  &nbsp;  <img src="${resource(dir: 'images', file: 'delete.png')}"/>--}%
    %{--</div>--}%
        <g:form name="createSecurityRoleForm" action="save" controller="securityRole">
            <div id="secureRole">
                <ul>
                    <li><strong>Name ${role}:</strong>
                        <input type="text" name="name" class="inpbox ${hasErrors(bean: role, field: 'name', 'errors')}" value="${role?.name}" size="60"/>
                    </li>
                    <li><span><strong>Description:</strong></span>
                        <label>
                            <textarea name="description" cols="48" rows="3" class="inpbox ${hasErrors(bean: role, field: 'description', 'errors')}">${role?.description}</textarea>
                        </label>
                    </li>
                    <li>
                        <h3>Permissions For Security Role</h3>
                        <ul><li><a href="#" onclick="checkAllCheckbox();
                        return false;">Check All</a></li>
                            <li>|</li>
                            <li><a href="#" onclick="unCheckAllCheckbox();
                            return false;">Uncheck All</a></li>
                            <li>|</li>
                            <li><a href="#">Copy Permissions From</a></li>
                            <li>
                                <g:select name="applicationRole" id="applicationRole" class="inpbox"
                                        optionKey="id"
                                        value="${id}"
                                        onchange="getPermissionsForSecurityRole( this.value,'${createLink(action:'getPermissionsForSecurityRole', controller:'securityRole')}')"
                                        from="${roles}" optionValue="name"
                                        noSelection="['noSelection':'(Select One)']"/>
                            </li>
                            <li></li>

                        </ul>

                    </li>
                    <div id="permissions-div">
                        <g:render template="/securityRole/permissionsNew"/>
                    </div>
                </ul>
            </div>
            <div id="button">
                <g:actionSubmit class="button" value="Create" controller="securityRole" action="save"/>
            </div>
        </g:form>
    </div>
    <div class="bottom-shadow">
        <label>&nbsp;</label>
    </div>
</div>
<script type="text/javascript">
    function checkAllCheckbox() {
        jQuery(':checkbox').attr('checked', true);
        jQuery('.child-check-box').attr('checked', false);
    }
    function unCheckAllCheckbox() {
        jQuery(':checkbox').attr('checked', false);
    }

    function getPermissionsForSecurityRole(roleId, ajaxUrl) {
        unCheckAllCheckbox();
        jQuery.get(ajaxUrl,
        { ajax: 'true',roleId:roleId}, function(data) {
            jQuery('#permissions-div').html(data);
            jQuery.each(jQuery('.parent-check-box'), function() {
                disableChildrenIfUnchecked(this);
            });
        });
        jQuery('#applicationRole option[value="noSelection"]').attr('selected', 'true');
    }
</script>

</body>
</html>

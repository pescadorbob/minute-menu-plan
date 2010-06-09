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

            <div id="secureRole">
                <ul>
                    <li><strong>Name : </strong>${role.name}</li>
                    <li><strong>Description: </strong>${role.description}</li><br/>
                    <div id="permissions-div">
                        <g:render template="/securityRole/permissions"/>
                    </div>
                </ul>
            </div>
        <div id="button">
            <ul>
                <li><g:link action="edit" id="${role.id}" name="editSecurityRole">
                    <input type="button" class="button" value="Edit"/></g:link>
                </li>
            </ul>
        </div>
    </div>
    <div class="bottom-shadow">
        <label>&nbsp;</label>
    </div>
</div>
<script type="text/javascript">
    jQuery(document).ready(function() {
        jQuery(':checkbox').attr('disabled', true)
    });
</script>

</body>
</html>

<%@ page import="com.mp.domain.*" %>
<html>
<head>
    <meta name="layout" content="menu"/>
    <title>Edit User</title>
</head>
<body>

<div id="container">
    <div id="wrapper" class="clearfix">
        <div id="content-wrapper" class="clearfix">
            <div class="headbox">
                <h3>Admin Profile  Edit</h3>
            </div>
            <g:hasErrors bean="${userCO}">
                <div class="errors">
                    <g:renderErrors bean="${userCO}" as="list"/>
                </div>
            </g:hasErrors>

            <div class="top-shadow">
                <label>&nbsp;</label>
            </div>
            <div class="leftbox clearfix">
                <g:uploadForm name="formUpdateUser">
                    <g:hiddenField name='id' value='${userCO?.id}'/>
                    <g:render template="/user/imageSection" model="[userCO:userCO]"/>
                    <g:render template="/user/userDetail" model="[userCO:userCO]"/>
                    <div id="button">
                        <g:actionSubmit name="btnUpdate" class="button" value="Update User" controller="user" action="update"/>
                        <g:actionSubmit name="cancel" class="button" value="Cancel" controller="user" action="show"/>
                    </div>
                </g:uploadForm>
            </div>
            <div class="bottom-shadow">
                <label>&nbsp;</label>
            </div>
        </div>
    </div>
</div>
<g:render template="/user/userJquery"/>
<script type="text/javascript">
    jQuery(document).ready(function() {
        jQuery('.passwordSection').hide()
        jQuery('#displayEmailAsLabel').html(jQuery('input[name="email"]').val())
        jQuery('input[name="email"]').hide()
        jQuery('#btnChangePassword').show()
    })
</script>
</body>
</html>
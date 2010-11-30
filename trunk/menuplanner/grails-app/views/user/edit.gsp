<%@ page import="org.apache.commons.lang.StringUtils; com.mp.domain.*" %>
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
                <h3>Edit ${StringUtils.capitaliseAllWords(party?.toString())} Profile Details</h3>
            </div>
            <g:hasErrors bean="${userCO}">
                <div id="displayUserCOErrors" class="errors">
                    <g:renderErrors bean="${userCO}" as="list"/>
                </div>
            </g:hasErrors>
            <div class="top-shadow">
                <label>&nbsp;</label>
            </div>
            <div class="leftbox clearfix">
                <g:form name="formUpdateUser">
                    <g:hiddenField name='id' value='${userCO?.id}'/>
                    <g:render template="/user/imageSection" model="[userCO:userCO,party:party]"/>
                    <g:render template="/user/userDetail" model="[userCO:userCO,party:party,currentUser:UserTools.currentUser?.party]"/>
                    <div id="button">
                        <g:actionSubmit class="button updateUserButtonFT" value="Update User" controller="user" action="update"/>
                        <g:actionSubmit class="button" value="Cancel" controller="user" action="show"/>
                    </div>
                </g:form>
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
        jQuery(':input[name="password"]').val(jQuery(':input[name="confirmPassword"]').val());
    })
</script>
</body>
</html>

<%@ page import="com.mp.domain.*" %>
<html>
<head>
    <meta name="layout" content="menu"/>
    <title>User List</title>
</head>
<body>

<div id="container">
    <div id="wrapper" class="clearfix">
        <div id="content-wrapper" class="clearfix">
            <div class="headbox">
                <h3>User List</h3>
            </div>

            <div class="top-shadow">
                <label>&nbsp;</label>
            </div>
            <g:form name="searchForm">
                <div class="leftbox clearfix">
                    <div id="adduser">
                        <ul>
                            <li>
                                <span><strong>Accounts :</strong></span>
                                <label>
                                    <g:actionSubmit controller="user" action="create" name="addUser" value="Add User" class="pointer"/>
                                </label>
                            </li>
                            <li>
                                <span><strong>Filter Name :</strong></span>
                                <input name="searchName" type="text" class="inpbox" value="${searchName}"/>
                                &nbsp;&nbsp;&nbsp;&nbsp;Show&nbsp;
                                <input type="radio" name="userStatus" ${(userStatus == 'all') ? 'checked="checked"' : ''} value="all" onClick="usersByStatus()"/> All
                                <input type="radio" name="userStatus" ${(userStatus == 'enabled') ? 'checked="checked"' : ''} value="enabled" onClick="usersByStatus()"/> Enabled
                                <input type="radio" name="userStatus" ${(userStatus == 'disabled') ? 'checked="checked"' : ''} value="disabled" onClick="usersByStatus()"/> Disabled
                            &nbsp;  &nbsp; &nbsp; Flagged :
                                <input type="text" class="inpboxSmall">
                            </li>
                        </ul>
                        <g:actionSubmit controller="user" action="list" name="submit" value="Submit" style="display:none;"/>
                    </div>
                    <div id="userlist" class="clearfix">
                        <g:render template="/user/usersResult" mode="[userLise:userList]"/>
                    </div>
                    <div id="pagination">
                        <g:paginate controller="user" action="list" total="${total}"/>
                    </div>
                </div>
            </g:form>
            <div class="bottom-shadow">
                <label>&nbsp;</label>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    function usersByStatus() {
        jQuery('input[name="submit"]').click();
        return false;
    }
</script>

</body>
</html>
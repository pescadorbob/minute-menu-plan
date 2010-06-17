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
            <div class="leftbox clearfix">
                <g:if test="${flash.message}">
                    <div class="userFlashMessage">
                        ${flash.message}
                    </div>
                </g:if>
                <div id="adduser">
                    <ul>
                        <g:form name="searchForm">
                            <li>
                                <g:actionSubmit id="btnSubmit" controller="user" action="list" name="submit" value="Submit" style="display:none;"/>
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
                            &nbsp;  &nbsp; &nbsp; Flagged :<input name="flags" type="text" class="inpboxSmall" value="${flags}">
                            </li>
                        </g:form>
                    </ul>
                </div>
                <div id="userlist" class="clearfix">
                    <g:render template="/user/usersResult" mode="[userLise:userList]"/>
                </div>
                <div class="paginateButtons">
                    <g:paginate controller="user" action="list" total="${total}"/>
                </div>
            </div>
            <div class="bottom-shadow">
                <label>&nbsp;</label>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    function usersByStatus() {
        jQuery('#btnSubmit').click();
        return false;
    }
</script>

</body>
</html>
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
            <g:form controller="user" action="list" name="searchForm">
                <div class="leftbox clearfix">

                    <div id="adduser">

                        <ul>
                            <li>
                                <span><strong>Accounts :</strong></span>
                                <label>
                                    <a href="${createLink(controller: 'user', action: 'create')}">
                                        <input type="button" value="Add User" class=""/>
                                    </a>
                                </label>
                            </li>
                            <li>
                                <span><strong>Filter Name :</strong></span>
                                %{--<label>--}%
                                <input name="searchName" type="text" class="inpbox" value="${searchName}"/>
                                &nbsp;
                                <g:checkBox name="hideEnabled" value="${hideEnabled}" onClick="hideEnabledUsers()"/>
                                %{--<input name="hideEnabled" type="checkbox" value="${hideEnabled}"/>--}%
                                hide Active
                                &nbsp; &nbsp;
                                <g:checkBox name="hideDisabled" value="${hideDisabled}" onClick="hideDisabledUsers()"/>
                                hide Inactive   &nbsp;  &nbsp; &nbsp; Flagged :
                                <input type="text" class="inpbox" style="width:50px;">
                                %{--</label>--}%
                            </li>
                        </ul>

                        <g:submitButton name="submit" value="Submit" style="display:none;"/>

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
    function hideEnabledUsers(){
        jQuery('input[name="submit"]').click();
        return false;
    }
    function hideDisabledUsers(){
        jQuery('input[name="submit"]').click();
        return false;
    }
</script>

</body>
</html>
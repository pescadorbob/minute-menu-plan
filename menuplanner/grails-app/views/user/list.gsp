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
                <div id="adduser">
                    <g:form controller="user" action="list"
                            name="searchForm">

                        <ul>
                            <li>
                                <span><strong>Accounts :</strong></span>
                                <label>
                                    <a href="${createLink(controller: 'user', action: 'create')}">
                                        <input type="button" value="Add User" class="button"/>
                                    </a>
                                </label>
                            </li>
                            <li>
                                <span><strong>Filter Name :</strong></span>
                                <label>
                                    <input name="searchName" type="text" class="inpbox" value="${params.searchName}"/>
                                    &nbsp;
                                    <input name="" type="checkbox" value=""/>

                                    hide Enabled
                                    &nbsp; &nbsp; <input name="" type="checkbox" value=""/>
                                    hide disabled   &nbsp;  &nbsp; &nbsp; Flagged :
                                    <input type="text" class="inpbox" style="width:50px;">
                                </label>
                            </li>
                        </ul>

                        <g:submitButton name="submit" value="Submit" style="display:none;"/>
                    </g:form>

                </div>

                <div id="userlist" class="clearfix">
                    <g:render template="/user/usersResult" mode="[userLise:userList]"/>
                </div>
                <div id="pagination">
                    <g:paginate controller="user" action="list" total="${total}"/>
                </div>
            </div>

            <div class="bottom-shadow">
                <label>&nbsp;</label>
            </div>
        </div>
    </div>
</div>
</body>
</html>
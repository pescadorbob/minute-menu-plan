<%@ page import="com.mp.domain.Permission" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="menu"/>
    <title>Security Role List</title>
</head>
<body>
<br/>
<div id="content-wrapper" class="clearfix">
    <div class="headbox">
        <h3>Security Roles List</h3>
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
        <div id="rolelist" class="clearfix">
            <ul>
                <li class="head">
                    <ul>
                        <li>Name</li>
                        <li class="desc">Description</li>
                    </ul>
                </li>

                <g:each in="${roles}" var="role">
                    <li class="alternatecolor">
                        <ul>
                            <li><a href="${createLink(action: 'show', controller: 'securityRole', id: role?.id)}">${role?.name}</a></li>
                            <li class="desc">${role?.description}</li>
                        </ul>
                    </li>
                </g:each>
            </ul>
        </div>
    </div>
    <div class="bottom-shadow">
        <label>&nbsp;</label>
    </div>
</div>
</body>
</html>

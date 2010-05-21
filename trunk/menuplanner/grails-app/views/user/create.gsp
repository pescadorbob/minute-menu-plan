<%@ page import="com.mp.domain.*" %>
<html>
<head>
    <meta name="layout" content="menu"/>
    <title>Add User</title>
</head>
<body>

<div id="container">
    <div id="wrapper" class="clearfix">
        <div id="content-wrapper" class="clearfix">
            <div class="headbox">
                <h3>Admin Profile  Add</h3>
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
                <g:uploadForm name="formCreateUser" controller="user" action="save">
                    <g:render template="/user/imageSection" model="[userCO:userCO]"/>
                    <g:render template="/user/userDetail" model="[userCO:userCO]"/>
                    <div id="button">
                        <g:submitButton name="btnUpdate" class="button" value="Create User"/>
                        <input class="button" type="button" name="cancel" id="cancel" value="Cancel"/>
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
</body>
</html>
<%@ page import="com.mp.domain.*" %>
<html>
<head>
    <meta name="layout" content="menu"/>
    <title>Show Sub Affiliate</title>
</head>
<body>
<div id="container">
    <div id="wrapper" class="clearfix">
        <div id="content-wrapper" class="clearfix">
            <div class="headbox">
                <h3>Sub Affiliate Detail</h3>
            </div>
            <div class="top-shadow">
                <label>&nbsp;</label>
            </div>
            <div class="leftbox clearfix">
                <g:if test="${flash.message}">
                    <div id="flashMsgTst" class="userFlashMessage">
                        ${flash.message}
                    </div>
                </g:if>
                <div id="leftpanel">
                    <ul>
                        <li>Member since ${party?.joiningDate?.format('MMMM yyyy')}</li>
                        <li></li><li></li>
                        <g:each in="${party?.roleTypes}" var="roleType"><li class="userRolesFT"><strong>${roleType}</strong></li></g:each>
                    </ul>
                </div>
                <div id="rightpanel">
                    <ul>
                        <li><span><strong>Email :</strong></span><label>${party?.email}</label></li>
                        <li><span><strong>Name :</strong></span><label>${party?.name}</label></li>
                    </ul>
                </div>
                <div id="button">
                    <g:submitButton class='button' id="backToCurrentUserProfile" controller='user' action='show' value='Back' name="back" onclick=""/>
                </div>
            </div>
            <div class="bottom-shadow">
                <label>&nbsp;</label>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $("#backToCurrentUserProfile").click(function() {
        window.location.replace("${createLink(controller:'user',action:'show',id:LoginCredential?.currentUser?.party?.id)}");
        return false;
    })
</script>
</body>
</html>
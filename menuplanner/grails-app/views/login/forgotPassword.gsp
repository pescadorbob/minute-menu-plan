<%@ page import="com.mp.domain.MealType" %>
<html>
<head>
    <meta name="layout" content="menu"/>
    <title>Forgot Password</title>
</head>

<body>

<div id="container">
    <div id="wrapper" class="clearfix">
        <div id="content-wrapper" class="clearfix">
            <div class="headbox">
                <h3>Forgot Password:</h3>
            </div>
            <div class="top-shadow"><label>&nbsp;</label></div>
            <div class="leftbox clearfix">
                <div id="adduser">
                    <g:if test="${passwordChanged}">
                        ${flash.message}
                    </g:if>
                    <g:else>
                        <g:if test="${flash.message}">
                            <div class="userFlashMessage">
                                ${flash.message}
                            </div>
                        </g:if>
                        some text<br/>
                        <g:form name="forgotPasswordForm">
                            Username : (Email Address) <g:textField name="email"/> <g:actionSubmit action="resetPassword" controller="login" value="Reset Password"/>
                        </g:form>
                    </g:else>
                    <p>&nbsp;</p><p>&nbsp;</p><p>Click here to go back to <g:link controller="login" action="index">Home Page</g:link></p>
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

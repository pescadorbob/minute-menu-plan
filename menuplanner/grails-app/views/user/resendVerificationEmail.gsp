<%@ page import="com.mp.domain.MealType" %>
<html>
<head>
    <meta name="layout" content="menu"/>
    <title>Resend Verification Email</title>
</head>
<body>
<div id="container">
    <div id="wrapper" class="clearfix">
        <div id="content-wrapper" class="clearfix">
            <div class="headbox">
                <h3>Resend Verification Email</h3>
            </div>
            <div class="top-shadow"><label>&nbsp;</label></div>
            <div class="leftbox clearfix">
                <div id="adduser">
                    <g:if test="${mailSent}">
                        ${flash.message}
                    </g:if>
                    <g:else>
                        <g:if test="${flash.message}">
                            <div class="errors">
                                ${flash.message}
                            </div>
                        </g:if>
                        <br/>
                        <g:form action="resendVerificationEmail"  controller="user" name="resendEmailForm">
                            Email: <g:textField name="email"/>
                            <input type="submit"  name="resendEmail" value="Resend Email"/>
                        </g:form>
                    </g:else>
                    <p>&nbsp;</p><p>&nbsp;</p><p>Click here to go back to <g:link uri="/">Home Page</g:link></p>
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

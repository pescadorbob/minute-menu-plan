<%@ page import="com.mp.domain.Testimonial" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="menu"/>
    <title>Show Testimonial</title>
</head>
<body>
<div id="content-wrapper" class="clearfix">
    <div class="headbox">
        <h3>Show Testimonial</h3>
    </div>
    <g:if test="${flash.message}">
        <div class="userFlashMessage">${flash.message}</div>
    </g:if>
    <div class="top-shadow">
        <label>&nbsp;</label>
    </div>
    <div class="leftbox clearfix">
        <g:form>
            <div id="secureRole">
                <ul>
                    <li><strong>Testimonial:</strong></li>
                    <li>${testimonial?.description}</li>
                </ul>
            </div>
            <div id="button">
                <ul>
                    <li><g:hiddenField name="id" value="${testimonial?.id}"/>
                        <g:link action="edit" id="${testimonial?.id}" name="editSecurityRole">
                            <input type="button" class="button" value="Edit"/></g:link>
                    </li>
                </ul>
            </div>
        </g:form>

    </div>
    <div class="bottom-shadow">
        <label>&nbsp;</label>
    </div>
</div>
</body>
</html>

<%@ page import="grails.util.GrailsUtil; org.codehaus.groovy.grails.commons.GrailsApplication" %>
<html>
<head>
    <title>Error Page</title>
    <meta name="layout" content="menu"/>
</head>
<body>
<div id="content-wrapper" class="clearfix" style="height:1000px;">
    <div class="headbox">
        <h3>Oops .. there is an error</h3>
        <br/>
        <div class="top-shadow"><label>&nbsp;</label></div>
        <div class="leftbox clearfix">
            <div id="adduser">
                <h2>Looks like there is some problem serving your request. This can be because of various reasons such as:</h2><br/><br/>
                <h4>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;# You are trying to access a page which does not exist.</h4><br/>
                <h4>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;# You do-not have necessary rights to access the page you are trying to reach.</h4><br/>
                <h4>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;# We are experiencing some problem at our server side.</h4><br/><br/>
                <g:if test="${GrailsUtil.environment != GrailsApplication.ENV_PRODUCTION}">

                    <br/><h2><u>Following stack trace is not displayed in production enviroment</u></h2><br/>

                    <h4>
                        <div class="message">
                            <strong>Message:</strong> ${exception?.message?.encodeAsHTML()} <br/>
                            <strong>Caused by:</strong> ${exception?.cause?.message?.encodeAsHTML()} <br/>
                            <strong>Class:</strong> ${exception?.className} <br/>
                            <strong>At Line:</strong> [${exception?.lineNumber}] <br/>
                            <strong>Code Snippet:</strong><br/><br/>
                            <div class="snippet">
                                <g:each var="cs" in="${exception?.codeSnippet}">
                                    ${cs?.encodeAsHTML()}<br/>
                                </g:each>
                            </div>
                        </div>
                    </h4><br/>
                    <h2>Stack Trace</h2><br/>
                    <g:each in="${exception?.stackTraceLines}">
                        <h4>${it?.encodeAsHTML()}<br/></h4>
                    </g:each>
                </g:if>
            </div>
        </div>
        <div class="bottom-shadow">
            <label>&nbsp;</label>
        </div>
    </div>
</div>
</body>
</html>
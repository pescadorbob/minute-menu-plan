<%@ page import="org.codehaus.groovy.grails.commons.ConfigurationHolder; com.mp.domain.Testimonial" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="menu"/>
    <title>Add Image</title>
</head>
<body>
<div id="content-wrapper" class="clearfix">
    <div class="headbox">
        <h3>Add Image</h3>
    </div>
    <g:if test="${flash.message}">
        <div class="userFlashMessage">${flash.message}</div>
    </g:if>
    <div class="top-shadow">
        <label>&nbsp;</label>
    </div>
    <div class="leftbox clearfix">
        <g:form controller="homePage" method="post" action="uploadImage" enctype="multipart/form-data">
            <input type="file" id="homepageImage" name="file"/> <br/><br/>
            <g:submitButton class="button"  name="upload" value="Upload"/>
        </g:form>
    </div>
    <br/>
    <g:if test="${image?.id}">
        <div class="leftbox clearfix">
            <span> Image Url</span><br/>
            <textarea name="imageUrl" rows="1" cols="70" readonly="true" style="text-align:center;">${ConfigurationHolder.config.grails.serverURL + '/image/image/' + image?.id}</textarea>
        </div>
    </g:if>
    <div class="bottom-shadow">
        <label>&nbsp;</label>
    </div>
</div>
</body>
</html>

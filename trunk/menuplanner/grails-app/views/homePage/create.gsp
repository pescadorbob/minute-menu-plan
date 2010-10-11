<%@ page import="com.mp.domain.HomePage" %>
<html>
<head>
    <meta name="layout" content="menu"/>
    <title>Create Homepage</title>
    <tinyMce:importJs/>
    <calendar:resources lang="en" theme="aqua"/>
</head>
<body>
<div id="container">
    <div id="wrapper" class="clearfix">
        <div id="content-wrapper" class="clearfix">
            <div class="headbox">
                <h3>Create Homepage</h3>
            </div>
            <g:hasErrors bean="${homePage}">
                <div id="displayRecipeErrors" class="errors">
                    <g:renderErrors bean="${homePage}"/>
                </div>
            </g:hasErrors>
        <div class="clearfix">
            <div class="top-shadow">
                <label>&nbsp;</label>
            </div>
            <g:uploadForm name="homePageCreate">
                <div class="leftbox">
                    Name <g:textField name="name" value="${fieldValue(bean: homePage, field: 'name')}"/><br/> <br/>
                    Active From <calendar:datePicker name="activeFrom" dateFormat="%m/%d/%Y %H:%M:%S" showTime="true" defaultValue="${new Date()}"/>
                    &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; Active To   <calendar:datePicker showTime="true" dateFormat="%m/%d/%Y %H:%M:%S" name="activeTo" defaultValue="${new Date()}"/>
                </div>
                <div class="leftbox">
                    <div style="text-align:right; color:#007AD8;"><g:link action="addImage" controller="homePage">Upload Image</g:link></div>
                    <br/>
                    Left Bar
                    <fckeditor:editor width="100%" height="400" name="leftBar" fileBrowser="default">${homePage?.leftBar}</fckeditor:editor>
                    <br/>
                    Central Text
                    <fckeditor:editor width="100%" height="400" name="centralText" fileBrowser="default">${homePage?.centralText}</fckeditor:editor>
                    <br/>
                    Categories
                    <fckeditor:editor width="100%" height="400" name="categories" fileBrowser="default">${homePage?.categories}</fckeditor:editor>
                    <br/>
                </div>
                <div class="bottom-shadow">
                    <label>&nbsp;</label>
                </div>
                </div>
                <div id="left-panel">
                    <div id="button">
                        <ul>
                            <li>
                                <g:actionSubmit class="button" controller="homePage" action="save" value="Create"/>
                            </li>
                            <li>
                                <g:actionSubmit class="button" controller="homePage" action="list" name="cancel" value="Cancel"/>
                            </li>
                        </ul>
                    </div>
                </div>
            </g:uploadForm>
        </div>
    </div>
</div>
</body>
</html>

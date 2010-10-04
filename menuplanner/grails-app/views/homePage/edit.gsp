<%@ page import="com.mp.domain.HomePage" %>
<html>
<head>
    <meta name="layout" content="menu"/>
    <title>Edit Homepage</title>
    <tinyMce:importJs/>
</head>
<body>
<div id="container">
    <div id="wrapper" class="clearfix">
        <div id="content-wrapper" class="clearfix">
            <div class="headbox">
                <h3>Edit HomePage</h3>
            </div>
            <g:hasErrors bean="${homePage}">
                <div id="displayRecipeErrors" class="errors">
                    <g:renderErrors bean="${homePage}"/>
                </div>
            </g:hasErrors>
            <g:uploadForm name="homePageEdit">
                <div class="left-panel-product">
                    <div class="top-shadow">
                        <label>&nbsp;</label>
                    </div>
                    <div class="leftbox">
                        Left Bar
                        <fckeditor:editor width="100%" height="400" name="leftBar" fileBrowser="default">${homePage?.leftBar}</fckeditor:editor>
                        <br/>
                        Central Text
                        <fckeditor:editor width="100%" height="400" name="centralText" fileBrowser="default">${homePage?.centralText}</fckeditor:editor>
                        <br/>
                        Categories
                        <fckeditor:editor width="100%" height="400" name="categories" fileBrowser="default">${homePage?.categories}</fckeditor:editor>
                        <br/>
                        Testimonials
                        <fckeditor:editor width="100%" height="400" name="testimonial" fileBrowser="default">${homePage?.testimonial}</fckeditor:editor>
                    </div>
                    <div class="bottom-shadow">
                        <label>&nbsp;</label>
                    </div>
                </div>
                <div id="left-panel">
                    <div id="button">
                        <ul>
                            <li>
                                <g:actionSubmit class="button" controller="homePage" action="save" value="Update"/>
                            </li>
                            <li>
                                <g:actionSubmit class="button" controller="recipe" action="list" name="cancel" value="Cancel"/>
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

<%@ page import="com.mp.domain.HomePage" %>
<html>
<head>
    <meta name="layout" content="menu"/>
    <title>Edit Testimonial</title>
    <tinyMce:importJs/>
</head>
<body>
<div id="container">
    <div id="wrapper" class="clearfix">
        <div id="content-wrapper" class="clearfix">
            <div class="headbox">
                <h3>Edit Testimonial</h3>
            </div>
            <g:hasErrors bean="${testimonial}">
                <div id="displayRecipeErrors" class="errors">
                    <g:renderErrors bean="${testimonial}"/>
                </div>
            </g:hasErrors>
            <g:uploadForm name="testimonialForm">
                <div class="left-panel-product">
                    <div class="top-shadow">
                        <label>&nbsp;</label>
                    </div>
                    <div class="leftbox">
                        Testimonial
                        <fckeditor:editor width="100%" height="300" name="description" fileBrowser="default">${testimonial?.description}</fckeditor:editor>
                        <br/>
                    </div>
                    <div class="bottom-shadow">
                        <label>&nbsp;</label>
                    </div>
                </div>
                <div id="left-panel">
                    <div id="button">
                        <input type="hidden" name="id" value="${testimonial?.id}">
                        <ul>
                            <li>
                                <g:actionSubmit class="button" controller="testimonial" action="update" value="Update"/>
                            </li>
                            <li>
                                <g:actionSubmit class="button" controller="testimonial" action="delete" name="delete" value="Delete" onclick="return confirm('Are you sure?');"/>
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

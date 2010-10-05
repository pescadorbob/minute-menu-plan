<%@ page import="com.mp.domain.HomePage" %>
<html>
<head>
    <meta name="layout" content="menu"/>
    <title>Create Testimonial</title>
    <tinyMce:importJs/>
</head>
<body>
<div id="container">
    <div id="wrapper" class="clearfix">
        <div id="content-wrapper" class="clearfix">
            <div class="headbox">
                <h3>Create Testimonial</h3>
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
                        Write Testimonial
                        <fckeditor:editor width="100%" height="300" name="description" fileBrowser="default">${testimonial?.description}</fckeditor:editor>
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
                                <g:actionSubmit class="button" controller="testimonial" action="save" value="Create"/>
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

<%@ page import="com.mp.domain.HomePage" %>
<html>
<head>
    <meta name="layout" content="menu"/>
    <title>Show Homepage</title>
</head>
<body>
<div id="container">
    <div id="wrapper" class="clearfix">
        <div id="content-wrapper" class="clearfix">
            <div class="headbox">
                <h3>Show HomePage</h3>
            </div>
            <g:hasErrors bean="${homePage}">
                <div id="displayRecipeErrors" class="errors">
                    <g:renderErrors bean="${homePage}"/>
                </div>
            </g:hasErrors>
            <div class="left-panel-product-show">
                <div class="top-shadow">
                    <label>&nbsp;</label>
                </div>
                <div class="leftbox leftBarShow">
                    ${homePage?.leftBar}
                </div>
                <div class="bottom-shadow">
                    <label>&nbsp;</label>
                </div>
            </div>

            <div id="maiddle-panel1">
                <div class="top-shadow">
                    <label>&nbsp;</label>
                </div>
                <div class="leftbox leftBarShow">
                    ${homePage?.centralText}
                    <br/>
                    ${homePage?.categories}
                </div>
                <div class="bottom-shadow">
                    <label>&nbsp;</label>
                </div>
            </div>


            <div class="left-panel-product-show">
                <div class="top-shadow">
                    <label>&nbsp;</label>
                </div>
                <div class="leftbox leftBarShow">
                    Testimonials
                    ${homePage?.testimonial}
                </div>
                <div class="bottom-shadow">
                    <label>&nbsp;</label>
                </div>
            </div>
            <div id="left-panel">
                <g:uploadForm name="homePageShow">
                    <div id="button">
                        <g:form name="editHomePageForm">
                            <ul>
                                <li>
                                    <g:actionSubmit class="button" controller="homePage" action="edit" value="Edit"/>
                                </li>
                                <li>
                                    <g:actionSubmit class="button" controller="recipe" action="list" name="cancel" value="Cancel"/>
                                </li>
                            </ul>
                        </g:form>
                    </div>
                </g:uploadForm>
            </div>
        </div>
    </div>
</div>
</body>
</html>

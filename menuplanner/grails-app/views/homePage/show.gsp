<%@ page import="grails.util.GrailsUtil; com.mp.domain.Testimonial; com.mp.domain.themes.HomePage" %>
<html>
<head>
    <meta name="layout" content="menu"/>
    <title>Preview Homepage</title>
</head>
<body>
<div id="container">
    <div id="wrapper" class="clearfix">
        <div id="content-wrapper" class="clearfix">
            <div class="headbox">
                <h3>Homepage Preview</h3>
            </div>
            <g:if test="${flash.message}">
                <div id="flashMsgTst" class="userFlashMessage">
                    ${flash.message}
                </div>
            </g:if>
            <div id="left-panel-product">
                <div class="top-shadow">
                    <label>&nbsp;</label>
                </div>
                <div class="leftbox clearfix">
                    <div id="country-cateLogin">
                        ${homePage?.leftBar}
                    </div>
                </div>
                <div class="bottom-shadow">
                    <label>&nbsp;</label>
                </div>
            </div>
            <div id="maiddle-panel">
                <div class="top-shadow">
                    <label>&nbsp;</label>
                </div>
                <div class="leftbox clearfix">
                    <div id="content">
                        <div class="clearfix">
                            ${homePage?.centralText}
                        </div>
                        <br/>
                        <div class="clearfix">
                            ${homePage?.categories}
                        </div>
                    </div>
                </div>
                <div class="bottom-shadow">
                    <label>&nbsp;</label>
                </div>
            </div>
            <div id="right-panel-landing">
                <div class="top-shadow">
                    <label>&nbsp;</label>
                </div>
                <div class="leftbox clearfix">
                    <div id="login">
                        <ul>
                            <li>Username : (Email Address)<div class="search-input">
                                <input name="email" type="text" class="inp" readonly="true"/></div>
                            </li>
                            <li>Password :<div class="search-input">
                                <input name="password" type="password" class="inp" readonly="true"/></div>
                            </li>
                            <li>
                                <input type="button" value="Login" readonly="true"/>
                            </li>
                            <li><a href="#">forgot password or username?</a></li>
                            <span style="color:#007AD8">Or login using Facebook</span>
                            <li><img src="${resource(dir:'images', file:'facebook-connect.gif')}" alt="Face Book" border="0"/></li>
                            <li class="border"><h2>TESTIMONIAL</h2></li>
                            <g:each in="${Testimonial.findAllByShowOnHomepage(true)}" var="testimonial">
                                <li>${testimonial}</li>
                            </g:each>
                        </ul>
                    </div>
                </div>
                <div class="bottom-shadow">
                    <label>&nbsp;</label>
                </div>
            </div>


            <div id="left-panel">
                <g:form>
                    <div id="button">
                        <g:form name="editHomePageForm">
                            <input type="hidden" name="id" value="${homePage?.id}"/>
                            <ul>
                                <li>
                                    <g:actionSubmit class="button editHomePageFT" controller="homePage" action="edit" value="Edit"/>
                                </li>
                                <li>
                                    <g:actionSubmit class="button" controller="homePage" action="list" name="cancel" value="Cancel"/>
                                </li>
                            </ul>
                        </g:form>
                    </div>
                </g:form>
            </div>
        </div>
    </div>
</div>
</body>
</html>

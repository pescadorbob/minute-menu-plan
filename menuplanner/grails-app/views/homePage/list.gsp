<%@ page import="com.mp.domain.Testimonial" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="menu"/>
    <title>Homepage List</title>
</head>
<body>
<div id="content-wrapper" class="clearfix">
    <div class="headbox">
        <h3>Homepage List</h3>
    </div>
    <div class="top-shadow">
        <label>&nbsp;</label>
    </div>
    <div class="leftbox clearfix">
        <g:if test="${flash.message}">
            <div class="userFlashMessage">
                ${flash.message}
            </div>
        </g:if>
        <div  class="clearfix">
            <table cellpadding="8" cellspacing="1" border="0" bgcolor="#d3d1d2" class="testimonial">
                <tr class="testimonialHead">
                    <td width="8%">HomePage</td>
                    <td width="35%" >Active From</td>
                    <td width="35%" >Active To</td>
                </tr>
                <g:each in="${homePageList}" var="homePage">
                    <tr class="testimonialWhite">
                        <td width="8%"><a href="${createLink(action: 'show', controller: 'homePage', id: homePage?.id)}">${homePage?.name}</a></td>
                        <td width="35%" ><a href="${createLink(action: 'show', controller: 'homePage', id: homePage?.id)}">${homePage?.activeFrom}</a></td>
                        <td width="35%" ><a href="${createLink(action: 'show', controller: 'homePage', id: homePage?.id)}">${homePage?.activeTo}</a></td>
                    </tr>
                </g:each>
            </table>
        </div>
        <div id="left-panel">
            <g:form>
                <div id="button">
                    <ul>
                        <li>
                            <g:actionSubmit class="button addNewHomepageFT" controller="homePage" action="create" value="Add New"/>
                        </li>
                    </ul>
                </div>
            </g:form>
        </div>
    </div>
    <div class="bottom-shadow">
        <label>&nbsp;</label>
    </div>
</div>
</body>
</html>
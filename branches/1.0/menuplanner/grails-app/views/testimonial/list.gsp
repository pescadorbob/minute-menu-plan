<%@ page import="com.mp.domain.Testimonial" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="menu"/>
    <title>Testimonial List</title>
</head>
<body>
<div id="content-wrapper" class="clearfix">
    <div class="headbox">
        <h3>Testimonials</h3>
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
                    <td width="8%">Testimonials</td>
                    <td width="70%" >Description</td>
                    <td>Show on Homepage</td>
                </tr>
                <g:each in="${testimonialList}" var="testimonial">
                    <tr class="testimonialWhite">
                        <td width="10%"><a href="${createLink(action: 'show', controller: 'testimonial', id: testimonial?.id)}">${testimonial?.id}</a></td>
                        <td width="70%" ><a href="${createLink(action: 'show', controller: 'testimonial', id: testimonial?.id)}">${testimonial?.description}</a></td>
                        <td><g:remoteLink controller="testimonial" action="showOnHomepage" onSuccess="invertStatus('${testimonial?.id}');" name="changeStatus${testimonial?.id}"
                                id="${testimonial?.id}">${(testimonial?.showOnHomepage) ? 'Hide' : 'Show'}</g:remoteLink></td>
                    </tr>
                </g:each>
            </table>
        </div>
        <div id="left-panel">
            <g:form>
                <div id="button">
                    <ul>
                        <li>
                            <g:actionSubmit class="button" controller="testimonial" action="create" value="Add New"/>
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
<script type="text/javascript">
    function invertStatus(id) {
        var link = jQuery("a[name=changeStatus" + id + "]");
        var text = jQuery(link).text();
        if (text == 'Show') {
            jQuery(link).text('Hide');
        } else {
            jQuery(link).text('Show');
        }
    }
</script>
</body>
</html>

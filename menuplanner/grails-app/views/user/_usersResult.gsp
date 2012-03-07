<%@ page import="com.mp.domain.*" %>
<ul><li class="product"><ul>
    <g:each in="${parties}" var="party" status="index">
        <g:if test="${index %4==0}">
        </ul><ul class="clearfix">
        </g:if>
             <g:render template="/user/thumbnail" model="[party:party]"/>
    </g:each>
</ul></li></ul>

<script type="text/javascript">
    function invertStatus(id) {
        var link = jQuery("a[name=changeStatus" + id + "]");
        var text = jQuery(link).text();
        if (text == 'Enable') {
            jQuery(link).text('Disable');
            jQuery('#status' + id).text('Enabled');
        } else {
            jQuery(link).text('Enable');
            jQuery('#status' + id).text('Disabled');
        }
    }
</script>

